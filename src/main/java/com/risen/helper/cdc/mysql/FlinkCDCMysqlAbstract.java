package com.risen.helper.cdc.mysql;

import com.alibaba.ververica.cdc.connectors.mysql.MySQLSource;
import com.alibaba.ververica.cdc.connectors.mysql.table.StartupOptions;
import com.alibaba.ververica.cdc.debezium.DebeziumSourceFunction;
import com.risen.helper.cdc.common.PortSetUtil;
import com.risen.helper.cdc.constant.DbMysqlModeEnum;
import com.risen.helper.cdc.dto.DbEventBody;
import com.risen.helper.cdc.formart.FlinkMysqlDeserializationSchemaFunction;
import com.risen.helper.cdc.property.FlinkCDCBaseProperties;
import com.risen.helper.consumer.ConditionConsumer;
import com.risen.helper.thread.ThreadPoolUtil;
import com.risen.helper.util.IfUtil;
import com.risen.helper.util.LogUtil;
import com.risen.helper.util.PredicateUtil;
import com.risen.helper.util.SpringBeanUtil;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.springframework.scheduling.annotation.Async;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/5/8 14:25
 */
public abstract class FlinkCDCMysqlAbstract<R extends SinkFunction, P extends FlinkCDCBaseProperties> {

    protected Class<R> r;
    protected Class<P> p;

    public FlinkCDCMysqlAbstract() {
        r = (Class<R>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        p = (Class<P>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        LogUtil.debug("FlinkCDCMysqlAbstract:{}", p.getName());
        SpringBeanUtil.getBean(ThreadPoolUtil.class).addTask(() -> {
            monitorBinlog();
        });
    }

    @Async
    protected void monitorBinlog() {
        try {
            FlinkCDCBaseProperties flinkCDCBaseProperties = SpringBeanUtil.getBean(p);
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            env.setParallelism(1);

            SpringBeanUtil.getBean(PortSetUtil.class).setUiPort(env);

            DebeziumSourceFunction<DbEventBody> SourceFunction = MySQLSource.<DbEventBody>builder()
                    .hostname(flinkCDCBaseProperties.getHost())
                    .port(flinkCDCBaseProperties.getPort())
                    .databaseList(flinkCDCBaseProperties.getDatabaseList())
                    .tableList(flinkCDCBaseProperties.getTableList())
                    .username(flinkCDCBaseProperties.getUserName())
                    .password(flinkCDCBaseProperties.getPassWord())
                    .serverTimeZone("Asia/Shanghai")
                    .startupOptions(getModelFunction(flinkCDCBaseProperties))
                    .deserializer(new FlinkMysqlDeserializationSchemaFunction(flinkCDCBaseProperties.getDataKey()))
                    .build();
            DataStreamSource<DbEventBody> stringDataStreamSource = env.addSource(SourceFunction);
            stringDataStreamSource.addSink(SpringBeanUtil.getBean(r));
            env.execute("Print MySQL Snapshot + Binlog");
            LogUtil.info("start monitor MySQL Binlog database:{},table:{}", flinkCDCBaseProperties.getDatabaseList(), flinkCDCBaseProperties.getTableList());
        } catch (Exception e) {
            LogUtil.error("{} proccess error:{}", this.getClass().getName(), e.getMessage());
        }
    }


    private StartupOptions getModelFunction(FlinkCDCBaseProperties flinkCDCBaseProperties) {
        /**
         *                     initial,
         *                     earliest,
         *                     latest,
         *                     specific,
         *                     timestamp;
         */
        Map<String, StartupOptions> startupOptionsMap = new HashMap<>(3);
        startupOptionsMap.put(DbMysqlModeEnum.INITIAL.getCode(), StartupOptions.initial());
        startupOptionsMap.put(DbMysqlModeEnum.EARLIEST.getCode(), StartupOptions.earliest());
        startupOptionsMap.put(DbMysqlModeEnum.LATEST.getCode(), StartupOptions.latest());
        AtomicReference<StartupOptions> startupOptions = new AtomicReference<>(StartupOptions.latest());
        ConditionConsumer consumer = () -> {
            startupOptions.set(startupOptionsMap.get(flinkCDCBaseProperties.getMode()));
        };
        IfUtil.goIf(consumer, PredicateUtil.isNotEmpty(flinkCDCBaseProperties.getMode()));
        return startupOptions.get();
    }
}

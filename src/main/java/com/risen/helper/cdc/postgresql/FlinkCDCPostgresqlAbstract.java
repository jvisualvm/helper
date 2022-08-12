package com.risen.helper.cdc.postgresql;

import com.alibaba.ververica.cdc.connectors.postgres.PostgreSQLSource;
import com.alibaba.ververica.cdc.debezium.DebeziumSourceFunction;
import com.risen.helper.cdc.common.PortSetUtil;
import com.risen.helper.cdc.constant.DbPostgresqlModeEnum;
import com.risen.helper.cdc.dto.DbEventBody;
import com.risen.helper.cdc.formart.FlinkPostgresqlDeserializationSchemaFunction;
import com.risen.helper.cdc.property.FlinkCDCBaseProperties;
import com.risen.helper.thread.ThreadPoolUtil;
import com.risen.helper.util.LogUtil;
import com.risen.helper.util.PredicateUtil;
import com.risen.helper.util.SpringBeanUtil;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.springframework.scheduling.annotation.Async;

import java.lang.reflect.ParameterizedType;
import java.util.Properties;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/5/8 14:25
 */
public abstract class FlinkCDCPostgresqlAbstract<R extends SinkFunction, P extends FlinkCDCBaseProperties> {


    protected Class<R> r;
    protected Class<P> p;

    public FlinkCDCPostgresqlAbstract() {
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

            Properties properties = new Properties();
            //when_needed 跟initial效果类似
            //schema_only  只会记录最新的更改，历史全量读不到
            //initial(默认)	连接器执行数据库的初始一致性快照，快照完成后，连接器开始为后续数据库更改流式传输事件记录。
            //always	连接器在启动时始终执行一致性快照。快照完成后，连接器继续执行流式传输更改。此模式用于以下情况：
            //-	已知一些 WAL 段已被删除，不再可用。
            //-	集群发生故障后，主备库发生切换。此快照模式确保连接器不会错过在新主节点提升之后但在新主节点上重新启动连接器之前所做的任何更改。
            //initial_only	读不到数据 连接器只执行数据库的初始一致性快照，不允许捕获任何后续更改的事件。
            //never	不执行初始一致性快照，但是会同步后续数据库的更改记录
            String mode = flinkCDCBaseProperties.getMode();
            properties.setProperty("snapshot.mode", PredicateUtil.filterOne(mode, DbPostgresqlModeEnum.NEVER.getCode()));
            //properties.setProperty("sslmode","require");
            properties.setProperty("decimal.handling.mode", "double"); //debezium 小数转换处理策略
            properties.setProperty("database.serverTimezone", "GMT+8"); //debezium 配置以database. 开头的属性将被传递给jdbc url
            DebeziumSourceFunction<DbEventBody> SourceFunction = PostgreSQLSource.<DbEventBody>builder()
                    .hostname(flinkCDCBaseProperties.getHost())
                    .port(flinkCDCBaseProperties.getPort())
                    .database(flinkCDCBaseProperties.getDatabaseList())
                    .schemaList(flinkCDCBaseProperties.getSchemaList())
                    .tableList(flinkCDCBaseProperties.getTableList())
                    .username(flinkCDCBaseProperties.getUserName())
                    .password(flinkCDCBaseProperties.getPassWord())
                    .decodingPluginName("pgoutput") // pg解码插件
                    .slotName(flinkCDCBaseProperties.getSlotName()) // 复制槽名称 不能重复
                    .debeziumProperties(properties)
                    .deserializer(new FlinkPostgresqlDeserializationSchemaFunction(flinkCDCBaseProperties.getDataKey()))
                    .build();

            DataStreamSource<DbEventBody> stringDataStreamSource = env.addSource(SourceFunction);
            stringDataStreamSource.addSink(SpringBeanUtil.getBean(r));
            env.execute("Print PostgreSQL Snapshot + Binlog");
            LogUtil.info("start monitor PostgreSQL Binlog database:{},table:{}", flinkCDCBaseProperties.getDatabaseList(), flinkCDCBaseProperties.getTableList());
        } catch (Exception e) {
            LogUtil.error("{} proccess error:{}", this.getClass().getName(), e.getMessage());
        }
    }


}

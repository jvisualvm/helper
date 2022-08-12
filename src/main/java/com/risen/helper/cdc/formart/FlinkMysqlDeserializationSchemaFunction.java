package com.risen.helper.cdc.formart;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.ververica.cdc.debezium.DebeziumDeserializationSchema;
import com.risen.helper.cdc.dto.DbEventBody;
import com.risen.helper.util.LogUtil;
import com.risen.helper.util.PredicateUtil;
import io.debezium.data.Envelope;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.util.Collector;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/5/9 9:25
 */
public class FlinkMysqlDeserializationSchemaFunction implements DebeziumDeserializationSchema<DbEventBody> {

    private String dataKeyFieldName;

    public FlinkMysqlDeserializationSchemaFunction(String dataKeyFieldName) {
        this.dataKeyFieldName = dataKeyFieldName;
    }

    @Override
    public void deserialize(SourceRecord sourceRecord, Collector<DbEventBody> collector) throws Exception {
        LogUtil.debug("sourceRecord:{}", sourceRecord.toString());
        String topic = sourceRecord.topic();
        String[] split = topic.split("\\.");
        String database = split[1];
        String table = split[2];
        Envelope.Operation operation = Envelope.operationFor(sourceRecord);

        Struct struct = (Struct) sourceRecord.value();
        Struct after = struct.getStruct("after");
        JSONObject valueMapAfter = new JSONObject();

        if (PredicateUtil.isNotEmpty(after)) {
            Schema schema = after.schema();
            for (Field field : schema.fields()) {
                valueMapAfter.put(field.name(), after.get(field.name()));
            }
        }

        Struct before = struct.getStruct("before");
        JSONObject valueMapBefore = new JSONObject();

        if (PredicateUtil.isNotEmpty(before)) {
            Schema schema = before.schema();
            for (Field field : schema.fields()) {
                valueMapBefore.put(field.name(), before.get(field.name()));
            }
        }

        Struct keyStruct = (Struct) sourceRecord.key();
        Long key = null;
        if (PredicateUtil.isNotEmpty(dataKeyFieldName)) {
            key = Long.parseLong(keyStruct.get(dataKeyFieldName).toString());
        }
        DbEventBody dbEventBody = new DbEventBody(key, database, null, table, operation.toString().toLowerCase(), valueMapAfter, valueMapBefore);
        collector.collect(dbEventBody);
    }

    @Override
    public TypeInformation<DbEventBody> getProducedType() {
        return TypeInformation.of(DbEventBody.class);
    }
}

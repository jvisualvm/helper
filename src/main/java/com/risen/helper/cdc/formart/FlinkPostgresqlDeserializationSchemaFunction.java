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

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/5/9 9:25
 */
public class FlinkPostgresqlDeserializationSchemaFunction implements DebeziumDeserializationSchema<DbEventBody> {

    private String dataKeyFieldName;

    public FlinkPostgresqlDeserializationSchemaFunction(String dataKeyFieldName) {
        this.dataKeyFieldName = dataKeyFieldName;
    }

    @Override
    public void deserialize(SourceRecord sourceRecord, Collector<DbEventBody> collector) throws Exception {
        LogUtil.debug("sourceRecord:{}", sourceRecord.toString());
        Envelope.Operation operation = Envelope.operationFor(sourceRecord);

        Struct struct = (Struct) sourceRecord.value();
        Struct after = struct.getStruct("after");
        JSONObject valueMapAfter = new JSONObject();
        Struct source = struct.getStruct("source");
        if (PredicateUtil.isNotEmpty(after)) {
            Schema schema = after.schema();
            for (Field field : schema.fields()) {
                valueMapAfter.put(field.name(), after.get(field.name()));
            }
        }

        Struct structBefore = (Struct) sourceRecord.value();
        Struct before = structBefore.getStruct("before");
        JSONObject valueMapBefore = new JSONObject();
        if (PredicateUtil.isNotEmpty(before)) {
            Schema schema = before.schema();
            for (Field field : schema.fields()) {
                valueMapBefore.put(field.name(), before.get(field.name()));
            }
        }

        Struct keyStruct = (Struct) sourceRecord.key();
        Long key=null;
        if(PredicateUtil.isNotEmpty(dataKeyFieldName)) {
            key = Long.parseLong(keyStruct.get(dataKeyFieldName).toString());
        }
        DbEventBody dbEventBody = new DbEventBody(key, source.getString("db"), source.getString("schema"), source.getString("table"), operation.toString().toLowerCase(), valueMapAfter, valueMapBefore);
        collector.collect(dbEventBody);
    }

    @Override
    public TypeInformation<DbEventBody> getProducedType() {
        return TypeInformation.of(DbEventBody.class);
    }
}

package com.risen.helper.cdc.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/5/9 9:44
 */
@Data
public class DbEventBody {

    private Long key;
    private String database;
    private String table;
    private String operation;
    private String schema;
    private JSONObject before;
    private JSONObject after;

    public DbEventBody(Long key, String database, String schema, String table, String operation, JSONObject after, JSONObject before) {
        this.key = key;
        this.database = database;
        this.schema = schema;
        this.table = table;
        this.operation = operation;
        this.before = before;
        this.after = after;
    }
}

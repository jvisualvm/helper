package com.risen.helper.cdc.property;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/26 9:56
 */
@Data
@Component
public class FlinkCDCBaseProperties {

    private String host;
    private Integer port;
    private String userName;
    private String passWord;

    private String databaseList;
    private String schemaList;
    private String tableList;
    private String slotName;
    private String dataKey;
    private String mode;

}

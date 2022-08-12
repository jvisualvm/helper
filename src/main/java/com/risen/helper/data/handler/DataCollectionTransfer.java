package com.risen.helper.data.handler;

import com.alibaba.fastjson.JSON;
import com.risen.helper.util.LogUtil;
import com.risen.helper.util.PredicateUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/26 10:31
 */
@Deprecated
public interface DataCollectionTransfer<T, U> extends Serializable {

    //数据清洗
    public List<T> dataClear(List<T> source);

    //数据填充
    public List<U> dataFill(List<T> source);

    //填充后的数据再次清洗
    public List<U> dataClearAfterFill(List<U> source);

    //数据入库
    public void dataInsert(List<U> result);


    default void processor(List<T> source) {
        if (PredicateUtil.isNotEmpty(source)) {
            Integer total = source.size();
            List<T> sourceTempList = new ArrayList<>(source);
            List<T> clearList = dataClear(source);
            source.removeAll(clearList);
            LogUtil.debug("dataClear collection throw away data[数据清洗->丢弃数据量:{},余下数据量:{},数据总量:{}],丢弃数据详情:{}", total - clearList.size(), clearList.size(), total, clearList.size() == 0 ? JSON.toJSONString(sourceTempList) : JSON.toJSONString(source));
            if (PredicateUtil.isNotEmpty(clearList)) {
                //数据填充
                List<U> resultFill = dataFill(clearList);
                Integer totalResult = resultFill.size();
                List<U> resultFillTempList = new ArrayList<>(resultFill);
                //继续清洗
                List<U> resultClear = dataClearAfterFill(resultFill);
                resultFill.removeAll(resultClear);
                LogUtil.debug("dataClearAfterFill collection throw away data[数据清洗->丢弃数据量:{},余下数据量:{},数据总量:{}],丢弃数据详情:{}", totalResult - resultClear.size(), resultClear.size(), totalResult, resultClear.size() == 0 ? JSON.toJSONString(resultFillTempList) : JSON.toJSONString(resultFill));
                //数据入库
                if (PredicateUtil.isNotEmpty(resultClear)) {
                    dataInsert(resultClear);
                }
            }
        }
    }

}

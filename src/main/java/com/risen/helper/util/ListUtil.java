package com.risen.helper.util;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/29 15:49
 */
@Component
public class ListUtil {

    public <T extends Object> List<T> list(List<T> lst) {
        return CollectionUtils.isEmpty(lst) ? new ArrayList<>() : lst;
    }

}

package com.risen.helper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/4 12:31
 */
@Repository
public interface BaseOperateMapper<T,P> extends BaseMapper<T> {
    //分页只需要使用这个方法，但是参数取值，名称必须是pageListCondition
    List<T> listPageByCondition(@Param(value = "pageListCondition") P p);

}

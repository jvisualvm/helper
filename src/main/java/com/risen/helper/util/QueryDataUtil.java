package com.risen.helper.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.risen.helper.base.BaseCreateTimeEntity;
import com.risen.helper.base.BaseEvtTimeEntity;
import com.risen.helper.base.BaseIdEntity;
import com.risen.helper.base.BaseUpdateTimeEntity;
import com.risen.helper.constant.DataOrderEnum;
import com.risen.helper.constant.Symbol;
import com.risen.helper.query.entity.BaseTimeQueryEntity;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/22 17:16
 */
@Component
public class QueryDataUtil {

    public <M extends BaseIdEntity> M getNewestOneById(BaseMapper<M> mapper) {
        TableInitUtil.initTable(BaseIdEntity.class);
        LambdaQueryWrapper<M> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.orderByDesc(M::getId).last(QueryDataUtil.limit(1));
        return mapper.selectOne(lambdaQueryWrapper);
    }

    public <M extends BaseIdEntity> List<M> getNewestNById(BaseMapper mapper, Integer limit) {
        TableInitUtil.initTable(BaseIdEntity.class);
        LambdaQueryWrapper<M> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.orderByDesc(M::getId).last(QueryDataUtil.limit(limit));
        return mapper.selectList(lambdaQueryWrapper);
    }


    public <M extends BaseEvtTimeEntity> M getNewestOneEvtTime(BaseMapper<M> mapper) {
        TableInitUtil.initTable(BaseEvtTimeEntity.class);
        LambdaQueryWrapper<M> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.orderByDesc(M::getEvtTime).last(QueryDataUtil.limit(1));
        return mapper.selectOne(lambdaQueryWrapper);
    }

    public <M extends BaseEvtTimeEntity> List<M> getNewestNByEvtTime(BaseMapper mapper, Integer limit) {
        TableInitUtil.initTable(BaseEvtTimeEntity.class);
        LambdaQueryWrapper<M> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.orderByDesc(M::getEvtTime).last(QueryDataUtil.limit(limit));
        return mapper.selectList(lambdaQueryWrapper);
    }


    public <M extends BaseCreateTimeEntity> M getNewestOneCreateTime(BaseMapper<M> mapper) {
        TableInitUtil.initTable(BaseCreateTimeEntity.class);
        LambdaQueryWrapper<M> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.orderByDesc(M::getCreateTime).last(QueryDataUtil.limit(1));
        return mapper.selectOne(lambdaQueryWrapper);
    }

    public <M extends BaseCreateTimeEntity> List<M> getNewestNByCreateTime(BaseMapper mapper, Integer limit) {
        TableInitUtil.initTable(BaseCreateTimeEntity.class);
        LambdaQueryWrapper<M> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.orderByDesc(M::getCreateTime).last(QueryDataUtil.limit(limit));
        return mapper.selectList(lambdaQueryWrapper);
    }


    public <M extends BaseUpdateTimeEntity> M getNewestOneUpdateTime(BaseMapper<M> mapper) {
        TableInitUtil.initTable(BaseUpdateTimeEntity.class);
        LambdaQueryWrapper<M> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.orderByDesc(M::getUpdateTime).last(QueryDataUtil.limit(1));
        return mapper.selectOne(lambdaQueryWrapper);
    }

    public <M extends BaseUpdateTimeEntity> List<M> getNewestNByUpdateTime(BaseMapper<M> mapper, Integer limit) {
        TableInitUtil.initTable(BaseUpdateTimeEntity.class);
        LambdaQueryWrapper<M> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.orderByDesc(M::getUpdateTime).last(QueryDataUtil.limit(limit));
        return mapper.selectList(lambdaQueryWrapper);
    }


    public <T extends BaseTimeQueryEntity> List<T> queryListByEvtTime(Date start, Date end, BaseMapper mapper, LambdaQueryWrapper<T> queryWrapper, DataOrderEnum order) {
        TableInitUtil.initTable(BaseTimeQueryEntity.class);
        if (ObjectUtils.isEmpty(queryWrapper)) {
            queryWrapper = new LambdaQueryWrapper<>();
        }
        queryWrapper.le(ObjectUtils.isNotEmpty(end), T::getEvtTime, end);
        queryWrapper.ge(ObjectUtils.isNotEmpty(start), T::getEvtTime, start);

        if (DataOrderEnum.DESC == order) {
            queryWrapper.orderByDesc(T::getEvtTime);
        }
        if (DataOrderEnum.ASC == order) {
            queryWrapper.orderByAsc(T::getEvtTime);
        }

        List<T> resultList = mapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(resultList)) {
            return new ArrayList<>();
        }
        return resultList;
    }

    public <T extends BaseTimeQueryEntity> List<T> queryListByCreateTime(Date start, Date end, BaseMapper mapper, LambdaQueryWrapper<T> queryWrapper, DataOrderEnum order) {
        TableInitUtil.initTable(BaseTimeQueryEntity.class);
        if (ObjectUtils.isEmpty(queryWrapper)) {
            queryWrapper = new LambdaQueryWrapper<>();
        }
        queryWrapper.le(ObjectUtils.isNotEmpty(end), T::getCreateTime, end);
        queryWrapper.ge(ObjectUtils.isNotEmpty(start), T::getCreateTime, start);
        if (DataOrderEnum.DESC == order) {
            queryWrapper.orderByDesc(T::getCreateTime);
        }
        if (DataOrderEnum.ASC == order) {
            queryWrapper.orderByAsc(T::getCreateTime);
        }
        List<T> resultList = mapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(resultList)) {
            return new ArrayList<>();
        }
        return resultList;
    }


    public <T extends BaseTimeQueryEntity> List<T> queryListByUpdateTime(Date start, Date end, BaseMapper mapper, LambdaQueryWrapper<T> queryWrapper, DataOrderEnum order) {
        if (ObjectUtils.isEmpty(queryWrapper)) {
            queryWrapper = new LambdaQueryWrapper<>();
        }
        TableInitUtil.initTable(BaseTimeQueryEntity.class);
        queryWrapper.le(ObjectUtils.isNotEmpty(end), T::getUpdateTime, end);
        queryWrapper.ge(ObjectUtils.isNotEmpty(start), T::getUpdateTime, start);
        if (DataOrderEnum.DESC == order) {
            queryWrapper.orderByDesc(T::getUpdateTime);
        }
        if (DataOrderEnum.ASC == order) {
            queryWrapper.orderByAsc(T::getUpdateTime);
        }

        List<T> resultList = mapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(resultList)) {
            return new ArrayList<>();
        }
        return resultList;
    }

    public <T extends BaseTimeQueryEntity> List<T> queryListLimit(Integer limitCount, BaseMapper mapper, LambdaQueryWrapper<T> queryWrapper, DataOrderEnum order) {
        List<T> resultList = null;
        if (ObjectUtils.isEmpty(queryWrapper)) {
            queryWrapper = new LambdaQueryWrapper<>();
        }
        TableInitUtil.initTable(BaseTimeQueryEntity.class);

        if (ObjectUtils.isEmpty(order)) {
            queryWrapper.orderByDesc(T::getId);
        } else {
            if (DataOrderEnum.DESC == order) {
                queryWrapper.orderByDesc(T::getId);
            }
            if (DataOrderEnum.ASC == order) {
                queryWrapper.orderByAsc(T::getId);
            }
        }
        if (ObjectUtils.isEmpty(limitCount)) {
            limitCount = 100;
        }
        queryWrapper.last("limit " + limitCount);
        resultList = mapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(resultList)) {
            return new ArrayList<>();
        }
        return resultList;
    }


    public static String limit(Integer limit) {
        StringBuilder builder = new StringBuilder("limit");
        builder.append(Symbol.SYMBOL_SPACE);
        builder.append(limit);
        return builder.toString();
    }


    public <T extends Object> List<T> queryListByField(BaseMapper mapper, Map<String, Object> whereMap, String orderField, DataOrderEnum orderEnum, Integer limit) {
        QueryWrapper<T> queryWrapper = new QueryWrapper();
        if (!CollectionUtils.isEmpty(whereMap)) {
            whereMap.forEach((k, v) -> {
                queryWrapper.eq(StringUtils.isNotEmpty(k), k, v);
            });
        }
        if (DataOrderEnum.DESC == orderEnum) {
            queryWrapper.orderByDesc(orderField);
        }
        if (DataOrderEnum.ASC == orderEnum) {
            queryWrapper.orderByAsc(orderField);
        }

        if (ObjectUtils.isNotEmpty(limit)) {
            queryWrapper.last(QueryDataUtil.limit(limit));
        } else {
            queryWrapper.last(QueryDataUtil.limit(100));
        }
        return mapper.selectList(queryWrapper);
    }

    public <T extends Object> T queryOneByField(BaseMapper<T> mapper, Map<String, Object> whereMap, String orderField, DataOrderEnum orderEnum) {
        QueryWrapper<T> queryWrapper = new QueryWrapper();
        if (!CollectionUtils.isEmpty(whereMap)) {
            whereMap.forEach((k, v) -> {
                queryWrapper.eq(StringUtils.isNotEmpty(k), k, v);
            });
        }
        if (DataOrderEnum.DESC == orderEnum) {
            queryWrapper.orderByDesc(orderField);
        }
        if (DataOrderEnum.ASC == orderEnum) {
            queryWrapper.orderByAsc(orderField);
        }
        queryWrapper.last(QueryDataUtil.limit(1));
        return mapper.selectOne(queryWrapper);
    }

}

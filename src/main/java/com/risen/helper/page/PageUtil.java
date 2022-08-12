package com.risen.helper.page;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.risen.helper.base.BaseOperateMapper;
import com.risen.helper.base.BasePage;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/4 11:37
 */
@Repository
public class PageUtil<T, P extends BasePage, M extends BaseOperateMapper> implements Serializable {
    /**
     * @param param
     * @param mapper
     * @return com.github.pagehelper.PageInfo<T>
     * @author: zhangxin
     * @description: 主要用于自己写在xml中的SQL
     * @date: 2022/3/4 13:38
     */
    public PageInfo<T> page(P param, M mapper) {
        PageHelper.startPage(param.getPageIndex(), param.getPageSize());
        List<T> list = mapper.listPageByCondition(param);
        return new PageInfo<>(list);
    }

    /**
     * @author: zhangxin
     * @description: 简化分页
     * @date: 2022/3/7 13:08
     * @param pageIndex
     * @param pageSize
     * @param mapper
     * @param queryWrapper
     * @return com.github.pagehelper.PageInfo<T>
     */
    public PageInfo<T> page(Integer pageIndex, Integer pageSize, BaseMapper mapper, Wrapper<T> queryWrapper) {
        PageHelper.startPage(pageIndex, pageSize);
        List<T> list = mapper.selectList(queryWrapper);
        return new PageInfo<>(list);
    }
}

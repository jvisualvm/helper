package com.risen.helper.page;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.PageInfo;
import com.risen.helper.base.BaseOperateMapper;
import com.risen.helper.base.BasePage;
import com.risen.helper.consumer.ForConsumer;
import com.risen.helper.util.SpringBeanUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author zhangxin
 * @version 1.0
 * @description 分页并执行某个程序
 * @date 2022/5/26 9:34
 */
public class PageService {

    /**
     * @param pageSize
     * @param mapper
     * @param queryWrapper
     * @param forConsumer
     * @return void
     * @author: zhangxin
     * @description: 为了减少代码，这里封装通用的分页方法，
     * 只针对不需要写xml的简单SQL可以使用，复杂的SQL此方法不能使用
     * @date: 2022/5/26 9:54
     */
    public static <T> void processByPage(Integer pageSize, BaseMapper mapper, Wrapper<T> queryWrapper, ForConsumer forConsumer) {
        Integer pageIndex = 1;
        pageSize = ObjectUtils.isEmpty(pageSize) ? 2000 : pageSize;
        boolean flag = true;
        Predicate<List> predicate = s -> CollectionUtils.isEmpty(s);
        while (flag) {
            PageInfo<T> pageInfo = getPageUtil().page(pageIndex, pageSize, mapper, queryWrapper);
            if (predicate.test(pageInfo.getList())) {
                flag = false;
            }
            if (!CollectionUtils.isEmpty(pageInfo.getList())) {
                //执行处理操作
                forConsumer.accept(pageInfo.getList());
            }
            ++pageIndex;
        }
    }


    /**
     * @param pageSize
     * @param param
     * @param mapper
     * @param forConsumer
     * @return void
     * @author: zhangxin
     * @description: 针对复杂SQL ，分页只需要使用这个方法，但是参数取值，名称必须是pageListCondition
     * @date: 2022/5/26 10:03
     */
    public static <T, P extends BasePage, M extends BaseOperateMapper> void processByPage(Integer pageSize, P param, M mapper, ForConsumer forConsumer) {
        Integer pageIndex = 1;
        pageSize = ObjectUtils.isEmpty(pageSize) ? 2000 : pageSize;
        boolean flag = true;
        Predicate<List> predicate = s -> CollectionUtils.isEmpty(s);
        while (flag) {
            param.setPageIndex(pageIndex);
            param.setPageSize(pageSize);
            PageInfo<T> pageInfo = getPageUtil().page(param, mapper);
            if (!CollectionUtils.isEmpty(pageInfo.getList())) {
                //执行处理操作
                forConsumer.accept(pageInfo.getList());
            }
            if (predicate.test(pageInfo.getList())) {
                flag = false;
            }
            ++pageIndex;
        }
    }

    private static PageUtil getPageUtil() {
        return SpringBeanUtil.getBean(PageUtil.class);
    }


}

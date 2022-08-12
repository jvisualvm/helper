package com.risen.helper.translate.cache;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.risen.helper.cache.AgentCacheAbstract;
import com.risen.helper.config.SwitchConfig;
import com.risen.helper.translate.dto.DictItemValueDTO;
import com.risen.helper.translate.entity.DictItemEntity;
import com.risen.helper.translate.entity.DictTypeEntity;
import com.risen.helper.translate.mapper.DictItemMapper;
import com.risen.helper.translate.mapper.DictTypeMapper;
import com.risen.helper.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/16 14:45
 */
@Component
public class DictLoadCache extends AgentCacheAbstract<String, List<DictItemValueDTO>> {

    @Autowired
    private DictItemMapper dictItemMapper;

    @Autowired
    private DictTypeMapper dictTypeMapper;

    @Autowired
    private SwitchConfig switchConfig;

    public DictLoadCache() {
        super(null, null, null);
    }

    @Override
    public void loadCache() {
        if (switchConfig.getLogOpenWitch()) {
            LogUtil.info("start load dict cache...");
            List<DictItemEntity> itemList = dictItemMapper.selectList(new LambdaQueryWrapper());
            List<DictTypeEntity> typeList = dictTypeMapper.selectList(new LambdaQueryWrapper());
            Optional.ofNullable(typeList).ifPresent(type -> {
                Optional.ofNullable(itemList).ifPresent(item -> {
                    Map<String, List<DictItemEntity>> itemEntityMap = item.stream().collect(Collectors.groupingBy(DictItemEntity::getTypeCode));
                    Set<String> typeSetInfo = type.stream().map(s -> s.getTypeCode()).collect(Collectors.toSet());
                    itemEntityMap.forEach((k, v) -> {
                        if (typeSetInfo.contains(k)) {
                            put(k, v.stream().map(s -> {
                                return new DictItemValueDTO(s);
                            }).collect(Collectors.toList()));
                        }
                    });
                });
            });
        }
    }

    public String getDictNameByCacheKey(String typeCode, Object value) {
        AtomicReference<String> dictName = new AtomicReference("");
        Predicate<List> predicate = s -> !CollectionUtils.isEmpty(s);
        List<DictItemValueDTO> ListCache = get(typeCode);
        if (predicate.test(ListCache)) {
            ListCache.forEach(item -> {
                if (item.getItemCode().equals(String.valueOf(value))) {
                    dictName.set(item.getItemName());
                    return;
                }
            });
        }
        return dictName.get();
    }


}

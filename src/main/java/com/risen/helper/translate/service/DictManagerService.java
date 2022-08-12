package com.risen.helper.translate.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.risen.helper.page.PageUtil;
import com.risen.helper.translate.annotation.TranslateService;
import com.risen.helper.translate.cache.DictLoadCache;
import com.risen.helper.translate.dto.*;
import com.risen.helper.translate.entity.DictItemEntity;
import com.risen.helper.translate.entity.DictTypeEntity;
import com.risen.helper.translate.mapper.DictItemMapper;
import com.risen.helper.translate.mapper.DictTypeMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/15 19:44
 */
@Service
@AllArgsConstructor
public class DictManagerService {

    private DictItemMapper dictItemMapper;

    private DictTypeMapper dictTypeMapper;

    private PageUtil pageUtil;

    private DictLoadCache dictLoadCache;

    @Transactional
    public void updateItemById(DictItemUpdateDTO itemUpdate) {
        DictItemEntity dictItemEntity = new DictItemEntity();
        BeanUtils.copyProperties(itemUpdate, dictItemEntity);
        dictItemEntity.setUpdateTime(new Date());
        dictItemMapper.updateById(dictItemEntity);
    }

    @Transactional
    public void updateTypeById(DictTypeUpdateDTO dictTypeUpdateDTO) {
        DictTypeEntity dictTypeEntity = new DictTypeEntity();
        BeanUtils.copyProperties(dictTypeUpdateDTO, dictTypeEntity);
        dictTypeEntity.setUpdateTime(new Date());
        dictTypeMapper.updateById(dictTypeEntity);
    }

    @Transactional
    public Object insertItem(DictItemInsertDTO dictItemInsertDTO) {
        DictItemEntity dictItemEntity = new DictItemEntity();
        BeanUtils.copyProperties(dictItemInsertDTO, dictItemEntity);
        dictItemEntity.setCreateTime(new Date());
        dictItemMapper.insert(dictItemEntity);
        LambdaQueryWrapper<DictTypeEntity> itemWrapper = new LambdaQueryWrapper();
        itemWrapper.eq(StringUtils.isNotEmpty(dictItemInsertDTO.getTypeCode()), DictTypeEntity::getTypeCode, dictItemInsertDTO.getTypeCode());
        DictTypeEntity dictTypeEntity = dictTypeMapper.selectOne(itemWrapper);
        Optional.ofNullable(dictTypeEntity).ifPresent(item -> {
            dictItemEntity.setTypeId(dictTypeEntity.getId());
            dictItemMapper.updateById(dictItemEntity);
        });
        return dictItemEntity.getId();
    }

    @Transactional
    public Object insertType(DictTypeInsertDTO typeInsert) {
        DictTypeEntity dictTypeEntity = new DictTypeEntity();
        BeanUtils.copyProperties(typeInsert, dictTypeEntity);
        dictTypeEntity.setCreateTime(new Date());
        dictTypeMapper.insert(dictTypeEntity);
        return dictTypeEntity.getId();
    }


    @Transactional
    public void deleteItemById(Integer id) {
        dictItemMapper.deleteById(id);
    }


    @Transactional
    public void deleteTypeById(Integer id) {
        dictTypeMapper.deleteById(id);
    }


    public Object queryItemPage(DictItemQueryDTO dictItemQueryDTO) {
        LambdaQueryWrapper<DictItemEntity> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(dictItemQueryDTO.getItemCode()), DictItemEntity::getItemCode, dictItemQueryDTO.getItemCode());
        lambdaQueryWrapper.eq(ObjectUtils.isNotEmpty(dictItemQueryDTO.getTypeId()), DictItemEntity::getTypeId, dictItemQueryDTO.getTypeId());
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(dictItemQueryDTO.getTypeCode()), DictItemEntity::getTypeCode, dictItemQueryDTO.getTypeCode());
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(dictItemQueryDTO.getItemName()), DictItemEntity::getItemName, dictItemQueryDTO.getItemName());
        lambdaQueryWrapper.or().like(StringUtils.isNotEmpty(dictItemQueryDTO.getItemName()), DictItemEntity::getItemNameExt1, dictItemQueryDTO.getItemName());
        lambdaQueryWrapper.or().like(StringUtils.isNotEmpty(dictItemQueryDTO.getItemName()), DictItemEntity::getItemNameExt2, dictItemQueryDTO.getItemName());
        lambdaQueryWrapper.or().like(StringUtils.isNotEmpty(dictItemQueryDTO.getItemName()), DictItemEntity::getItemNameExt3, dictItemQueryDTO.getItemName());
        lambdaQueryWrapper.or().like(StringUtils.isNotEmpty(dictItemQueryDTO.getItemName()), DictItemEntity::getItemNameExt4, dictItemQueryDTO.getItemName());
        lambdaQueryWrapper.or().like(StringUtils.isNotEmpty(dictItemQueryDTO.getItemName()), DictItemEntity::getItemNameExt5, dictItemQueryDTO.getItemName());
        return pageUtil.page(dictItemQueryDTO.getPageIndex(), dictItemQueryDTO.getPageSize(), dictItemMapper, lambdaQueryWrapper);
    }


    public Object queryTypePage(DictTypeQueryDTO dictTypeQueryDTO) {
        LambdaQueryWrapper<DictTypeEntity> itemWrapper = new LambdaQueryWrapper();
        itemWrapper.eq(StringUtils.isNotEmpty(dictTypeQueryDTO.getTypeCode()), DictTypeEntity::getTypeCode, dictTypeQueryDTO.getTypeCode());
        itemWrapper.like(StringUtils.isNotEmpty(dictTypeQueryDTO.getTypeName()), DictTypeEntity::getTypeName, dictTypeQueryDTO.getTypeName());
        itemWrapper.or().like(StringUtils.isNotEmpty(dictTypeQueryDTO.getTypeName()), DictTypeEntity::getTypeExt1, dictTypeQueryDTO.getTypeName());
        return pageUtil.page(dictTypeQueryDTO.getPageIndex(), dictTypeQueryDTO.getPageSize(), dictTypeMapper, itemWrapper);
    }


    public Object queryAllTypeItem(DictAllItemQueryDTO dictAllItemQueryDTO) {
        LambdaQueryWrapper<DictItemEntity> dictWrapper = new LambdaQueryWrapper();
        dictWrapper.eq(StringUtils.isNotEmpty(dictAllItemQueryDTO.getTypeCode()), DictItemEntity::getTypeCode, dictAllItemQueryDTO.getTypeCode());
        dictWrapper.eq(ObjectUtils.isNotEmpty(dictAllItemQueryDTO.getTypeId()), DictItemEntity::getTypeId, dictAllItemQueryDTO.getTypeId());
        return dictItemMapper.selectList(dictWrapper);
    }

   public void refreshDictCache(){
       dictLoadCache.loadCache();
   }



}

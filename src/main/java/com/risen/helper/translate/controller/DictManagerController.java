package com.risen.helper.translate.controller;

import com.risen.helper.annotation.LogAnnotation;
import com.risen.helper.constant.OperateType;
import com.risen.helper.response.Result;
import com.risen.helper.response.ResultProxy;
import com.risen.helper.translate.dto.*;
import com.risen.helper.translate.service.DictManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/4/15 19:33
 */
@RestController
@RequestMapping("manager/dict")
@AllArgsConstructor
@Api(tags = "字典管理接口")
public class DictManagerController {

    private DictManagerService dictManagerService;

    @PutMapping("/item/update")
    @ApiOperation("更新字典项")
    @LogAnnotation(menu = "字典管理", module = "更新字典项", operateType = OperateType.UPDATE)
    public Result updateItemById(@Valid @RequestBody DictItemUpdateDTO dictItemUpdateDTO) {
        dictManagerService.updateItemById(dictItemUpdateDTO);
        return ResultProxy.build().successIgnoreBody();
    }

    @PutMapping("/type/update")
    @ApiOperation("更新字典类")
    @LogAnnotation(menu = "字典管理", module = "更新字典类", operateType = OperateType.UPDATE)
    public Result updateTypeById(@Valid @RequestBody DictTypeUpdateDTO dictTypeUpdateDTO) {
        dictManagerService.updateTypeById(dictTypeUpdateDTO);
        return ResultProxy.build().successIgnoreBody();
    }

    @PostMapping("/item/insert")
    @ApiOperation("新增字典项")
    @LogAnnotation(menu = "字典管理", module = "新增字典项", operateType = OperateType.INSERT)
    public Result insertItem(@Valid @RequestBody DictItemInsertDTO dictItemInsertDTO) {
        return ResultProxy.build().successWithBody(dictManagerService.insertItem(dictItemInsertDTO));
    }

    @PostMapping("/type/insert")
    @ApiOperation("新增字典类")
    @LogAnnotation(menu = "字典管理", module = "新增字典类", operateType = OperateType.INSERT)
    public Result insertType(@Valid @RequestBody DictTypeInsertDTO typeInsert) {
        return ResultProxy.build().successWithBody(dictManagerService.insertType(typeInsert));
    }

    @DeleteMapping("/item/delete/{id}")
    @ApiOperation("删除字典项")
    @LogAnnotation(menu = "字典管理", module = "删除字典项", operateType = OperateType.DELETE)
    public Result deleteItemById(@PathVariable(value = "id") Integer id) {
        dictManagerService.deleteItemById(id);
        return ResultProxy.build().successIgnoreBody();
    }


    @DeleteMapping("/type/update/{id}")
    @ApiOperation("删除字典类")
    @LogAnnotation(menu = "字典管理", module = "删除字典类", operateType = OperateType.DELETE)
    public Result deleteTypeById(@PathVariable(value = "id") Integer id) {
        dictManagerService.deleteTypeById(id);
        return ResultProxy.build().successIgnoreBody();
    }


    @GetMapping("/item/query")
    @ApiOperation("查询字典项")
    public Result queryItemPage(DictItemQueryDTO dictItemQueryDTO) {
        return ResultProxy.build().successWithBody(dictManagerService.queryItemPage(dictItemQueryDTO));
    }


    @GetMapping("/type/query")
    @ApiOperation("查询字典类")
    public Result queryTypePage(DictTypeQueryDTO dictTypeQueryDTO) {
        return ResultProxy.build().successWithBody(dictManagerService.queryTypePage(dictTypeQueryDTO));
    }


    @GetMapping("/type/list/item")
    @ApiOperation("查询字典类下面的字典项")
    public Result queryAllTypeItem(DictAllItemQueryDTO dictAllItemQueryDTO) {
        return ResultProxy.build().successWithBody(dictManagerService.queryAllTypeItem(dictAllItemQueryDTO));
    }



    @PostMapping("/cache/update")
    @ApiOperation("刷新字典缓存")
    @LogAnnotation(menu = "字典管理", module = "刷新字典缓存", operateType = OperateType.UPDATE)
    public Result refreshDictCache() {
        dictManagerService.refreshDictCache();
        return ResultProxy.build().successIgnoreBody();
    }


}

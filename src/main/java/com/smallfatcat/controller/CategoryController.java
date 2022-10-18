package com.smallfatcat.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smallfatcat.common.Result;
import com.smallfatcat.entity.Category;
import com.smallfatcat.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zsz
 * @Description-分类管理
 * @date 2022/10/6
 */
@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     *
     * @return
     */
    @PostMapping
    public Result<String> save(@RequestBody Category category) {
        boolean save = categoryService.save(category);
        if (save) {
            log.info("新增分类成功: {}", category);
            return Result.success("新增分类成功!");
        } else {
            return Result.success("新增分类失败!");
        }
    }

    /**
     * 分页查询分类管理
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize) {
        log.info("page = {},pageSize = {}", page, pageSize);
        //构造分页构造器
        Page<Category> pageInFfo = new Page<>(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加一个排序条件
        queryWrapper.orderByAsc(Category::getSort);
        //查询
        categoryService.page(pageInFfo, queryWrapper);
        return Result.success(pageInFfo);
    }

    /**
     * 根据id删除分类
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<String> delete(Long ids) {
        log.info("删除分类id为：{}", ids);
//        boolean b = categoryService.removeById(ids);
        categoryService.remove(ids);
        return Result.success("分类信息删除成功!");
    }

    /**
     * 根据id修改分类信息
     *
     * @param request
     * @param category
     * @return
     */
    @PutMapping
    public Result<String> update(HttpServletRequest request, @RequestBody Category category) {
        log.info("修改分类信息：{}", category);
        boolean b = categoryService.updateById(category);
        if (b) {
            return Result.success("修改分类信息成功!");
        } else {
            return Result.error("修改分类信息失败!");
        }
    }

    /**
     * 根据条件查询分类数据
     *
     * @param category
     * @return
     */
    @GetMapping("/list")
    public Result<List<Category>> list(Category category) {
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件,通过type类型查询
        queryWrapper.eq(category.getType() != null, Category::getType, category.getType());
        //添加排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(queryWrapper);
        return Result.success(list);
    }


}

package com.smallfatcat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smallfatcat.entity.Category;

/**
 * @author zsz
 * @Description
 * @date 2022/10/6
 */
public interface CategoryService extends IService<Category> {

    public void remove(Long id);

}

package com.timi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.timi.entity.Category;
import com.timi.utils.ResponseResult;
import com.timi.vo.CategoryVo;

public interface CategoryService extends IService<Category> {
    ResponseResult getCategoryList();

    ResponseResult listAllCategory();

    ResponseResult list(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addCategory(CategoryVo categoryVo);

    ResponseResult getCategory(Long id);

    ResponseResult updateCategory(CategoryVo categoryVo);

    ResponseResult deleteCategory(Long id);

    ResponseResult changeCategory(CategoryVo categoryVo);
}

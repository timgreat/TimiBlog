package com.timi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.timi.entity.Category;
import com.timi.utils.ResponseResult;

public interface CategoryService extends IService<Category> {
    ResponseResult getCategoryList();

}

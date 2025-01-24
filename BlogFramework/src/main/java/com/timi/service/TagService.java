package com.timi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.timi.dto.TagDto;
import com.timi.entity.Tag;
import com.timi.entity.Tag;
import com.timi.utils.ResponseResult;

/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2024-12-31 11:42:03
 */
public interface TagService extends IService<Tag> {

    ResponseResult list(Integer pageNum, Integer pageSize, TagDto tagDto);

    ResponseResult addTag(Tag tag);

    ResponseResult deleteTag(Long id);


    ResponseResult getTagById(Long id);

    ResponseResult updateTag(Tag tag);

    ResponseResult listAllTag();
}


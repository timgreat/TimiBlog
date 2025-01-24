package com.timi.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timi.dto.TagDto;
import com.timi.entity.Tag;
import com.timi.exception.SystemException;
import com.timi.mapper.TagMapper;
import com.timi.utils.AppHttpCodeEnum;
import com.timi.utils.BeanCopyUtils;
import com.timi.utils.ResponseResult;
import com.timi.vo.PageVo;
import com.timi.vo.TagVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2024-12-31 11:42:04
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult list(Integer pageNum, Integer pageSize, TagDto tagDto) {
        LambdaQueryWrapper<Tag> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(tagDto.getName()),Tag::getName,tagDto.getName());
        queryWrapper.like(StringUtils.hasText(tagDto.getRemark()),Tag::getRemark,tagDto.getRemark());

        Page<Tag> page=new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<TagVo> tagVos= BeanCopyUtils.copyBeanList(page.getRecords(),TagVo.class);
        PageVo pageVo=new PageVo(tagVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(Tag tag) {
        if(!StringUtils.hasText(tag.getName())){
            throw new SystemException(AppHttpCodeEnum.TAGNAME_NOT_NULL);
        }
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTagById(Long id) {
        Tag tag=getById(id);
        if(tag==null){
            throw new SystemException(AppHttpCodeEnum.TAGID_NOT_EXIST);
        }
        TagVo tagVo=BeanCopyUtils.copyBean(tag,TagVo.class);
        return ResponseResult.okResult(tagVo);
    }

    @Override
    public ResponseResult updateTag(Tag tag) {
        if(!StringUtils.hasText(tag.getName())){
            throw new SystemException(AppHttpCodeEnum.TAGNAME_NOT_NULL);
        }
        updateById(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllTag() {
        LambdaQueryWrapper<Tag> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId,Tag::getName);
        List<Tag> tags=list(queryWrapper);
        List<TagVo> tagVos=BeanCopyUtils.copyBeanList(tags,TagVo.class);
        return ResponseResult.okResult(tagVos);
    }

}


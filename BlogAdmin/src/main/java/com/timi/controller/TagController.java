package com.timi.controller;

import com.aliyun.oss.common.comm.ResponseMessage;
import com.timi.dto.TagDto;
import com.timi.entity.Tag;
import com.timi.service.TagService;
import com.timi.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, TagDto tagDto){
        return tagService.list(pageNum,pageSize,tagDto);
    }
    @PostMapping("")
    public ResponseResult addTag(@RequestBody Tag tag){
        return tagService.addTag(tag);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable("id") Long id){
        return tagService.deleteTag(id);
    }
    @GetMapping("/{id}")
    public ResponseResult getTagById(@PathVariable("id")Long id){
        return tagService.getTagById(id);
    }
    @PutMapping("")
    public ResponseResult updateTag(@RequestBody Tag tag){
        return tagService.updateTag(tag);
    }
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
    }
}

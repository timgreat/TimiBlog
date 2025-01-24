package com.timi.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.timi.service.LinkService;
import com.timi.utils.ResponseResult;
import com.timi.vo.LinkVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    private LinkService linkService;
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum,Integer pageSize,String name,String status){
        return linkService.list(pageNum,pageSize,name,status);
    }
    @PostMapping("")
    public ResponseResult addLink(@RequestBody LinkVo linkVo){
        return linkService.addLink(linkVo);
    }
    @GetMapping("/{id}")
    public ResponseResult getLink(@PathVariable("id")Long id){
        return linkService.getLink(id);
    }
    @PutMapping("")
    public ResponseResult updateLink(@RequestBody LinkVo linkVo){
        return linkService.updateLink(linkVo);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable("id")Long id){
        return linkService.deleteLink(id);
    }
//    @PutMapping("/changeStatus")
//    public ResponseResult changeStatus(@RequestBody LinkVo linkVo){
//        return linkService.changeStatus(linkVo);
//    }
}

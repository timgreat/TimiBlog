package com.timi.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timi.entity.Link;
import com.timi.mapper.LinkMapper;
import com.timi.utils.BeanCopyUtils;
import com.timi.utils.ResponseResult;
import com.timi.utils.SysConstant;
import com.timi.vo.LinkVo;
import com.timi.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService{

    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SysConstant.LINK_STATUS_NORMAL);
        List<Link> links=list(queryWrapper);
        List<LinkVo> linkVos=BeanCopyUtils.copyBeanList(links, LinkVo.class);

        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult list(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Link> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(name),Link::getName,name);
        queryWrapper.eq(StringUtils.hasText(status),Link::getStatus,status);
        Page<Link> pages=new Page<>(pageNum,pageSize);
        page(pages,queryWrapper);
        List<LinkVo> linkVos=BeanCopyUtils.copyBeanList(pages.getRecords(), LinkVo.class);
        PageVo pageVo=new PageVo(linkVos,pages.getTotal());
        return ResponseResult.okResult(pageVo);

    }

    @Override
    public ResponseResult addLink(LinkVo linkVo) {
        Link link=BeanCopyUtils.copyBean(linkVo, Link.class);
        save(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getLink(Long id) {
        LinkVo linkVo=BeanCopyUtils.copyBean(getById(id), LinkVo.class);
        return ResponseResult.okResult(linkVo);
    }

    @Override
    public ResponseResult updateLink(LinkVo linkVo) {
        Link link=BeanCopyUtils.copyBean(linkVo, Link.class);
        updateById(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteLink(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }
//    @Autowired
//    private LinkMapper linkMapper;
//    @Override
//    public ResponseResult changeStatus(LinkVo linkVo) {
//        LambdaUpdateWrapper<Link> updateWrapper=new LambdaUpdateWrapper<>();
//        updateWrapper.set(Link::getStatus,linkVo.getStatus())
//                .eq(Link::getId,linkVo.getId());
//        linkMapper.update(null,updateWrapper);
//        return ResponseResult.okResult();
//    }
}

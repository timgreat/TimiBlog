package com.timi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.timi.entity.Link;
import com.timi.utils.ResponseResult;
import com.timi.vo.LinkVo;

/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2024-12-18 16:47:43
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult list(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addLink(LinkVo linkVo);

    ResponseResult getLink(Long id);

    ResponseResult updateLink(LinkVo linkVo);

    ResponseResult deleteLink(Long id);

//    ResponseResult changeStatus(LinkVo linkVo);
}


package com.timi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.timi.entity.Menu;
import com.timi.utils.ResponseResult;
import com.timi.vo.MenuTreeVo;
import com.timi.vo.MenuVo;
import com.timi.vo.RoutersVo;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

public interface MenuService extends IService<Menu> {
    List<String> selectPermsByUserId(Long userId);

    List<MenuVo> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult listMenus(String status, String menuName);

    ResponseResult addMenu(Menu menu);

    ResponseResult selectMenu(Long id);

    ResponseResult updateMenu(Menu menu);

    ResponseResult deleteMenu(Long id);

    List<MenuTreeVo> treeSelect();

    List<Long> getMenuIds(Long roleId);
}

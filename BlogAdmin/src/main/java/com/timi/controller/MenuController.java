package com.timi.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.timi.entity.Menu;
import com.timi.service.MenuService;
import com.timi.utils.ResponseResult;
import com.timi.vo.MenuTreeVo;
import com.timi.vo.RoleMenuTreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @GetMapping("/list")
    public ResponseResult listMenus(String status, String menuName){
        return menuService.listMenus(status,menuName);
    }
    @PostMapping("")
    public ResponseResult addMenu(@RequestBody Menu menu){
        return menuService.addMenu(menu);
    }
    @GetMapping("/{id}")
    public ResponseResult selcetMenu(@PathVariable("id")Long id){
        return menuService.selectMenu(id);
    }
    @PutMapping("")
    public ResponseResult updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu);
    }
    @DeleteMapping("/{id}")
    public ResponseResult delteMenu(@PathVariable("id") Long id){
        return menuService.deleteMenu(id);
    }
    @GetMapping("/treeselect{id}")
    public ResponseResult treeSelect(){
        return ResponseResult.okResult(menuService.treeSelect());
    }
    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult roleMenuTree(@PathVariable("id") Long roleId){
        List<MenuTreeVo> menuTreeVos=menuService.treeSelect();
        List<Long> menuIds=menuService.getMenuIds(roleId);
        RoleMenuTreeVo roleMenuTreeVo=new RoleMenuTreeVo(menuTreeVos,menuIds);
        return ResponseResult.okResult(roleMenuTreeVo);
    }
}

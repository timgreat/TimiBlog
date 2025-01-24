package com.timi.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.timi.entity.Menu;
import com.timi.exception.SystemException;
import com.timi.mapper.MenuMapper;
import com.timi.utils.*;
import com.timi.vo.MenuTreeVo;
import com.timi.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService{
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public List<String> selectPermsByUserId(Long userId) {
        //如果是管理员，返回所有权限
        if(SecurityUtils.isAdmin()){
            return menuMapper.getAllPerms();
        }
        return menuMapper.selectPermsByUserId(userId);
    }

    @Override
    public List<MenuVo> selectRouterMenuTreeByUserId(Long userId) {
        //判断是否是管理员。如果是，返回所有menu
        List<Menu> menus=null;
        if(SecurityUtils.isAdmin()){
            menus=menuMapper.selectAllRouterMenu();
        }else {
            //如果不是管理员，返回当前用户的menu
            menus=menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        //构建tree
        //先找出第一层菜单，然后找出子菜单设置到children中
        List<MenuVo> menuVos= BeanCopyUtils.copyBeanList(menus,MenuVo.class);
        List<MenuVo> menuTree=getChildren(SysConstant.TOP_MENU_ID,menuVos);
        return menuTree;
    }
    public List<MenuVo> getChildren(Long id,List<MenuVo> menuVos){
        List<MenuVo> menuTree=menuVos.stream()
                .filter(m->m.getParentId().equals(id))
                .map(new Function<MenuVo, MenuVo>() {
                    @Override
                    public MenuVo apply(MenuVo menuVo) {
                        menuVo.setChildren(getChildren(menuVo.getId(),menuVos));
                        return menuVo;
                    }
                }).collect(Collectors.toList());
        return menuTree;
    }
    @Override
    public ResponseResult listMenus(String status,String menuName) {
        LambdaQueryWrapper<Menu> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(status),Menu::getStatus,status);
        queryWrapper.like(StringUtils.hasText(menuName),Menu::getMenuName,menuName);
        queryWrapper.orderByAsc(Menu::getParentId,Menu::getOrderNum);
        List<Menu> menus=list(queryWrapper);
        return ResponseResult.okResult(menus);
    }

    @Override
    public ResponseResult addMenu(Menu menu) {
        if(!StringUtils.hasText(menu.getMenuName())){
            throw new SystemException(AppHttpCodeEnum.MENUNAME_NOT_NULL);
        }
        menuMapper.insert(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectMenu(Long id) {
        Menu menu=getById(id);
        return ResponseResult.okResult(menu);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        if(menu.getParentId().equals(menu.getId())){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"修改菜单’"+menu.getMenuName()+"‘失败，上级菜单不能选择自己");
        }
        updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteMenu(Long id) {
        if(hasChildren(id)){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"存在子菜单不允许删除");
        }
        removeById(id);
        return ResponseResult.okResult();
    }

    public Boolean hasChildren(Long id){
        LambdaQueryWrapper<Menu> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId,id);
        int counts=count(queryWrapper);
        if(counts!=0){
            return true;
        }
        return false;
    }

    @Override
    public List<MenuTreeVo> treeSelect() {
        List<MenuTreeVo> menuTreeVos=getChilds(SysConstant.TOP_MENU_ID);
        return menuTreeVos;
    }

    public List<MenuTreeVo> getChilds(Long id){
        LambdaQueryWrapper<Menu> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId,id);
        List<Menu> menus=list(queryWrapper);
        if (menus==null){
            return null;
        }else {
            queryWrapper.orderByAsc(Menu::getParentId,Menu::getOrderNum);
            menus=list(queryWrapper);
            List<MenuTreeVo> menuTreeVos=menus.stream()
                            .map(new Function<Menu, MenuTreeVo>() {
                                @Override
                                public MenuTreeVo apply(Menu menu) {
                                    MenuTreeVo menuTreeVo=
                                            new MenuTreeVo(menu.getId(),menu.getMenuName(),menu.getParentId(),getChilds(menu.getId()));
                                    return menuTreeVo;
                                }
                            }).collect(Collectors.toList());
            return menuTreeVos;
        }
    }
    @Override
    public List<Long> getMenuIds(Long roleId) {
        List<Long> menuIds=menuMapper.selectRoleMenu(roleId);
        return menuIds;
    }


}

package com.hyy.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyy.server.pojo.Menu;
import com.hyy.server.pojo.MenuRole;
import com.hyy.server.pojo.RespBean;
import com.hyy.server.pojo.Role;
import com.hyy.server.service.IMenuRoleService;
import com.hyy.server.service.IMenuService;
import com.hyy.server.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Description
 * 权限组
 *
 * @author helaxest
 * @date 2021/03/17  6:46
 * @since
 */
@RestController
@RequestMapping("/system/basic/promise")
public class PromiseController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IMenuRoleService menuRoleService;

    @ApiOperation(value = "获取所有角色")
    @GetMapping("/")
    public List<Role> getAllPromises() {
        return roleService.list();
    }

    @ApiOperation(value = "添加角色")
    @PostMapping("/role")
    public RespBean addPromise(@RequestBody Role role) {
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());//不是ROLE_将其补充完整,security要求的
        }
        if (roleService.save(role)) {
            return RespBean.success("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/role/{rid}")
    public RespBean DeletePromise(@PathVariable Integer rid) {
        if (roleService.removeById(rid)) {
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败!");
    }

    @ApiOperation(value = "查询所有菜单")
    @GetMapping("/menus")
    public List<Menu> getAllMenus() {
        //菜单有对应的子菜单
        return menuService.getAllMenus();
    }

    @ApiOperation(value = "根据角色id查询菜单id")
    @GetMapping("/mid/{rid}")
    public List<Integer> getMidByRid(@PathVariable Integer rid) {
       return menuRoleService.list(new QueryWrapper<MenuRole>()
               .eq("rid",rid))
               .stream()
               .map(MenuRole::getMid)
               .collect(Collectors.toList());
    }
    @ApiOperation(value = "更新角色菜单")
    @PutMapping("/")
    public RespBean updateMenuRole(Integer rid,Integer[] mids){
        return menuRoleService.updateMenuRole(rid,mids);

    }
}

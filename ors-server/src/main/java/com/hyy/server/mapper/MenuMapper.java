package com.hyy.server.mapper;

import com.hyy.server.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author helaxest
 * @since 2021-03-15
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据id获取菜单列表
     * @return
     */
    List<Menu> getMenusByAdminId(Integer id);
    /**
     * 根据角色获取菜单列表
     * @return
     */
    List<Menu> getMenusWithRole();

    List<Menu> getAllMenus();
}

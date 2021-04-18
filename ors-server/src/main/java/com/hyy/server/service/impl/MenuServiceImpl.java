package com.hyy.server.service.impl;


import com.hyy.server.pojo.Menu;
import com.hyy.server.mapper.MenuMapper;
import com.hyy.server.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyy.server.utils.AdminUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author helaxest
 * @since 2021-03-15
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Resource
    private MenuMapper menuMapper;

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 根据用户id查询菜单列表
     * @return
     */
    @Override
    public List<Menu> getMenuByAdminId() {
     Integer adminId= AdminUtils.getCurrentAdmin().getId();

        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        //从Redis获取菜单数据
       List<Menu> menus= (List<Menu>) valueOperations.get("menu_" + adminId);
            if(CollectionUtils.isEmpty(menus)){
                 menus = menuMapper.getMenusByAdminId(adminId);
                 //将数据设置到Redis中
                 valueOperations.set("menu_"+ adminId,menus);
            }
        return menus ;
    }
    /**
     * 根据角色获取菜单列表
     * @return
     */
    @Override
    public List<Menu> getMenusWithRole() {
        return menuMapper.getMenusWithRole();
    }
    /**
     * 查询所有菜单
     * @return
     */
    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }
}

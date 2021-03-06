package com.hyy.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyy.server.pojo.MenuRole;
import com.hyy.server.mapper.MenuRoleMapper;
import com.hyy.server.pojo.RespBean;
import com.hyy.server.service.IMenuRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author helaxest
 * @since 2021-03-15
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {
    @Autowired
    private MenuRoleMapper menuRoleMapper;
    /**
     * 更新角色菜单
     * @param rid
     * @param mids
     * @return
     */
    @Override
    @Transactional
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid",rid));
        if(mids==null||mids.length==0){
            return RespBean.success("更新成功!");
        }
        //批量更新
        Integer result = menuRoleMapper.insertRecord(rid, mids);
        if(result==mids.length){
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败");
    }
}

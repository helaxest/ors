package com.hyy.server.service;

import com.hyy.server.pojo.MenuRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hyy.server.pojo.RespBean;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author helaxest
 * @since 2021-03-15
 */
public interface IMenuRoleService extends IService<MenuRole> {


    /**
     * 更新角色菜单
     * @param rid
     * @param mids
     * @return
     */
    RespBean updateMenuRole(@Param("rid") Integer rid, @Param("mids") Integer[] mids);
}

package com.hyy.server.mapper;

import com.hyy.server.pojo.Role;
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
public interface RoleMapper extends BaseMapper<Role> {
    /**
     *  根据用户id查询角色列表
     * @param adminId
     * @return
     */
    List<Role> getRoles(Integer adminId);
}

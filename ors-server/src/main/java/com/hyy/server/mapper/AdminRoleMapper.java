package com.hyy.server.mapper;

import com.hyy.server.pojo.AdminRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyy.server.pojo.RespBean;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author helaxest
 * @since 2021-03-15
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    Integer addAdminRole(Integer adminId, Integer[] rids);
}

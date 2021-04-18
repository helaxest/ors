package com.hyy.server.mapper;

import com.hyy.server.pojo.MenuRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author helaxest
 * @since 2021-03-15
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {
    /**
     *  更新角色菜单
     * @param rid
     * @param mids
     * @return
     */
    Integer insertRecord(Integer rid, Integer[] mids);
}

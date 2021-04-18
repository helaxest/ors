package com.hyy.server.mapper;

import com.hyy.server.pojo.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyy.server.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author helaxest
 * @since 2021-03-15
 */
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 获取所有操作员id
     * @param id
     * @param keywords
     * @return
     */
    List<Admin> getAllAdmins(@Param("id") Integer id, @Param("keywords") String keywords);

    List<Admin> getAdminByRole(@Param("id") Integer id, @Param("theRole") String theRole);

}

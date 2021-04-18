package com.hyy.server.service;

import com.hyy.server.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

import com.hyy.server.pojo.RespBean;
import com.hyy.server.pojo.Role;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author helaxest
 * @since 2021-03-15
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 登录之后返回token
     * @param username 用户名
     * @param password 密码
     * @param request
     * @param code
     * @return
     */
    RespBean login(String username, String password, HttpServletRequest request, String code);

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    Admin getAdminByUsername(String username);

    /**
     * 根据用户id查询角色
     * @param adminId
     * @return
     */
    List<Role> getRoles(Integer adminId);

    List<Admin> getAllAdmins(String keywords);

    RespBean addAdminRole(Integer adminId, Integer[] rids);

    /**
     * 更新用户头像
     * @param url
     * @param id
     * @param authentication
     * @return
     */
    RespBean updateAdminUserFace(String url, Integer id, Authentication authentication);

    List<Admin> getAdminByRole(String theRole);


}

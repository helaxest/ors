package com.hyy.server.controller;

import com.hyy.server.pojo.Admin;
import com.hyy.server.pojo.AdminLoginParam;
import com.hyy.server.pojo.RespBean;
import com.hyy.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Description
 * 登录
 *
 * @author helaxest
 * @date 2021/03/15  11:04
 * @since
 */
@Api(tags = "LoginController")
@RestController
public class LoginController {
    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "登录之后,返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request) {

        return adminService.login(adminLoginParam.getUsername(), adminLoginParam.getPassword(), request, adminLoginParam.getCode());
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/admin/info")//principal为security在登录成功后把对象设置到全局里
    public Admin getAdminInfo(Principal principal) {


        if (principal == null) {
            return null;//登录有问题,不提供任何信息
        }
        String username = principal.getName();//用户名
        Admin admin = adminService.getAdminByUsername(username);
        admin.setPassword(null);//为了安全，获得用户信息后就把密码设为空
        admin.setRoles(adminService.getRoles(admin.getId()));

        return admin;
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public RespBean logout() {
        return RespBean.success("注销成功");//前端收到状态码200,就把请求头里的token删除
    }
}

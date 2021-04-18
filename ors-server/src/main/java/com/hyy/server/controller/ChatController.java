package com.hyy.server.controller;

import com.hyy.server.pojo.Admin;
import com.hyy.server.service.IAdminService;
import com.hyy.server.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description
 *
 * @author helaxest
 * @date 2021/03/19  17:29
 * @since
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "获取所有操作员")
    @GetMapping("/admin")
    public List<Admin> getAllAdmins(String keywords) {

        return adminService.getAllAdmins(keywords);
    }
}

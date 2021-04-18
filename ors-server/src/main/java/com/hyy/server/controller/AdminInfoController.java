package com.hyy.server.controller;

import com.hyy.server.pojo.Admin;
import com.hyy.server.pojo.RespBean;
import com.hyy.server.service.IAdminService;
import com.hyy.server.utils.FastDFSUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Description
 *
 * @author helaxest
 * @date 2021/03/27  10:37
 * @since
 */
@RestController
@RequestMapping("/admin")
public class AdminInfoController {
    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "更新用户头像")
    @PostMapping("/userFace")
    public RespBean updateAdminUserFace(MultipartFile file, Integer id, Authentication authentication) {
        String[] filePath = FastDFSUtils.upload(file);
        String url = FastDFSUtils.getTrackerUrl() + filePath[0] + "/" + filePath[1];
        return adminService.updateAdminUserFace(url, id, authentication);
    }

    @ApiOperation(value = "更新当前用户信息")
    @PutMapping("/info")
    public RespBean updateAdmin(@RequestBody Admin admin, Authentication authentication) {
        if (adminService.updateById(admin)) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(admin,
                    null, authentication.getAuthorities()));
            return RespBean.success("更新成功");
        }
        return RespBean.success("更新失败");
    }

    @ApiOperation(value = "更新用户密码")

    @PutMapping("/pass")
    RespBean updateAdminPassWord(@RequestBody Map<String, Object> info) {
        String oldPass = (String) info.get("oldPass");
        String pass = (String) info.get("pass");
        Integer adminId = (Integer) info.get("adminId");
        return adminService.updateAdminPassWord(oldPass, pass, adminId);


    }


}

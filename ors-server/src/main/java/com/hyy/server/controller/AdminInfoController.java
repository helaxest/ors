package com.hyy.server.controller;

import com.hyy.server.pojo.RespBean;
import com.hyy.server.service.IAdminService;
import com.hyy.server.utils.FastDFSUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description
 *
 * @author helaxest
 * @date 2021/03/27  10:37
 * @since
 */
@RestController
@RequestMapping("/admin/Info")
public class AdminInfoController {
    @Autowired
    private IAdminService adminService;
    @ApiOperation(value = "更新用户头像")
    @PostMapping("/admin/userFace")
    public RespBean updateAdminUserFace(MultipartFile file, Integer id, Authentication authentication){
        String[] filePath = FastDFSUtils.upload(file);
      String url= FastDFSUtils.getTrackerUrl()+filePath[0]+"/"+filePath[1];
      return adminService.updateAdminUserFace(url,id,authentication);
    }


}

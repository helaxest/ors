package com.hyy.server.utils;

import com.hyy.server.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Description
 *
 * @author helaxest
 * @date 2021/03/19  11:12
 * @since
 */
public class AdminUtils {
    /**
     * 获取当前登录操作员
     * @return
     */
    public static Admin getCurrentAdmin(){
        return (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

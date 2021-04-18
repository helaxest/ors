package com.hyy.server.config.security.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyy.server.pojo.RespBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Description
 * 访问接口没有权限时,自定义返回结果
 *
 * @author helaxest
 * @date 2021/03/15  13:11
 * @since
 */
@Component
public class RestfulAccessDeniedHandle implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();//拿到输出流
        RespBean bean = RespBean.error("权限不足,请联系管理员!");
        bean.setCode(403);
        out.write(new ObjectMapper().writeValueAsString(bean));
        out.flush();
        out.close();
    }
}

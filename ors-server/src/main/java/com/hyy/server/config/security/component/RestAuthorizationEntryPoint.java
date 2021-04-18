package com.hyy.server.config.security.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyy.server.pojo.RespBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Description
 * 当未登录或者token失效 访问接口，自定义返回结果
 * @author helaxest
 * @date 2021/03/15  12:59
 * @since
 */
@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");//前后端分离用json传数据
        PrintWriter out = response.getWriter();//拿到输出流
        RespBean bean=RespBean.error("尚未登录,请登录");
        bean.setCode(401);
        out.write(new ObjectMapper().writeValueAsString(bean));//?????
        out.flush();
        out.close();
    }
}

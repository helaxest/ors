package com.hyy.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyy.server.config.security.component.JwtTokenUtil;
import com.hyy.server.mapper.AdminRoleMapper;
import com.hyy.server.mapper.RoleMapper;
import com.hyy.server.pojo.Admin;
import com.hyy.server.mapper.AdminMapper;
import com.hyy.server.pojo.AdminRole;
import com.hyy.server.pojo.RespBean;
import com.hyy.server.pojo.Role;
import com.hyy.server.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyy.server.utils.AdminUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author helaxest
 * @since 2021-03-15
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {
    @Autowired
    private UserDetailsService userDetailsService;//SpringSecurity里的接口
    @Autowired
    private PasswordEncoder passwordEncoder;//SpringSecurity里的加密
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")//从配置中拿到token的头部信息
    private String tokenHead;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private AdminRoleMapper adminRoleMapper;

    /**
     * 登录之后,返回token
     *
     * @param username 用户名
     * @param password 密码
     * @param request
     * @param code
     * @return
     */
    @Override
    public RespBean login(String username, String password, HttpServletRequest request, String code) {
        String captcha = ((String) request.getSession().getAttribute("captcha"));
        if (StringUtils.isEmpty(code) || !captcha.equals(code)) {
            return RespBean.error("验证码错误,请重新输入！");
        }
        //登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        String encode = passwordEncoder.encode("mypassword");
//        System.out.println("加密密码 :mypassword=====>" + encode);
        if (null == userDetails || !passwordEncoder.matches(password, userDetails.getPassword())) {

            return RespBean.error("用户名或密码不正确！");
        }
        if (!userDetails.isEnabled()) {
            return RespBean.error("账号被禁用,亲联系管理员！");
        }
        //更新security的登录对象  参数1放userDetails，参数2为凭证是密码,设为空,参数3为权限列表
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //登录成功后,jwt可以根据userDetails拿到令牌
        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);//头部信息让前端放在请求头里的
        return RespBean.success("登录成功", tokenMap);//返回给前端
    }

    /**
     * 根据用户名获取用户对象
     *
     * @param username
     * @return
     */
    @Override
    public Admin getAdminByUsername(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", username).eq("enabled", true));//账户是否禁用,bug,selectOne查出的数据
    }

    /**
     * 根据用户id查询角色列表
     *
     * @param adminId
     * @return
     */
    @Override
    public List<Role> getRoles(Integer adminId) {
        return roleMapper.getRoles(adminId);
    }

    @Override
    public List<Admin> getAllAdmins(String keywords) {
        return adminMapper.getAllAdmins(AdminUtils.getCurrentAdmin().getId(), keywords);
    }

    @Override
    @Transactional
    public RespBean addAdminRole(Integer adminId, Integer[] rids) {
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId",adminId));
        Integer result = adminRoleMapper.addAdminRole(adminId, rids);
        if(rids.length==result){
            return RespBean.success("更新成功");
        }
        return RespBean.success("更新失败") ;
    }

    @Override
    public RespBean updateAdminUserFace(String url, Integer id, Authentication authentication) {
        Admin admin = adminMapper.selectById(id);
        admin.setUserFace(url);
        int result = adminMapper.updateById(admin);
        if(1==result){
            Admin principal =(Admin) authentication.getPrincipal();
            principal.setUserFace(url);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(admin,null,authentication.getAuthorities()));
            return RespBean.success("更新成功",url);
        }
        return RespBean.error("更新失败");
    }

    @Override
    public List<Admin> getAdminByRole(String theRole) {
        return adminMapper.getAdminByRole(AdminUtils.getCurrentAdmin().getId(),theRole);
    }

    @Override
    public RespBean updateAdminPassWord(String oldPass, String pass, Integer adminId) {
        Admin admin = adminMapper.selectById(adminId);
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        if(encoder.matches(oldPass, admin.getPassword())){
            admin.setPassword(encoder.encode(pass));
            int result = adminMapper.updateById(admin);
            if(result==1){
                return  RespBean.success("更新成功");
            }
        }
        return RespBean.error("更新失败");
    }


}

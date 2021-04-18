package com.hyy.server.service.impl;

import com.hyy.server.pojo.Role;
import com.hyy.server.mapper.RoleMapper;
import com.hyy.server.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author helaxest
 * @since 2021-03-15
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}

package com.hyy.server.service.impl;

import com.hyy.server.pojo.Department;
import com.hyy.server.mapper.DepartmentMapper;
import com.hyy.server.pojo.RespBean;
import com.hyy.server.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author helaxest
 * @since 2021-03-15
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {
@Autowired
private DepartmentMapper departmentMapper;

    @Override
    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartments(-1);
    }

    @Override
    public RespBean addDep(Department dep) {
        dep.setEnabled(true);
        departmentMapper.addDep(dep);
        if(1==dep.getResult()){
            return RespBean.success("添加成功！",dep);
        }
        return RespBean.success("添加失败！");
    }

    @Override
    public RespBean deleteDep(Integer id) {
        Department dep=new Department();
        dep.setId(id);
        departmentMapper.deleteDep(dep);
        if(-2==dep.getResult()){
            return RespBean.error("该部门下还有子部门，删除失败!");
        }
        if(-1==dep.getResult()){
            return RespBean.error("该部门下还有员工，删除失败!");
        }
        if(1==dep.getResult()){
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败！");
    }

    @Override
    public List<Department> getAllDepartments2() {
        return departmentMapper.getAllDepartments2();
    }
}

package com.hyy.server.service;

import com.hyy.server.pojo.Department;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hyy.server.pojo.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author helaxest
 * @since 2021-03-15
 */
public interface IDepartmentService extends IService<Department> {



    List<Department> getAllDepartments();

    RespBean addDep(Department dep);

    RespBean deleteDep(Integer id);

    List<Department> getAllDepartments2();
}

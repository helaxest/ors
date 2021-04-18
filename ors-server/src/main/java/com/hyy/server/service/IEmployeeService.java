package com.hyy.server.service;

import com.hyy.server.pojo.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hyy.server.pojo.RespBean;
import com.hyy.server.pojo.RespPageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author helaxest
 * @since 2021-03-15
 */
public interface IEmployeeService extends IService<Employee> {

    RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);

    RespBean maxWorkID();

    RespBean addEmp(Employee employee);

    List<Employee> getEmployee(Integer id);
}

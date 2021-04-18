package com.hyy.server.service;

import com.hyy.server.pojo.Expense;
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
 * @since 2021-04-05
 */
public interface IExpenseService extends IService<Expense> {

    RespPageBean getExpenseByPage(Integer currentPage, Integer size, Expense expense, LocalDate[] beginDateScope);

    RespBean addExp(Expense expense);

    RespBean updateExp(Expense expense);

    RespBean sendEmail(Expense expense);

    List<Expense> getExpense(Integer id);

    RespBean deleteExpById(Integer id);
}

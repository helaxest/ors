package com.hyy.server.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyy.server.pojo.Expense;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author helaxest
 * @since 2021-04-05
 */
public interface ExpenseMapper extends BaseMapper<Expense> {

    IPage<Expense> getExpenseByPage(Page<Expense> page, @Param("expense") Expense expense,@Param("beginDateScope") LocalDate[] beginDateScope);

    List<Expense> getExpense(Integer id);
}

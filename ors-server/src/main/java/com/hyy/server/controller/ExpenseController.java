package com.hyy.server.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.hyy.server.pojo.Employee;
import com.hyy.server.pojo.Expense;
import com.hyy.server.pojo.RespBean;
import com.hyy.server.pojo.RespPageBean;
import com.hyy.server.service.IExpenseService;
import com.sun.deploy.net.URLEncoder;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author helaxest
 * @since 2021-04-05
 */
@RestController
@RequestMapping("/expense/basic")
public class ExpenseController {

    @Autowired
    private IExpenseService expenseService;
    @ApiOperation(value = "获取所有报销单(分页)")
    @GetMapping("/")
    public RespPageBean getExpense(@RequestParam(defaultValue = "1") Integer currentPage,
                                    @RequestParam(defaultValue = "10") Integer size,
                                    Expense expense, LocalDate[] beginDateScope) {
        return expenseService.getExpenseByPage(currentPage, size, expense, beginDateScope);
    }
    @ApiOperation(value = "添加报销单")
    @PostMapping("/")
    public RespBean addExp(@RequestBody Expense expense){
        return expenseService.addExp(expense);
    }
    @ApiOperation(value = "更新报销单")
    @PutMapping("/")
    public RespBean updateExp(@RequestBody Expense expense){
        return expenseService.updateExp(expense);
    }
    @ApiOperation(value = "删除报销单")
    @DeleteMapping("/{id}")
    public RespBean deleteExp(@PathVariable Integer id){
         return expenseService.deleteExpById(id);
    }
    @ApiOperation(value = "发通知")
    @PutMapping("/message")
    public RespBean sendMessage(@RequestBody Expense expense){
        return expenseService.sendEmail(expense);
    }


    @ApiOperation(value = "导出财务数据")
    @GetMapping(value = "/export",produces ="application/octet-stream")
    public void exportEmployee(HttpServletResponse response){
        List<Expense> list = expenseService.getExpense(null);
        ExportParams params=new ExportParams("财务报销表","财务报销表", ExcelType.HSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, Expense.class, list);
        ServletOutputStream out=null;
        try {
            //流的形式
            response.setHeader("content-type","application/octet-stream");
            //防止中文乱码
            response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode("财务报销表.xls","UTF-8"));
            out = response.getOutputStream();
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null!=out){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}

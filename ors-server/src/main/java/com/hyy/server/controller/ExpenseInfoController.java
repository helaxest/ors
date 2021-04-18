package com.hyy.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyy.server.pojo.Expense;
import com.hyy.server.pojo.RespBean;
import com.hyy.server.service.IExpenseService;
import com.hyy.server.utils.FastDFSUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Description
 *
 * @author helaxest
 * @date 2021/03/27  10:37
 * @since
 */
@RestController
@RequestMapping("/expense/Info")
public class ExpenseInfoController {

    @Autowired
    private IExpenseService expenseService;

    @ApiOperation(value = "上传文件")
    @PostMapping("/upload")
    public String uploadAttachment(MultipartFile file, Integer id, Authentication authentication) {
        String[] filePath = FastDFSUtils.upload(file);
        String url = FastDFSUtils.getTrackerUrl() + filePath[0] + "/" + filePath[1];
        System.out.println(url);
        return url;
    }
    @ApiOperation("根据部门查询报销单")
    @GetMapping("/groupByDep")
    public RespBean getData1(){
        QueryWrapper<Expense> qw = new QueryWrapper<>();
        qw.select("depName as name,sum(money) as value");//查询自定义列
        qw.groupBy("depName");
          //listMaps方法查询
        List<Map<String, Object>> maps = expenseService.listMaps(qw);
        return RespBean.success("获取数据成功",maps);
    }
}

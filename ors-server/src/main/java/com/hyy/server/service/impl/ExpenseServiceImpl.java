package com.hyy.server.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyy.server.mapper.MailLogMapper;
import com.hyy.server.pojo.*;
import com.hyy.server.mapper.ExpenseMapper;
import com.hyy.server.service.IExpenseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyy.server.utils.AdminUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author helaxest
 * @since 2021-04-05
 */
@Service
public class ExpenseServiceImpl extends ServiceImpl<ExpenseMapper, Expense> implements IExpenseService {

    @Autowired
    private ExpenseMapper expenseMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MailLogMapper mailLogMapper;
    @Override
    public RespPageBean getExpenseByPage(Integer currentPage, Integer size, Expense expense, LocalDate[] beginDateScope) {

        //开启分页
        Page<Expense> page = new Page<>(currentPage, size);
        IPage<Expense> expenseByPage = expenseMapper.getExpenseByPage(page, expense, beginDateScope);
        RespPageBean respPageBean = new RespPageBean(expenseByPage.getTotal(), expenseByPage.getRecords());
        return respPageBean;
    }

    /**
     * 添加报销单
     *
     * @param expense
     * @return
     */
    @Override
    public RespBean addExp(Expense expense) {
        expense.setApplyName(AdminUtils.getCurrentAdmin().getName());//设置申请人
        expense.setApplyId(AdminUtils.getCurrentAdmin().getId());//设置申请人id
        expense.setCreateDate(LocalDate.now());//设置报销单创建日期
        expense.setUpdateDate(LocalDate.now());//设置报销单最近一次更新日期
        expense.setDealWay("提交");
        if (1 == expenseMapper.insert(expense)) {
            return RespBean.success("添加成功");
        }
        return RespBean.success("添加失败");
    }

    /**
     * 更新报销单
     *
     * @param expense
     * @return
     */
    @Override
    public RespBean updateExp(Expense expense) {
        synchronized (this) {
            Expense one = expenseMapper.selectById(expense.getId());
            if(one.getDealWay().equals("已阅")||null==one){
                return RespBean.error("该数据已被修改,请刷新页面");
            }
            expense.setDealName(AdminUtils.getCurrentAdmin().getName());//设置审核人姓名
            expense.setDealId(AdminUtils.getCurrentAdmin().getId());//设置审核人id
            expense.setUpdateDate(LocalDate.now());//设置报销单最近一次更新日期
            expense.setDealWay("已阅");
            if (1 == expenseMapper.updateById(expense)) {
                return RespBean.success("操作成功");
            }
        }
        return RespBean.error("操作失败");
    }

    @Override
    public RespBean sendEmail(Expense expense) {
        expense.setDealWay("已经邮件通知");
        if(1==expenseMapper.updateById(expense)){
            Expense exp = expenseMapper.getExpense(expense.getId()).get(0);//查出刚插入的报销单
            String msgId = UUID.randomUUID().toString();
            MailLog mailLog = new MailLog();
            mailLog.setMsgId(msgId);
            mailLog.setEid(expense.getId());
            mailLog.setStatus(0);
            mailLog.setRouteKey(MailConstants.MAIL_ROUTING_KEY_NAME);
            mailLog.setExchange(MailConstants.MAIL_EXCHANGE_NAME);
            mailLog.setCount(0);
            mailLog.setTryTime(LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT));
            mailLog.setCreateTime(LocalDateTime.now());
            mailLog.setUpdateTime(LocalDateTime.now());
            mailLogMapper.insert(mailLog);
            // 发送消息
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME, MailConstants.MAIL_ROUTING_KEY_NAME, exp, new CorrelationData(msgId));
            return RespBean.success("添加成功！");
        }
        return RespBean.error("添加失败!");
    }
    @Override
    public List<Expense> getExpense(Integer id) {
        return expenseMapper.getExpense(id);
    }

    @Override
    public RespBean deleteExpById(Integer id) {

        Expense one = expenseMapper.selectById(id);
        if(null!=one&&one.getDealWay().equals("提交")){
            System.out.println(one);
            expenseMapper.deleteById(one);
            return RespBean.success("删除成功");
        }
        return RespBean.error("数据已被修改,删除失败");
    }


}

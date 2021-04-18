package com.hyy.server.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hyy.server.pojo.Employee;
import com.hyy.server.pojo.Expense;
import com.hyy.server.pojo.MailConstants;
import com.hyy.server.pojo.MailLog;
import com.hyy.server.service.IEmployeeService;
import com.hyy.server.service.IExpenseService;
import com.hyy.server.service.IMailLogService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Description
 * 邮件发送定时任务
 * @author helaxest
 * @date 2021/03/31  15:26
 * @since
 */
@Component
public class MailTask {
    @Autowired
    private IMailLogService mailLogService;

//    @Autowired
//    private IEmployeeService employeeService;

    @Autowired
    private IExpenseService expenseService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 邮件发送定时任务
     * 10s执行
     */
   /* @Scheduled(cron = "0/10 * * * * ?")
    public void mailTask(){
    List<MailLog> list= mailLogService.list(new QueryWrapper<MailLog>().eq("status",0).lt("tryTime", LocalDateTime.now()));
        list.forEach((mailLog)->{
            //如果重试次数超过三次,更新状态投递失败,不在重试
            if(3<=mailLog.getCount()){
                mailLogService.update(new UpdateWrapper<MailLog>().set("status",2).eq("msgId",mailLog.getMsgId()));
            }
            mailLogService.update(new UpdateWrapper<MailLog>().set("count",mailLog.getCount()+1)
                    .set("updateTime",LocalDateTime.now())
                    .set("tryTime",LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT))
                    .eq("msgId",mailLog.getMsgId()));
          Employee emp=  employeeService.getEmployee(mailLog.getEid()).get(0);
          rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,MailConstants.MAIL_ROUTING_KEY_NAME,emp,new CorrelationData(mailLog.getMsgId()));
        });
    }
    */


    /**
     * 邮件发送定时任务
     * 10s执行
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void mailTask(){
        List<MailLog> list= mailLogService.list(new QueryWrapper<MailLog>().eq("status",0).lt("tryTime", LocalDateTime.now()));
        list.forEach((mailLog)->{
            //如果重试次数超过三次,更新状态投递失败,不在重试
            if(3<=mailLog.getCount()){
                mailLogService.update(new UpdateWrapper<MailLog>().set("status",2).eq("msgId",mailLog.getMsgId()));
            }
            mailLogService.update(new UpdateWrapper<MailLog>().set("count",mailLog.getCount()+1)
                    .set("updateTime",LocalDateTime.now())
                    .set("tryTime",LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT))
                    .eq("msgId",mailLog.getMsgId()));
            Expense exp=expenseService.getExpense(mailLog.getEid()).get(0);

            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,MailConstants.MAIL_ROUTING_KEY_NAME,exp,new CorrelationData(mailLog.getMsgId()));
        });
    }
}

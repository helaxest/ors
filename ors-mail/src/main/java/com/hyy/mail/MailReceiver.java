package com.hyy.mail;



import com.hyy.server.pojo.Expense;
import com.hyy.server.pojo.MailConstants;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;

/**
 * Description
 *
 * @author helaxest
 * @date 2021/03/31  12:17
 * @since
 */
@Component
public class MailReceiver {
    private static  final Logger Logger= LoggerFactory.getLogger(MailReceiver.class);
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailProperties mailProperties;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitListener(queues= MailConstants.MAIL_QUEUE_NAME)
    public void handler(Message message, Channel channel){
//      Employee employee= (Employee) message.getPayload();
        Expense expense=(Expense) message.getPayload();
      MessageHeaders headers=message.getHeaders();
      //消息序号
        long tag = (long) headers.get(AmqpHeaders.DELIVERY_TAG);
        String msgId = (String)headers.get("spring_returned_message_correlation");
        HashOperations hashOperations = redisTemplate.opsForHash();

        try {
            if(hashOperations.entries("mail_log").containsKey(msgId)){
                Logger.error("消息已被消费================>{}",msgId);
                /**
                 * 手动确认消息
                 * tag:消息序号
                 * multiple:是否确认多条
                 */
                channel.basicAck(tag,false);
                return;
            }
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(msg);
            helper.setFrom(mailProperties.getUsername());
            helper.setTo(expense.getEmail());
            helper.setSubject("入职欢迎邮件");
            helper.setSentDate(new Date());
            Context context=new Context();
            context.setVariable("name", expense.getApplyName());
            context.setVariable("money", expense.getMoney());
            String mail = templateEngine.process("mail", context);
            helper.setText(mail,true);
            javaMailSender.send(msg);
            Logger.info("邮件发送成功");
            //将消息id存入redis
            hashOperations.put("mail_log",msgId,"ok");
            //手动确认消息
            channel.basicAck(tag,false);
        } catch (Exception e) {
            /**
             * 手动确认消息
             * tag:消息序号
             * 是否确认多条
             * 是否退回到队列
             */
            try {
                channel.basicNack(tag,false,true);
            } catch (IOException ioException) {
                Logger.error("邮件发送失败=======>{}",e.getMessage());
            }
            Logger.error("邮件发送失败=======>{}",e.getMessage());
        }
    }

}

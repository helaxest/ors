package com.hyy.server.pojo;

/**
 * Description
 * 消息状态
 *
 * @author helaxest
 * @date 2021/03/31  14:34
 * @since
 */
public class MailConstants {
    //消息投递中
    public static final Integer DELIVERING = 0;
    //成功
    public static final Integer SUCCESS = 1;
    //失败
    public static final Integer FAILING = 2;
    //最大重试次数
    public static final Integer MAX_TRY_COUNT = 3;

    //消息超时时间
    public static final Integer MSG_TIMEOUT = 1;

    public static final String MAIL_QUEUE_NAME = "mail.queue";

    public static final String MAIL_EXCHANGE_NAME = "mail.exchange";
    //路由键
    public static final String MAIL_ROUTING_KEY_NAME = "mail.routing.key";
}

package com.hyy.server.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Description
 *
 * @author helaxest
 * @date 2021/03/18  10:43
 * @since
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChatMsg {
    private  String from;
    private  String to;
    private  String content;
    private LocalDateTime date;
    private  String formNickName;

}

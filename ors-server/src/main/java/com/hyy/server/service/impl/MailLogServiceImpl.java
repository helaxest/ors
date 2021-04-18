package com.hyy.server.service.impl;

import com.hyy.server.pojo.MailLog;
import com.hyy.server.mapper.MailLogMapper;
import com.hyy.server.service.IMailLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author helaxest
 * @since 2021-03-15
 */
@Service
public class MailLogServiceImpl extends ServiceImpl<MailLogMapper, MailLog> implements IMailLogService {

}

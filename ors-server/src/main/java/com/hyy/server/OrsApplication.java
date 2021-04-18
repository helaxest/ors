package com.hyy.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Description
 * 启动类
 * @author helaxest
 * @date 2021/03/15  8:53
 * @since
 */
@SpringBootApplication
//mapper包开启扫描
@MapperScan("com.hyy.server.mapper")
@EnableScheduling
public class OrsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrsApplication.class,args);
    }
}

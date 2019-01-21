/*
 *Copyright (C) 2018 HangZhou Hikvision System Technology Co., Ltd. All Right Reserved.
 *@className Application
 *@description http://www.hikvision.com
 *@author ruanshubin
 *@date 2019/1/21 21:04
 *@version 0.1
 */
package com.ruanshubin.springboot.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

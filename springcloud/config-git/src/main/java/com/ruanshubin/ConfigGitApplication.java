/*
 *Copyright (C) 2018 HangZhou Hikvision System Technology Co., Ltd. All Right Reserved.
 *@className ConfigGitApplication
 *@description http://www.hikvision.com
 *@author ruanshubin
 *@date 2019/3/26 17:11
 *@version 1.0
 */
package com.ruanshubin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ConfigGitApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigGitApplication.class, args);
    }
}



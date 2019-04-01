/*
 *Copyright (C) 2018 HangZhou Hikvision System Technology Co., Ltd. All Right Reserved.
 *@className ConfigGitConsulApplication
 *@description http://www.hikvision.com
 *@author ruanshubin
 *@date 2019/3/26 20:50
 *@version 1.0
 */
package com.ruanshubin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigServer
public class ConfigGitConsulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigGitConsulApplication.class, args);
    }
}

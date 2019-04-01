/*
 *Copyright (C) 2018 HangZhou Hikvision System Technology Co., Ltd. All Right Reserved.
 *@className ConfigClientConsulApp
 *@description http://www.hikvision.com
 *@author ruanshubin
 *@date 2019/3/27 10:12
 *@version 1.0
 */
package com.ruanshubin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ConfigClientConsulApp {
    public static void main(String[] args) {
        SpringApplication.run(ConfigClientConsulApp.class, args);
    }
}

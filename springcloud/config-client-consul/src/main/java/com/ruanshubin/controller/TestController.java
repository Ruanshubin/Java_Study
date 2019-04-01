/*
 *Copyright (C) 2018 HangZhou Hikvision System Technology Co., Ltd. All Right Reserved.
 *@className TestController
 *@description http://www.hikvision.com
 *@author ruanshubin
 *@date 2019/3/26 20:19
 *@version 1.0
 */
package com.ruanshubin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class TestController {

    @Value("${from}")
    private String from;

    @RequestMapping("/from")
    public String from(){
        return this.from;
    }
}

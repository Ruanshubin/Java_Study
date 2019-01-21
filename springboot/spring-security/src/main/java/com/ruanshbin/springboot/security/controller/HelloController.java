/*
 *Copyright (C) 2018 HangZhou Hikvision System Technology Co., Ltd. All Right Reserved.
 *@className HelloController
 *@description http://www.hikvision.com
 *@author ruanshubin
 *@date 2019/1/21 20:11
 *@version 0.1
 */
package com.ruanshbin.springboot.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
  *@description:
  *@author: ruanshubin 2019/1/21 20:11
  *@version v0.1
*/

@Controller
public class HelloController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}

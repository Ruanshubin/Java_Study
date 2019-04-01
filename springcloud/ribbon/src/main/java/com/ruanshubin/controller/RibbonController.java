/*
 *Copyright (C) 2018 HangZhou Hikvision System Technology Co., Ltd. All Right Reserved.
 *@className RibbonController
 *@description http://www.hikvision.com
 *@author ruanshubin
 *@date 2019/3/26 14:03
 *@version 1.0
 */
package com.ruanshubin.controller;

import com.ruanshubin.service.ComputeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;

@RestController
public class RibbonController {

    @Autowired
    RestTemplate restTemplate;

    @Resource
    ComputeService computeService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return restTemplate.getForEntity("http://COMPUTE-SERVICE-B/add?a=10&b=20", String.class).getBody();
    }

    @RequestMapping(value = "/addHystrix", method = RequestMethod.GET)
    public String addHystrix() {
        return computeService.addService();
    }
}

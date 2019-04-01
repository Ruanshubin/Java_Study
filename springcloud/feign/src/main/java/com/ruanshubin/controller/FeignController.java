/*
 *Copyright (C) 2018 HangZhou Hikvision System Technology Co., Ltd. All Right Reserved.
 *@className FeignController
 *@description http://www.hikvision.com
 *@author ruanshubin
 *@date 2019/3/26 14:45
 *@version 1.0
 */
package com.ruanshubin.controller;

import com.ruanshubin.service.ComputeClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class FeignController {

    @Resource
    private ComputeClient computeClient;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Integer add() {
        return computeClient.add(10, 20);
    }

}

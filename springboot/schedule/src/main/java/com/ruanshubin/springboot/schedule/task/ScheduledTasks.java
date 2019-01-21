/*
 *Copyright (C) 2018 HangZhou Hikvision System Technology Co., Ltd. All Right Reserved.
 *@className ScheduledTasks
 *@description http://www.hikvision.com
 *@author ruanshubin
 *@date 2019/1/21 21:06
 *@version 0.1
 */
package com.ruanshubin.springboot.schedule.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime(){
        System.out.println("当前时间为： " + dateFormat.format(new Date()));
    }
}

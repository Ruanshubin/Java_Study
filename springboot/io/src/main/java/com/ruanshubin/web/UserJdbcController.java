/*
 *Copyright (C) 2018 HangZhou Hikvision System Technology Co., Ltd. All Right Reserved.
 *@className UserJdbcController
 *@description http://www.hikvision.com
 *@author ruanshubin
 *@date 2019/3/18 20:06
 *@version 1.0
 */
package com.ruanshubin.web;

import com.ruanshubin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users/jdbc")
public class UserJdbcController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void create(@RequestParam String name, @RequestParam Integer age){
        userService.create(name, age);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public void deleteAllUsers(){
        userService.deleteAllUsers();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    public void deleteUserByName(@PathVariable String name){
        userService.deleteByName(name);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Integer getUserNum(){
        return userService.getUsersNum();
    }
}

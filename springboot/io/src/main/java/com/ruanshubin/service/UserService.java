/*
 *Copyright (C) 2018 HangZhou Hikvision System Technology Co., Ltd. All Right Reserved.
 *@className UserService
 *@description http://www.hikvision.com
 *@author ruanshubin
 *@date 2019/3/18 19:34
 *@version 1.0
 */
package com.ruanshubin.service;

public interface UserService {
    /*
     * @Description 新增一个用户
     * @Param [name, age]
     * @return void
     */
    void create(String name, Integer age);

    /*
     * @Description 根据姓名删除用户
     * @Param [name]
     * @return void
     */
    void deleteByName(String name);

    /*
     * @Description 获取用户数量
     * @Param []
     * @return java.lang.Integer
     */
    Integer getUsersNum();

    /*
     * @Description 删除所有用户
     * @Param []
     * @return void
     */
    void deleteAllUsers();

}

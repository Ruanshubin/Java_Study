/*
 *Copyright (C) 2018 HangZhou Hikvision System Technology Co., Ltd. All Right Reserved.
 *@className Person
 *@description http://www.hikvision.com
 *@author ruanshubin
 *@date 2019/3/29 15:25
 *@version 1.0
 */
package com.ruanshubin.domain;

import java.util.Map;

public class Person {
    private String name;
    private Integer age;
    private boolean sex;
    private Map<String, Object> maps;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", maps=" + maps +
                '}';
    }
}

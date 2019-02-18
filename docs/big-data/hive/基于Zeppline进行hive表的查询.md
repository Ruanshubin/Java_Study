> zeppelin是一个让交互式数据分析变得可行的基于网页的notebook;
> 前端提供了精美的数据可视化功能;
> 后台提供各种大数据组件的解析器，可以操作hive、sparkSQL、rdbms、es等大数据组件。

hive数据仓库数据无法使用传统数据库连接工具查看，因此可以借助zeppelin查看hive数仓数据。

## 配置步骤

- 下载zeppelin： https://mirrors.tuna.tsinghua.edu.cn/apache/zeppelin/zeppelin-0.8.0/zeppelin-0.8.0-bin-all.tgz

- 修改配置文件

解压后修改配置文件:

conf目录中:

1. cp zeppelin-site.xml.template zeppelin-site.xml

2. cp zeppelin-env.sh.template zeppelin-env.sh

3. cp shiro.ini.template shiro.ini

4. 将hive的依赖包拷贝到 interpreter/jdbc 目录下:

```
cp /usr/lib/LOCALCLUSTER/SERVICE-SPARK-a4e12aea037d4ae891fdcfd238c29d2a/jars /tcd/tools/zeppeline/zeppelin-0.8.0-bin-all/interpreter/jdbc
```

5. 启动
bin/zeppelin-daemon.sh start

6. 浏览器访问 8080

使用shiro.ini中配置的用户名密码登录

7. 创建hive interpreter

```
# create--填写Interpreter Name--Interpreter group选择jdbc
# 在配置里增加两项内容
default.driver：org.apache.hive.jdbc.HiveDriver
default.url：jdbc:hive2://10.194.224.111:10000
```

8. 创建notebook并使用hive interpreter
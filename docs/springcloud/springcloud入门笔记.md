## Kafka

### Kafka Manager后台启动

```
cd /usr/local/kafkaManager/kafka-manager-1.3.3.18/bin
nohup ./kafka-manager -DHttp.port=9002 &
```


## Config配置

- cinfig支持的数据配置

git、svn、jdbc

推荐使用JDBC的方式，因为之前的GIT SVN 对于服务比较少的系统，可能比较容易维护，如果服务比较多，没有一个后台管理系统来维护，就太复杂。

阅读源码，我们可以知道，config默认支持git模式，但是同时也提供了svn、vault、 jdbc，三种配置模式，那我们怎么激活呢？

```
spring.profiles.active=jdbc
```

IP:端口号:application/profile/label

## 数据库里的坑

MySQL在Linux下数据库名、表名、列名、别名大小写规则是这样的：
　　1、数据库名与表名是严格区分大小写的；
　　2、表的别名是严格区分大小写的；
　　3、列名与列的别名在所有的情况下均是忽略大小写的；
    4、字段内容默认情况下是大小写不敏感的。
	
需要注意的是，需要保证在数据库中，定义的SQL语句能够执行成功；

Driver的版本需要匹配，否则连接不上数据库。
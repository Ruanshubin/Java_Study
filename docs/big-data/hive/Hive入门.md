## 环境搭建

### Mysql安装

- 处理系统环境

```
rpm -qa | grep mariadb
rpm -e --nodeps mariadb-libs-5.5.56-2.el7.x86_64

# 暂时没有用到
groupadd mysql
useradd -g mysql mysql -d /home/mysql
passwd mysql

mkdir /home/mysql/3306/data
mkdir -p /home/mysql/3306/log
mkdir -p /home/mysql/3306/tmp

```

- Mysql安装

```
tar -xvf mysql-8.0.13-1.el7.x86_64.rpm-bundle.tar 
rpm -ivh mysql-community-common-8.0.13-1.el7.x86_64.rpm 
rpm -ivh mysql-community-libs-8.0.13-1.el7.x86_64.rpm 
rpm -ivh mysql-community-client-8.0.13-1.el7.x86_64.rpm 
rpm -ivh mysql-community-server-8.0.13-1.el7.x86_64.rpm 

service mysqld status
service mysqld start

# 获取初始密码
grep 'temporary password' /var/log/mysqld.log
# 若在log文件中找不到初始密码
# 说明原来的mysql未删除干净
rm -rf /var/lib/mysql
systemctl restart mysqld

# 修改密码等级 + 修改密码
[root@tcd-test ~]# mysql -u root -p
Enter password: 初始密码
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 8
Server version: 8.0.13

Copyright (c) 2000, 2018, Oracle and/or its affiliates. All rights reserved.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> set global validate_password.policy=0;
Query OK, 0 rows affected (0.00 sec)

mysql> set global validate_password.length=6;
Query OK, 0 rows affected (0.00 sec)

mysql> ALTER USER root@localhost IDENTIFIED BY '123456';
Query OK, 0 rows affected (0.11 sec)

mysql> show global variables like '%validate_password%';
+--------------------------------------+-------+
| Variable_name                        | Value |
+--------------------------------------+-------+
| validate_password.check_user_name    | ON    |
| validate_password.dictionary_file    |       |
| validate_password.length             | 6     |
| validate_password.mixed_case_count   | 1     |
| validate_password.number_count       | 1     |
| validate_password.policy             | LOW   |
| validate_password.special_char_count | 1     |
+--------------------------------------+-------+
7 rows in set (0.08 sec)

# 此时，只支持host为localhost连接，需要修改远程连接权限
mysql> use mysql;
Reading table information for completion of table and column names
You can turn off this feature to get a quicker startup with -A

Database changed
mysql> select user, host, authentication_string from user;
+------------------+-----------+------------------------------------------------------------------------+
| user             | host      | authentication_string                                                  |
+------------------+-----------+------------------------------------------------------------------------+
| mysql.infoschema | localhost | $A$005$THISISACOMBINATIONOFINVALIDSALTANDPASSWORDTHATMUSTNEVERBRBEUSED |
| mysql.session    | localhost | $A$005$THISISACOMBINATIONOFINVALIDSALTANDPASSWORDTHATMUSTNEVERBRBEUSED |
| mysql.sys        | localhost | $A$005$THISISACOMBINATIONOFINVALIDSALTANDPASSWORDTHATMUSTNEVERBRBEUSED |
| root             | localhost | $A$005$MBKK=R,oircXwk~2HHEqJ6ko9c/Lx1iVMERob7DXAoWi6bXyC0w19dJLnQx9 |
+------------------+-----------+------------------------------------------------------------------------+
4 rows in set (0.00 sec)

mysql> update user set host = "%" where user='root';
Query OK, 1 row affected (0.09 sec)
Rows matched: 1  Changed: 1  Warnings: 0

mysql> select user, host, authentication_string from user;
+------------------+-----------+------------------------------------------------------------------------+
| user             | host      | authentication_string                                                  |
+------------------+-----------+------------------------------------------------------------------------+
| root             | %         | $A$005$MBKK=R,oircXwk~2HHEqJ6ko9c/Lx1iVMERob7DXAoWi6bXyC0w19dJLnQx9 |
| mysql.infoschema | localhost | $A$005$THISISACOMBINATIONOFINVALIDSALTANDPASSWORDTHATMUSTNEVERBRBEUSED |
| mysql.session    | localhost | $A$005$THISISACOMBINATIONOFINVALIDSALTANDPASSWORDTHATMUSTNEVERBRBEUSED |
| mysql.sys        | localhost | $A$005$THISISACOMBINATIONOFINVALIDSALTANDPASSWORDTHATMUSTNEVERBRBEUSED |
+------------------+-----------+------------------------------------------------------------------------+
4 rows in set (0.00 sec)

mysql> grant all privileges on *.* to 'root'@'%' with grant option;
Query OK, 0 rows affected (0.13 sec)

或者：GRANT ALL ON *.* TO 'root'@'%';  # 两个在不同机器上都成功过，但是不能同时成功，都试一下吧。

# Navicat连接linux上的mysql报2059 Authentication plugin 'caching_sha2_password'cannot be loaded
# 从mysql5.7版本之后，默认采用了caching_sha2_password验证方式。
# 在linux服务器中，开启mysql，并进入连接的数据库执行如下语句，表示采用原来的身份验证机制。
mysql> ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY '123456';

mysql> use mysql;
Reading table information for completion of table and column names
You can turn off this feature to get a quicker startup with -A

Database changed
mysql> select user, host, authentication_string , plugin from user;
+------------------+-----------+------------------------------------------------------------------------+-----------------------+
| user             | host      | authentication_string                                                  | plugin                |
+------------------+-----------+------------------------------------------------------------------------+-----------------------+
| root             | %         | *6BB4837EB74329105EE4568DDA7DC67ED2CA2AD9                              | mysql_native_password |
| mysql.infoschema | localhost | $A$005$THISISACOMBINATIONOFINVALIDSALTANDPASSWORDTHATMUSTNEVERBRBEUSED | caching_sha2_password |
| mysql.session    | localhost | $A$005$THISISACOMBINATIONOFINVALIDSALTANDPASSWORDTHATMUSTNEVERBRBEUSED | caching_sha2_password |
| mysql.sys        | localhost | $A$005$THISISACOMBINATIONOFINVALIDSALTANDPASSWORDTHATMUSTNEVERBRBEUSED | caching_sha2_password |
+------------------+-----------+------------------------------------------------------------------------+-----------------------+
4 rows in set (0.00 sec)

```

### Hive安装

```
vim /etc/profile

export HIVE_HOME=/usr/software/hive/apache-hive-2.3.4-bin
export PATH=$PATH:$HIVE_HOME/bin

source /etc/profile

# 此时执行hive的show databases会报错，是从Hive 2.1版本开始,我们需要先运行schematool 命令来执行初始化操作
schematool -dbType derby -initSchema

# 启动hive之前请确保hadoop启动起来

# 使用默认数据库derby，执行hive的show databases仍然会报错，未解决。

# 试着用mysql数据库

cp hive-env.sh.template hive-env.sh
cp hive-default.xml.template hive-site.xml

vim hive-site.xml

<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>

<configuration>
	<property>
		<name>hive.default.fileformat</name>
		<value>TextFile</value>
		<description>Default file format for CREATE TABLE statement. Options are TextFile and SequenceFile. Users can explicitly say CREATE TABLE ... STORED AS &lt;TEXTFILE|SEQUENCEFILE&gt; to override</description>
	</property>
	<property>
		<name>javax.jdo.option.ConnectionURL</name>
		<value>jdbc:mysql://localhost:3306/hive?createDatabaseIfNotExist=true&amp;useSSL=false</value>
		<description>JDBC connect string for a JDBC metastore</description>
	</property>
	<property>
		<name>javax.jdo.option.ConnectionDriverName</name>
		<value>com.mysql.jdbc.Driver</value>
		<description>Driver class name for a JDBC metastore</description>
	</property>
	<property>
		<name>javax.jdo.option.ConnectionUserName</name>
		<value>root</value>
		<description>username to use against metastore database</description>
	</property>
	<property>
		<name>javax.jdo.option.ConnectionPassword</name>
		<value>123456</value>
		<description>password to use against metastore database</description>
	</property>
  
</configuration>

vim hive-env.sh

HADOOP_HOME=/usr/software/hadoop2.8.5/hadoop-2.8.5
export HIVE_CONF_DIR=/usr/software/hive/apache-hive-2.3.4-bin/conf
export HIVE_AUX_JARS_PATH=/usr/software/hive/apache-hive-2.3.4-bin/lib

# 将mysql连接jar放到/hive/lib下

# 初始化mysql数据库
schematool -dbType mysql -initSchema
schematool -dbType postgres -initSchema

hive> show databases;
Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'. The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.
OK
default
Time taken: 9.951 seconds, Fetched: 1 row(s)

# 存在一个默认数据库default,但是在hdfs中并没有/user/hive/warehouse，这个目录属于顶层目录，即数据仓库；
# 此时，在hive shell中
create database test;
# 此时，/user/hive/warehouse会被创建，并有test.db文件

```

### hive in spark

```
配置步骤：

1.复制postgresql-42.2.5.jar文件到spark home路径下jars文件夹中

2. 使用hadoop fs命令创建hive数仓目录
/usr/lib/LOCALCLUSTER/SERVICE-HADOOP-ec48e21ba173412995c6eff698cca573/bin/hadoop fs -mkdir -p /hive/warehouse

3. spark 配置路径下配置hive-site.xml文件
配置内容参考当前目录下hive-site.xml

3.在postgresql中新建名为hivedb的数据库；（hivedb是在hive-site.xml文件中配置的）

验证配置是否正确
1. 以yran-client模式启动hive thriftserver：
/usr/lib/LOCALCLUSTER/SERVICE-SPARK-cf577f6604384507a5931a3af3a1908b/sbin/start-thriftserver.sh \
--master yarn-client \
--driver-cores 1 \
--conf spark.driver.memory=2G \
--queue root.jobs \
--num-executors 2 \
--executor-memory 2g \
--conf spark.yarn.executor.memoryOverhead=1024

./start-thriftserver.sh \
--master yarn-client \
--driver-cores 2 \
--conf spark.driver.memory=1G \
--queue root.jobs \
--num-executors 2 \
--executor-memory 1g \
--conf spark.yarn.executor.memoryOverhead=1024

2. 使用beeline工具操作数据库：
/usr/lib/LOCALCLUSTER/SERVICE-SPARK-cf577f6604384507a5931a3af3a1908b/bin/beeline

3. 之后执行：
!connect jdbc:hive2://10.194.224.112:10000
连续回车即可

----------------------------------------------
SQL 建表操作

1. 使用CSV等文本格式存储
create table test (id int , name string) ROW FORMAT DELIMITED FIELDS TERMINATED BY '|' STORED AS TEXTFILE ;
INSERT INTO test VALUES(1,'a');

2. 使用ORC格式存储数据
create table if not exists test_orc(
  id string,
  name string,
  comment string
) STORED AS ORC;

INSERT INTO test_orc VALUES(1,'a','abc');

3. 使用parquet格式存储数据
create table if not exists test_parquet(
  id string,
  name string,
  comment string
) STORED AS PARQUET;

INSERT INTO test_parquet VALUES(1,'a','abc');

--------------------------------------------
使用insert语句添加数据，一句insert语句就会生成一个文件，大量的小文件会严重拖慢sparkSQL的查询性能，因此需要定期对文件进行合并。

创建表后hdfs会生成相应目录，外部程序通过将规定格式的文件写入hdfs对应目录下，hive就可以查询到对应数据。

注意：
上面的方式创建的是hive内部表，当执行drop table table_name;语句时，hdfs上关联的数据目录将会被删除。
如果创建外部表则在drop table后hdfs数据目录不会被删除。
创建外部表方式如下：

create external table if not exists external_test_parquet(
  id string,
  name string,
  comment string
) stored as parquet location '/hive/warehouse/external_test_parquet';

其中指定的目录为hdfs路径。

```

## 数据处理

### 表类型

- Hive 是一个建立在hadoop文件系统上的数据仓库架构，可以用其对hdfs上数据进行分析与管理；
- 实际上是将hdfs上的文件映射成table（按文件格式创建table,然后hive的数据仓库会生成对应的目录，默认的仓库路径：user/hive/warehouse/tablename，目录名与这个表名相同，这时只要将符合table定义的文件加载到该目录便可通过Hql对整个目录的文件进行查询；
- 将数据加载到该目录可以用hdfs dfs -put 命令直接添加到该目录；
- 也可以通过load data local inpath ‘user/test.txt’ into table tableName,通过load命令加载数据与通过put命令加载文件的结果是一样的，即在user/hive/warehouse/tablename 目录下都会有加载进来的文件，如果用load命令加载的是hdfs上的文件则会将原hdfs目录下对应的文件移动至hive的仓库目录下）,并将这些元数据保存到关系型数据库中，元数据存储着表所对应的文件路径，表的列与分区，表创建时间，文件大小等属性；
- 同时支持用户运用类sql对文件进行操作，这个操作主要是查询。

hive的数据模型中有4种表：

```
Table内部表 
External Table 外部表
Partition分区表 
Bucket Table 桶表
```
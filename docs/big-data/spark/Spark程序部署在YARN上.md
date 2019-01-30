## 自定义spark的依赖包

>启动Spark任务时，在没有配置spark.yarn.archive或者spark.yarn.jars时， 会看到不停地上传jar，非常耗时；使用spark.yarn.archive可以大大地减少任务的启动时间；同时，也可以在不污染原有spark环境的前提下，解决包冲突的问题。整个处理过程如下:

- 在本地创建zip文件

```
hzlishuming@hadoop691:~/env/spark$ cd jars/
hzlishuming@hadoop691:~/env/spark$ zip spark2.1.1-hadoop2.7.3.zip ./*
```

- 上传至HDFS并更改权限

```
hzlishuming@hadoop691:~/env/spark$ /usr/ndp/current/hdfs_client/bin/hdfs dfs -mkdir /tmp/spark-archive
hzlishuming@hadoop691:~/env/spark$ /usr/ndp/current/hdfs_client/bin/hdfs dfs -put ./spark2.1.1-hadoop2.7.3.zip /tmp/spark-archive
hzlishuming@hadoop691:~/env/spark$ /usr/ndp/current/hdfs_client/bin/hdfs dfs -chmod 775 /tmp/spark-archive/spark2.1.1-hadoop2.7.3.zip

```

- 配置spark-defaut.conf

```
spark.yarn.archive hdfs:///tmp/spark-archive/spark2.1.1-hadoop2.7.3.zip
```

也可以在提交任务的时候指定：

```
./bin/spark-submit \
--class com.hikvision.tcd.decicion.DecisionControlApplication \
--name DecisionControlApplication \
--master yarn \
--deploy-mode cluster \
--queue root.root \
--driver-cores 1 \
--driver-memory 2g \
--executor-cores 4 \
--executor-memory 2g \
--num-executors 4 \
--driver-java-options '-XX:MaxDirectMemorySize=128M -XX:NewRatio=1 -XX:SurvivorRatio=8 -XX:TargetSurvivorRatio=90 -XX:MaxTenuringThreshold=8 -XX:+UseConcMarkSweepGC -XX:ConcGCThreads=4 -XX:ParallelGCThreads=4 -XX:+CMSScavengeBeforeRemark -XX:PretenureSizeThreshold=64m -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=50 -XX:CMSMaxAbortablePrecleanTime=6000 -XX:+CMSParallelRemarkEnabled -XX:+ParallelRefProcEnabled -XX:-OmitStackTraceInFastThrow' \
--files hdfs://tcd111:8020/tcd/decision_control/conf/bootstrap.yml,hdfs://tcd111:8020/tcd/decision_control/conf/log4j.properties \
--conf spark.yarn.driver.memoryOverhead=1024  \
--conf spark.yarn.executor.memoryOverhead=384  \
--conf spark.yarn.archive=hdfs://tcd111:8020/spark/yarn/spark2.4.0-hadoop2.6.0.zip \
hdfs://tcd111:8020/tcd/decision_control/jar/tcd-decision-control-0.1-jar-with-dependencies.jar
```
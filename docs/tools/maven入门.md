- 将jar包添加到本地仓库中

```
mvn install:install-file -Dfile=D:\data_utils.jar -DgroupId=com.ruanshubin.data_utils -DartifactId=data_utils -Dversion=1.0.0 -Dpackaging=jar
```
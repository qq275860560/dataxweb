[TOC]
dataxweb镜像

# 适用场景
适用于开发测试环境

# 功能
## 下载dataxweb


# 使用方式
```
# docker build -t qq275860560/dataxweb .
docker pull qq275860560/dataxweb
(docker kill dataxweb || true) && (docker rm dataxweb || true) 
docker run -it --name dataxweb qq275860560/dataxweb /bin/bash

#本地仓库安装orai18n
curl -O http://maven.jahia.org/maven2/com/oracle/orai18n/11.2.0.3/orai18n-11.2.0.3.jar
mvn install:install-file -DgroupId=com.oracle -DartifactId=orai18n -Dversion=11.2.0.3 -Dpackaging=jar -Dfile=orai18n-11.2.0.3.jar
#本地仓库安装ojdbc6
curl -O http://www.datanucleus.org/downloads/maven2/oracle/ojdbc6/11.2.0.3/ojdbc6-11.2.0.3.jar
mvn install:install-file -DgroupId=oracle -DartifactId=ojdbc6 -Dversion=11.2.0.3 -Dpackaging=jar -Dfile=ojdbc6-11.2.0.3.jar
#本地仓库安装sqljdbc4
curl -O http://clojars.org/repo/com/microsoft/sqlserver/sqljdbc4/4.0/sqljdbc4-4.0.jar
mvn install:install-file -DgroupId=com.microsoft.sqlserver -DartifactId=sqljdbc4 -Dversion=4.0 -Dpackaging=jar -Dfile=sqljdbc4-4.0.jar

#设置环境变量DATAX_HOME
cd /tmp/dataxweb && mvn spring-boot:run

```


```
# 创建view
java -jar jenkins-cli.jar -s http://localhost:8081/ create-view view1
<?xml version="1.1" encoding="UTF-8"?>
<hudson.model.ListView>
  <name>view1</name>
  <filterExecutors>false</filterExecutors>
  <filterQueue>false</filterQueue>
  <properties class="hudson.model.View$PropertyList"/>
  <jobNames>
    <comparator class="hudson.util.CaseInsensitiveComparator"/>
  </jobNames>
  <jobFilters/>
  <columns>
    <hudson.views.StatusColumn/>
    <hudson.views.WeatherColumn/>
    <hudson.views.JobColumn/>
    <hudson.views.LastSuccessColumn/>
    <hudson.views.LastFailureColumn/>
    <hudson.views.LastDurationColumn/>
    <hudson.views.BuildButtonColumn/>
  </columns>
  <recurse>false</recurse>
</hudson.model.ListView>

# 获取view
java -jar jenkins-cli.jar -s http://localhost:8081/ get-view view1



```

# 前端

前端本来是用node，但要另外启动端口，调试也比较麻烦，为了更加轻便，并没有使用node，npm，webpack，直接使用传统方式，前端代码放到[src/main/resources/static](https://github.com/qq275860560/dataxweb/tree/master/src/main/resources/static)目录下,拷贝到任何地方双击index.html即可运行（后台服务要正常）



# 温馨提醒

* 此项目将会长期维护，增加或改进实用的功能
* 右上角点击star，给我继续前进的动力,谢谢
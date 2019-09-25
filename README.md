[TOC]
dataxweb镜像

# 适用场景
适用于开发测试环境

# 功能
## 下载dataxweb

# 使用方式
## 启动方式
```
docker pull qq275860560/dataxweb

(docker kill dataxweb || true) && (docker rm dataxweb || true) 

docker run -d -p 8045:8045 -p 8081:8081  --name dataxweb qq275860560/dataxweb  /bin/bash -c '  /etc/rc.d/init.d/jenkins start && cd /tmp/dataxweb/target && java -jar github-qq275860560-dataxweb.jar \
--spring.datasource.url=jdbc:log4jdbc:mysql://192.168.137.45:3306/dataxweb?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8 \
--spring.datasource.username=root \
--spring.datasource.password=123456
'
    
```

# 测试
http://XXX:8045
其中XXX为linux的ip，使用ip a命令可以查看
 
# 温馨提醒

* 此项目将会长期维护，增加或改进实用的功能
* 右上角点击star，给我继续前进的动力,谢谢


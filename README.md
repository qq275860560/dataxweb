[TOC]
dataxweb镜像

# 适用场景
适用于开发测试环境

# 功能
## 下载dataxweb

# 使用方式
## 启动方式
```
cd /tmp && rm -rf dataxweb &&  git clone https://github.com/qq275860560/dataxweb.git 
cd /tmp/dataxweb && git pull 
docker pull qq275860560/dataxweb
(docker kill dataxweb || true) && (docker rm dataxweb || true) 
docker run -d -p 8045:8045 -v /tmp:/tmp -v /root/.m2:/root/.m2 --name dataxweb qq275860560/dataxweb  /bin/bash -c 'source /etc/profile &&\
    /usr/sbin/sshd &&\
    chmod -R 777 /var/lib/mysql /usr/share/mysql /var/run/mysqld &&\
    chown -R root:root /var/lib/mysql /usr/share/mysql /var/run/mysqld &&\
    cd /etc/ && curl -fsSL -O "https://raw.githubusercontent.com/qq275860560/dataxweb/master/src/main/centos/etc/my.cnf" &&\
    /usr/sbin/mysqld  --defaults-file=/etc/my.cnf --user=root --daemonize &&\
    cd /usr/share/mysql/ && curl -fsSL -O "https://raw.githubusercontent.com/qq275860560/dataxweb/master/src/main/centos/usr/share/mysql/dataxweb.sql" &&\
    mysql -uroot  -p123456  mysql< ./dataxweb.sql  2>/dev/null     &&\
    /etc/rc.d/init.d/jenkins start &&\
    cd /usr/local/apache-ftpserver-1.0.6 && (bin/ftpd.sh  res/conf/ftpd-typical.xml  &) &&\
    cd /tmp/dataxweb && mvn spring-boot:run && tail -f /var/log/lastlog'
    
```

# 测试
http://XXX:8045
其中XXX为linux的ip，使用ip a命令可以查看
 
# 温馨提醒

* 此项目将会长期维护，增加或改进实用的功能
* 右上角点击star，给我继续前进的动力,谢谢


FROM qq275860560/datax

MAINTAINER jiangyuanlin@163.com

# 安装mysql
RUN  yum -y install  http://dev.mysql.com/get/mysql57-community-release-el7-11.noarch.rpm &&\
    yum repolist enabled | grep mysql.*   &&\
    yum -y install mysql-community-server  &&\
    rm -rf /var/cache/yum/*
    
RUN   echo "201906171252,chmod,interact,expect eof"  && curl -fsSL "https://raw.githubusercontent.com/qq275860560/mysql/master/src/main/centos/tmp/init.sh" | sh

RUN cd /etc/ && curl -fsSL -O "https://raw.githubusercontent.com/qq275860560/dataxweb/master/src/main/centos/etc/my.cnf" &&\
    cd /usr/share/mysql/ && curl -fsSL -O "https://raw.githubusercontent.com/qq275860560/dataxweb/master/src/main/centos/usr/share/mysql/dataxweb.sql" &&\
    chmod -R 777 /var/lib/mysql /usr/share/mysql /var/run/mysqld &&\
    chown -R root:root /var/lib/mysql /usr/share/mysql /var/run/mysqld &&\
    /usr/sbin/mysqld  --defaults-file=/etc/my.cnf --user=root --daemonize &&\
    mysql -uroot  -p123456  mysql< ./dataxweb.sql  2>/dev/null 

RUN cd /tmp &&  git clone https://github.com/qq275860560/dataxweb.git 
RUN cd /tmp/dataxweb && mvn test


CMD source /etc/profile &&\
    service sshd start &&\
    cd /tmp/dataxweb && mvn spring-boot:run 
      	

 

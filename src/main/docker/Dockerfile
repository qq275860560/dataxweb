FROM qq275860560/datax

MAINTAINER jiangyuanlin@163.com

# 安装mysql
RUN  yum -y install  http://dev.mysql.com/get/mysql57-community-release-el7-11.noarch.rpm &&\
    yum repolist enabled | grep mysql.*   &&\
    yum -y install mysql-community-server  &&\
    rm -rf /var/cache/yum/*
    
RUN curl -fsSL "https://raw.githubusercontent.com/qq275860560/mysql/master/src/main/centos/tmp/init.sh" | sh



RUN cd /tmp &&  git clone https://github.com/qq275860560/dataxweb.git

CMD source /etc/profile &&\
     service sshd start &&\
     service dataxweb start 	

 

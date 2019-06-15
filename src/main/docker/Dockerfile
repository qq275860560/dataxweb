FROM qq275860560/datax

MAINTAINER jiangyuanlin@163.com

# 安装mysql
RUN wget http://dev.mysql.com/get/mysql57-community-release-el7-11.noarch.rpm 
RUN yum -y install mysql57-community-release-el7-11.noarch.rpm 
RUN yum repolist enabled | grep mysql.* 
RUN yum -y install mysql-community-server

RUN systemctl start  mysqld.service
RUN systemctl status mysqld.service
RUN grep "password" /var/log/mysqld.log
#RUN mysql -uroot -p

RUN cd /tmp &&  git clone https://github.com/qq275860560/dataxweb.git

CMD source /etc/profile &&\
     service sshd start &&\
     service dataxweb start 	

 

[TOC]
dataxweb镜像

# 适用场景
适用于开发测试环境

# 功能
## 下载dataxweb

# 使用方式
linux下执行
```
docker pull qq275860560/dataxweb
(docker kill dataxweb || true) && (docker rm dataxweb || true) 
docker run -d -p 8045:8045 --name dataxweb qq275860560/dataxweb 
```


```

docker run -d -p 8045:8045 --name dataxweb qq275860560/dataxweb  /bin/bash -c ' source /etc/profile &&\
    /usr/sbin/sshd &&\
    chmod -R 777 /var/lib/mysql /usr/share/mysql /var/run/mysqld &&\
    chown -R root:root /var/lib/mysql /usr/share/mysql /var/run/mysqld &&\
    /usr/sbin/mysqld  --defaults-file=/etc/my.cnf --user=root --daemonize &&\
    /etc/rc.d/init.d/jenkins start &&\
    cd /tmp/dataxweb && tail -f /var/log/lastlog'
    
```

http://XXX:8045
其中XXX为linux的ip，使用ip a命令可以查看
 
# 温馨提醒

* 此项目将会长期维护，增加或改进实用的功能
* 右上角点击star，给我继续前进的动力,谢谢

# index.html
```
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>require test</title>  
	<script src="https://cdn.bootcss.com/require.js/2.3.6/require.js" data-main="index"></script> 
   </head>
   <body>
		<div id="app"></div>   	  
   </body>
</html>

```


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
docker run -d -p 8080:8080 --name dataxweb qq275860560/dataxweb 
```

http://XXX:8080
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


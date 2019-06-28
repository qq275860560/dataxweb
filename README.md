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
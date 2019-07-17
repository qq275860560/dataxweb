set global max_allowed_packet = 4*100*1024*1024*10;

DROP DATABASE IF EXISTS dataxweb;
CREATE DATABASE IF NOT EXISTS dataxweb default charset utf8 COLLATE utf8_general_ci;
use dataxweb;

DROP TABLE  IF EXISTS job;
CREATE TABLE job (
id VARCHAR ( 32 ) NOT NULL,
name VARCHAR ( 64 ) DEFAULT NULL,
inputId VARCHAR ( 32 ) DEFAULT NULL,
inputName VARCHAR ( 64 ) DEFAULT NULL,
inputType VARCHAR ( 64 ) DEFAULT NULL COMMENT '输入流类型，比如mysqlreader',
outputId VARCHAR ( 32 ) DEFAULT NULL,
outputName VARCHAR ( 64 ) DEFAULT NULL, 
outputType VARCHAR ( 64 ) DEFAULT NULL COMMENT '输出流类型，比如mysqlwriter',
transformerId VARCHAR ( 32 ) DEFAULT NULL,
transformerName VARCHAR ( 64 ) DEFAULT NULL,
transformerType VARCHAR ( 64 ) DEFAULT NULL,
dataxJson  VARCHAR ( 1024 ) DEFAULT NULL,
status int DEFAULT NULL comment '{0:停用:不允许构建,1:启用，允许构建，2：正在构建中}',
lastBuildId VARCHAR(32) DEFAULT NULL COMMENT '最后一次构建Id',
lastBuildNumber VARCHAR(32) DEFAULT NULL COMMENT '最后一次构建编号',
lastSuccessfulBuildNumber VARCHAR(32) DEFAULT NULL COMMENT '最后一次成功构建编号',
lastUnsuccessfulBuildNumber VARCHAR(32) DEFAULT NULL COMMENT '最后一次失败构建编号',
nextBuildNumber VARCHAR(32) DEFAULT NULL COMMENT '下一次构建编号',
lastBuildCreateTime datetime DEFAULT NULL,
lastBuildEstimatedDuration int  DEFAULT NULL COMMENT '预期构建时长（毫秒）',
lastBuildProgress double  DEFAULT NULL COMMENT '完成进度百分比',
createUserId VARCHAR ( 32 ) DEFAULT NULL,
createUserName VARCHAR ( 64 ) DEFAULT NULL,
createTime datetime DEFAULT NULL,
PRIMARY KEY ( id ) 
)  DEFAULT CHARSET=utf8;

 

DROP TABLE  IF EXISTS build;
CREATE TABLE build (
id VARCHAR ( 32 ) NOT NULL,
name VARCHAR ( 64 ) DEFAULT NULL,
jobId VARCHAR ( 32 ) DEFAULT NULL COMMENT '所属计划任务id',
jobName VARCHAR ( 32 ) DEFAULT NULL COMMENT '所属计划任务名称',
number VARCHAR(32)  DEFAULT NULL COMMENT '构建日志编号',
status int   DEFAULT NULL COMMENT '{2:构建中,3:构建停止}',
estimatedDuration int  DEFAULT NULL COMMENT '预期构建时长（毫秒）',
progress double  DEFAULT NULL COMMENT '完成进度百分比',
duration int  DEFAULT NULL COMMENT '实际构建时长(毫秒)',
result int   DEFAULT NULL COMMENT '{1:成功，2:失败,3:取消}',
consoleText longtext  DEFAULT NULL COMMENT '控制台日志',
createUserId VARCHAR ( 32 ) DEFAULT NULL,
createUserName VARCHAR ( 64 ) DEFAULT NULL,
createTime datetime DEFAULT NULL,
PRIMARY KEY ( id ) 
)  DEFAULT CHARSET=utf8;
 



DROP TABLE  IF EXISTS test;
CREATE TABLE test (
id VARCHAR ( 32 ) NOT NULL,
name VARCHAR ( 64 ) DEFAULT NULL,
PRIMARY KEY ( id ) 
)  DEFAULT CHARSET=utf8;

 

DROP TABLE  IF EXISTS input;
CREATE TABLE input (
id VARCHAR ( 32 ) NOT NULL,
name VARCHAR ( 64 ) DEFAULT NULL comment '输入流名称',
type VARCHAR ( 64 ) DEFAULT NULL comment '输入流类型',
createUserId VARCHAR ( 32 ) DEFAULT NULL,
createUserName VARCHAR ( 64 ) DEFAULT NULL,
createTime datetime DEFAULT NULL,
PRIMARY KEY ( id ) 
)  DEFAULT CHARSET=utf8;

DROP TABLE  IF EXISTS mysqlReader;
CREATE TABLE mysqlReader (
id VARCHAR ( 32 ) NOT NULL,
name VARCHAR ( 64 ) DEFAULT NULL comment '输入流名称',
type VARCHAR ( 64 ) DEFAULT NULL comment '输入流类型',
parameterUsername VARCHAR ( 32 ) DEFAULT NULL,
parameterPassword VARCHAR ( 32 ) DEFAULT NULL,
parameterColumn VARCHAR ( 1024 ) DEFAULT NULL,
parameterWhere VARCHAR ( 1024 ) DEFAULT NULL,
parameterConnectionJdbcUrl VARCHAR ( 1024 ) DEFAULT NULL,
parameterConnectionTable VARCHAR ( 32 ) DEFAULT NULL,
createUserId VARCHAR ( 32 ) DEFAULT NULL,
createUserName VARCHAR ( 64 ) DEFAULT NULL,
createTime datetime DEFAULT NULL,
PRIMARY KEY ( id ) 
)  DEFAULT CHARSET=utf8;

DROP TABLE  IF EXISTS txtFileReader;
CREATE TABLE txtFileReader (
id VARCHAR ( 32 ) NOT NULL,
name VARCHAR ( 64 ) DEFAULT NULL comment '输出流名称',
type VARCHAR ( 64 ) DEFAULT NULL comment '输出流类型',
parameterPath VARCHAR ( 1024 ) DEFAULT NULL comment '所在文件夹路径',
parameterFieldDelimiter VARCHAR ( 32 ) DEFAULT NULL comment '字段分隔符',
parameterColumn VARCHAR ( 1024 ) DEFAULT NULL comment '列信息数组',
createUserId VARCHAR ( 32 ) DEFAULT NULL,
createUserName VARCHAR ( 64 ) DEFAULT NULL,
createTime datetime DEFAULT NULL,
PRIMARY KEY ( id ) 
)  DEFAULT CHARSET=utf8;  

DROP TABLE  IF EXISTS httpReader;
CREATE TABLE httpReader (
id VARCHAR ( 32 ) NOT NULL,
name VARCHAR ( 64 ) DEFAULT NULL comment '输出流名称',
type VARCHAR ( 64 ) DEFAULT NULL comment '输出流类型',
parameterPath VARCHAR ( 1024 ) DEFAULT NULL comment 'http或者https的url，如果需要认证，带上用户名密码',
parameterFieldDelimiter VARCHAR ( 32 ) DEFAULT NULL comment '字段分隔符',
parameterColumn VARCHAR ( 1024 ) DEFAULT NULL comment '列信息数组',
createUserId VARCHAR ( 32 ) DEFAULT NULL,
createUserName VARCHAR ( 64 ) DEFAULT NULL,
createTime datetime DEFAULT NULL,
PRIMARY KEY ( id ) 
)  DEFAULT CHARSET=utf8; 

DROP TABLE  IF EXISTS ftpReader;
CREATE TABLE ftpReader (
id VARCHAR ( 32 ) NOT NULL,
name VARCHAR ( 64 ) DEFAULT NULL comment '输出流名称',
type VARCHAR ( 64 ) DEFAULT NULL comment '输出流类型',
parameterProtocol  VARCHAR ( 64 ) DEFAULT NULL comment '协议',
parameterHost  VARCHAR ( 64 ) DEFAULT NULL comment 'IP',
parameterPort  VARCHAR ( 64 ) DEFAULT NULL comment '端口',
parameterUsername  VARCHAR ( 64 ) DEFAULT NULL comment '用户名',
parameterPassword  VARCHAR ( 64 ) DEFAULT NULL comment '密码',
parameterPath VARCHAR ( 1024 ) DEFAULT NULL comment '所在文件夹路径',
parameterFieldDelimiter VARCHAR ( 32 ) DEFAULT NULL comment '字段分隔符',
parameterColumn VARCHAR ( 1024 ) DEFAULT NULL comment '列信息数组',
createUserId VARCHAR ( 32 ) DEFAULT NULL,
createUserName VARCHAR ( 64 ) DEFAULT NULL,
createTime datetime DEFAULT NULL,
PRIMARY KEY ( id ) 
)  DEFAULT CHARSET=utf8; 

DROP TABLE  IF EXISTS output;
CREATE TABLE output (
id VARCHAR ( 32 ) NOT NULL,
name VARCHAR ( 64 ) DEFAULT NULL comment '输出流名称',
type VARCHAR ( 64 ) DEFAULT NULL comment '输出流类型',
createUserId VARCHAR ( 32 ) DEFAULT NULL,
createUserName VARCHAR ( 64 ) DEFAULT NULL,
createTime datetime DEFAULT NULL,
PRIMARY KEY ( id ) 
)  DEFAULT CHARSET=utf8;

DROP TABLE  IF EXISTS mysqlWriter;
CREATE TABLE mysqlWriter (
id VARCHAR ( 32 ) NOT NULL,
name VARCHAR ( 64 ) DEFAULT NULL comment '输出流名称',
type VARCHAR ( 64 ) DEFAULT NULL comment '输出流类型',
parameterUsername VARCHAR ( 32 ) DEFAULT NULL,
parameterPassword VARCHAR ( 32 ) DEFAULT NULL,
parameterWriteMode VARCHAR ( 1024 ) DEFAULT NULL,
parameterColumn VARCHAR ( 1024 ) DEFAULT NULL,
parameterPreSql VARCHAR ( 1024 ) DEFAULT NULL,
parameterConnectionJdbcUrl VARCHAR ( 1024 ) DEFAULT NULL,
parameterConnectionTable VARCHAR ( 32 ) DEFAULT NULL,
createUserId VARCHAR ( 32 ) DEFAULT NULL,
createUserName VARCHAR ( 64 ) DEFAULT NULL,
createTime datetime DEFAULT NULL,
PRIMARY KEY ( id ) 
)  DEFAULT CHARSET=utf8; 

DROP TABLE  IF EXISTS txtFileWriter;
CREATE TABLE txtFileWriter (
id VARCHAR ( 32 ) NOT NULL,
name VARCHAR ( 64 ) DEFAULT NULL comment '输出流名称',
type VARCHAR ( 64 ) DEFAULT NULL comment '输出流类型',
parameterPath VARCHAR ( 1024 ) DEFAULT NULL  comment '所在文件夹路径',
parameterFileName VARCHAR ( 32 ) DEFAULT NULL,
parameterWriteMode VARCHAR ( 32 ) DEFAULT NULL,
createUserId VARCHAR ( 32 ) DEFAULT NULL,
createUserName VARCHAR ( 64 ) DEFAULT NULL,
createTime datetime DEFAULT NULL,
PRIMARY KEY ( id ) 
)  DEFAULT CHARSET=utf8; 

DROP TABLE  IF EXISTS httpWriter;
CREATE TABLE httpWriter (
id VARCHAR ( 32 ) NOT NULL,
name VARCHAR ( 64 ) DEFAULT NULL comment '输出流名称',
type VARCHAR ( 64 ) DEFAULT NULL comment '输出流类型',
parameterPath VARCHAR ( 1024 ) DEFAULT NULL comment 'http或者https的url，如果需要认证，带上用户名密码',
parameterFileName VARCHAR ( 32 ) DEFAULT NULL,
parameterWriteMode VARCHAR ( 32 ) DEFAULT NULL,
createUserId VARCHAR ( 32 ) DEFAULT NULL,
createUserName VARCHAR ( 64 ) DEFAULT NULL,
createTime datetime DEFAULT NULL,
PRIMARY KEY ( id ) 
)  DEFAULT CHARSET=utf8; 

DROP TABLE  IF EXISTS ftpWriter;
CREATE TABLE ftpWriter (
id VARCHAR ( 32 ) NOT NULL,
name VARCHAR ( 64 ) DEFAULT NULL comment '输出流名称',
type VARCHAR ( 64 ) DEFAULT NULL comment '输出流类型',
parameterProtocol  VARCHAR ( 64 ) DEFAULT NULL comment '协议',
parameterHost  VARCHAR ( 64 ) DEFAULT NULL comment 'IP',
parameterPort  VARCHAR ( 64 ) DEFAULT NULL comment '端口',
parameterUsername  VARCHAR ( 64 ) DEFAULT NULL comment '用户名',
parameterPassword  VARCHAR ( 64 ) DEFAULT NULL comment '密码',
parameterPath VARCHAR ( 1024 ) DEFAULT NULL  comment '所在文件夹路径',
parameterFileName VARCHAR ( 32 ) DEFAULT NULL,
parameterWriteMode VARCHAR ( 32 ) DEFAULT NULL,
createUserId VARCHAR ( 32 ) DEFAULT NULL,
createUserName VARCHAR ( 64 ) DEFAULT NULL,
createTime datetime DEFAULT NULL,
PRIMARY KEY ( id ) 
)  DEFAULT CHARSET=utf8; 



DROP TABLE  IF EXISTS transformer;
CREATE TABLE transformer (
id VARCHAR ( 32 ) NOT NULL,
name VARCHAR ( 64 )  comment '交换清洗名称',
type VARCHAR (64) DEFAULT NULL comment '交换清洗类型',
parameterCode text DEFAULT NULL comment '交换清洗代码',
parameterExtraPackage text DEFAULT NULL comment '交换清洗引入的 包名引入语句，换行符结尾，如果存在多个引入，每一个语句还是分号结尾，也就是多行',
createUserId VARCHAR ( 32 ) DEFAULT NULL,
createUserName VARCHAR ( 64 ) DEFAULT NULL,
createTime datetime DEFAULT NULL,
PRIMARY KEY ( id ) 
)  DEFAULT CHARSET=utf8;

 

DROP TABLE  IF EXISTS plugin;
CREATE TABLE plugin (
id VARCHAR ( 32 ) NOT NULL,
name VARCHAR ( 64 ) DEFAULT NULL comment '插件名称',
type int DEFAULT NULL comment '{0:reader类型,1:writer类型}',
readme LongBlob   DEFAULT NULL  comment 'markdown格式的使用说明',
source LongBlob   DEFAULT NULL  comment '源码的zip压缩包对应的二进制数组,压缩包顶层目录必须有pom.xml,src,doc，参考https://github.com/qq275860560/dataxweb/blob/master/src/test/resources/static/mysqlreader-source.zip?raw=true,确保执行mvn install能正常编译',
distribute LongBlob   DEFAULT NULL  comment '发布包的zip压缩包对应的二进制数组,发布包顶层目录必须有plugin.json,plugin_job_template.json和可执行jar，参考https://github.com/qq275860560/dataxweb/blob/master/src/test/resources/static/mysqlreader-distribute.zip?raw=true',
createUserId VARCHAR ( 32 ) DEFAULT NULL,
createUserName VARCHAR ( 64 ) DEFAULT NULL,
createTime datetime DEFAULT NULL,
PRIMARY KEY ( id ) 
) DEFAULT CHARSET=utf8;

insert into plugin values(1,'mysqlreader',0,LOAD_FILE('D:/workspace_git/github-qq275860560-dataxweb/src/test/resources/static/mysqlreader-README.md'),LOAD_FILE('D:/workspace_git/github-qq275860560-dataxweb/src/test/resources/static/mysqlreader-source.zip'),LOAD_FILE('D:/workspace_git/github-qq275860560-dataxweb/src/test/resources/static/mysqlreader-distribute.zip'),null,'createUserName1','1970-01-01 00:00:00');





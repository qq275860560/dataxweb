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
readerId VARCHAR ( 32 ) DEFAULT NULL,
readerName VARCHAR ( 64 ) DEFAULT NULL,
outputId VARCHAR ( 32 ) DEFAULT NULL,
outputName VARCHAR ( 64 ) DEFAULT NULL,
writerId VARCHAR ( 32 ) DEFAULT NULL,
writerName VARCHAR ( 64 ) DEFAULT NULL,
dataxJson  VARCHAR ( 1024 ) DEFAULT NULL,
status int DEFAULT NULL comment '{1:启用，允许构建，2:禁用:不允许构建}',
number VARCHAR(32) DEFAULT NULL COMMENT '最后一次构建编号',
lastSuccessfulBuild VARCHAR(32) DEFAULT NULL COMMENT '最后一次成功构建编号',
lastUnsuccessfulBuild VARCHAR(32) DEFAULT NULL COMMENT '最后一次失败构建编号',
nextBuildNumber VARCHAR(32) DEFAULT NULL COMMENT '下一次构建编号',
progress double  DEFAULT NULL,
createUserId VARCHAR ( 32 ) DEFAULT NULL,
createUserName VARCHAR ( 64 ) DEFAULT NULL,
createTime datetime DEFAULT NULL,
PRIMARY KEY ( id ) 
)  DEFAULT CHARSET=utf8;

insert into job values(1,'jobName1',1,'inputName1',null,'mysqlreader',1,'outputName1',null,'mysqlwriter',1,1,null,null,null,1,0.0,null,'createUserName1','1970-01-01 00:00:00');
insert into job values(2,'jobName2',2,'inputName2',null,'mysqlreader',2,'outputName2',null,'mysqlwriter',1,1,null,null,null,1,0.0,null,'createUserName2','1970-01-01 00:00:00');


DROP TABLE  IF EXISTS build;
CREATE TABLE build (
id VARCHAR ( 32 ) NOT NULL,
name VARCHAR ( 64 ) DEFAULT NULL,
jobId VARCHAR ( 32 ) DEFAULT NULL COMMENT '所属计划任务id',
jobName VARCHAR ( 32 ) DEFAULT NULL COMMENT '所属计划任务名称',
number VARCHAR(32)  DEFAULT NULL COMMENT '构建日志编号',
building int   DEFAULT NULL COMMENT '{0:还未构建或已构建完毕,1:构建中}',
estimatedDuration int  DEFAULT NULL COMMENT '预期构建时长（毫秒）',
duration int  DEFAULT NULL COMMENT '实际构建时长(毫秒)',
result int   DEFAULT NULL COMMENT '1:成功，2:失败,3:取消ABORTED',
consoleText longtext  DEFAULT NULL COMMENT '控制台日志',
createUserId VARCHAR ( 32 ) DEFAULT NULL,
createUserName VARCHAR ( 64 ) DEFAULT NULL,
createTime datetime DEFAULT NULL,
PRIMARY KEY ( id ) 
)  DEFAULT CHARSET=utf8;
insert into build values('1','buildName1',1,'jobName1','1',0,100,101,null,'',null,'createUserName1','1970-01-01 00:00:00');




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
readerId  VARCHAR ( 32 ) DEFAULT NULL,
readerName VARCHAR ( 64 ) DEFAULT NULL comment '输入流类型名称',
readerParameterUsername VARCHAR ( 32 ) DEFAULT NULL,
readerParameterPassword VARCHAR ( 32 ) DEFAULT NULL,
readerParameterColumn VARCHAR ( 1024 ) DEFAULT NULL,
readerParameterWhere VARCHAR ( 1024 ) DEFAULT NULL,
readerParameterConnectionJdbcUrl VARCHAR ( 1024 ) DEFAULT NULL,
readerParameterConnectionTable VARCHAR ( 32 ) DEFAULT NULL,
createUserId VARCHAR ( 32 ) DEFAULT NULL,
createUserName VARCHAR ( 64 ) DEFAULT NULL,
createTime datetime DEFAULT NULL,
PRIMARY KEY ( id ) 
)  DEFAULT CHARSET=utf8;

insert into input values(1,'inputName1',null,'mysqlreader',
'root',
'123456',
'id,name',
null,
'jdbc:mysql://127.0.0.1:3306/dataxweb?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false',
'job',
null,'createUserName1','1970-01-01 00:00:00');

DROP TABLE  IF EXISTS output;
CREATE TABLE output (
id VARCHAR ( 32 ) NOT NULL,
name VARCHAR ( 64 ) DEFAULT NULL comment '输出流名称',
writerId VARCHAR ( 32 ) DEFAULT NULL,
writerName VARCHAR ( 64 ) DEFAULT NULL comment '输出流类型名称',
writerParameterUsername VARCHAR ( 32 ) DEFAULT NULL,
writerParameterPassword VARCHAR ( 32 ) DEFAULT NULL,
writerParameterWriteMode VARCHAR ( 1024 ) DEFAULT NULL,
writerParameterColumn VARCHAR ( 1024 ) DEFAULT NULL,
writerParameterPreSql VARCHAR ( 1024 ) DEFAULT NULL,
writerParameterConnectionJdbcUrl VARCHAR ( 1024 ) DEFAULT NULL,
writerParameterConnectionTable VARCHAR ( 32 ) DEFAULT NULL,
createUserId VARCHAR ( 32 ) DEFAULT NULL,
createUserName VARCHAR ( 64 ) DEFAULT NULL,
createTime datetime DEFAULT NULL,
PRIMARY KEY ( id ) 
)  DEFAULT CHARSET=utf8;

insert into output values(1,'outputName1',null,'mysqlwriter',
'root',
'123456',
'insert',
'id,name',
'delete from test',
'jdbc:mysql://127.0.0.1:3306/dataxweb?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false',
'test',
null,'createUserName1','1970-01-01 00:00:00');


DROP TABLE  IF EXISTS transformer;
CREATE TABLE transformer (
id VARCHAR ( 32 ) NOT NULL,
name VARCHAR ( 64 )  comment '交换清洗名称',
transformerType VARCHAR (64) DEFAULT NULL comment '交换清洗类型',
transformerParameterCode text DEFAULT NULL comment '交换清洗代码',
transformerParameterExtraPackage text DEFAULT NULL comment '交换清洗引入的 包名引入语句，换行符结尾，如果存在多个引入，每一个语句还是分号结尾，也就是多行',
createUserId VARCHAR ( 32 ) DEFAULT NULL,
createUserName VARCHAR ( 64 ) DEFAULT NULL,
createTime datetime DEFAULT NULL,
PRIMARY KEY ( id ) 
)  DEFAULT CHARSET=utf8;

insert into transformer values(1,'transformer','dx_groovy',
'Column column = record.getColumn(1);\nString oriValue = column.asString();\nString newValue = oriValue.substring(0, 3);\nrecord.setColumn(1, new StringColumn(newValue));\nreturn record;',
'import groovy.json.JsonSlurper;',
null,'createUserName1','1970-01-01 00:00:00');

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





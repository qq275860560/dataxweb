show VARIABLES like '%max_allowed_packet%';
set global max_allowed_packet = 4*100*1024*1024*10

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
status int DEFAULT NULL,
progress double  DEFAULT NULL,
createUserId VARCHAR ( 32 ) DEFAULT NULL,
createUserName VARCHAR ( 64 ) DEFAULT NULL,
createTime datetime DEFAULT NULL,
PRIMARY KEY ( id ) 
) ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_bin;

insert into job values(1,'jobName1',1,"inputName1",null,"mysqlreader",1,"outputName1",null,"mysqlwriter",1,1,0.0,null,"createUserName1","1970-01-01 00:00:00");
insert into job values(2,'jobName2',2,"inputName2",null,"mysqlreader",2,"outputName2",null,"mysqlwriter",1,1,0.0,null,"createUserName2","1970-01-01 00:00:00");


DROP TABLE  IF EXISTS test;
CREATE TABLE test (
id VARCHAR ( 32 ) NOT NULL,
name VARCHAR ( 64 ) DEFAULT NULL,
PRIMARY KEY ( id ) 
) ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_bin;

 

DROP TABLE  IF EXISTS input;
CREATE TABLE input (
id VARCHAR ( 32 ) NOT NULL,
name VARCHAR ( 64 ) DEFAULT NULL,

readerId  VARCHAR ( 32 ) DEFAULT NULL,
readerName VARCHAR ( 64 ) DEFAULT NULL,

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
) ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_bin;

insert into input values(1,"inputName1",null,"mysqlreader",
"root",
"123456",
"id,name",
null,
"jdbc:mysql://127.0.0.1:3306/dataxweb?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false",
"job",
null,"createUserName1","1970-01-01 00:00:00");







 

DROP TABLE  IF EXISTS output;
CREATE TABLE output (
id VARCHAR ( 32 ) NOT NULL,
name VARCHAR ( 64 ) DEFAULT NULL,
writerId VARCHAR ( 32 ) DEFAULT NULL,
writerName VARCHAR ( 64 ) DEFAULT NULL,

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
) ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_bin;

insert into output values(1,"outputName1",null,"mysqlwriter",
"root",
"123456",
"insert",
"id,name",
"delete from test",
"jdbc:mysql://127.0.0.1:3306/dataxweb?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false",
"test",
null,"createUserName1","1970-01-01 00:00:00");









DROP TABLE  IF EXISTS plugin;
CREATE TABLE plugin (
id VARCHAR ( 32 ) NOT NULL,
name VARCHAR ( 64 ) DEFAULT NULL,
type int DEFAULT NULL comment '{0:reader,1:writer}',
readme longText   DEFAULT NULL  comment 'markdown格式的使用说明',
source LongBlob   DEFAULT NULL  comment '源码的zip压缩包对应的二进制数组,压缩包顶层目录必须有pom.xml,src,doc，参考https://github.com/qq275860560/dataxweb/blob/master/src/main/resources/static/mysqlreader-source.zip?raw=true,确保执行mvn install能正常编译',
distribute LongBlob   DEFAULT NULL  comment '发布包的zip压缩包对应的二进制数组,发布包顶层目录必须有plugin.json,plugin_job_template.json和可执行jar，参考https://github.com/qq275860560/dataxweb/blob/master/src/main/resources/static/mysqlreader-distribute.zip?raw=true',
createUserId VARCHAR ( 32 ) DEFAULT NULL,
createUserName VARCHAR ( 64 ) DEFAULT NULL,
createTime datetime DEFAULT NULL,
PRIMARY KEY ( id ) 
) ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_bin;

insert into plugin values(1,'mysqlreader',0,null,null,null,null,"createUserName1","1970-01-01 00:00:00");
insert into plugin values(2,'mysqlwriter',1,null,null,null,null,"createUserName1","1970-01-01 00:00:00"); 




#!/bin/bash
set -xev
source /etc/profile
#
echo "当前目录"
SHELL_FOLDER=$(cd "$(dirname "$0")";pwd)
echo ${SHELL_FOLDER}
cd ${SHELL_FOLDER}
#

#执行打包
cd ${SHELL_FOLDER}
timeout 600 mvn clean  install -DskipTests -Dgpg.skip
#mvn test -Dtest=com.github.qq275860560.PluginTest#plugin

#代码分析
cd ${SHELL_FOLDER}
mvn sonar:sonar  || true

#(git add   src README.md pom.xml || true) && (git commit -m "commit" || true) && git push

mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.resources.staticLocations=classpath:/static2/"

apidoc -i ./ -o ./src/main/resources/static/doc
curl http://localhost:8081/job/job20190706233929/1/logText/progressiveText?start=0
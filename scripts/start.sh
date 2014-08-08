#!/bin/bash


if /usr/bin/which java &>/dev/null; then
    version=`java -version 2>&1 | awk 'NR==1{print $3}'`
    current_java_version=${version:1:3}
    if [[ ${current_java_version} > 1.5 ]];then
            java -jar easyxms.jar
    else
        echo "Java Version Not Matched,Please Install JDK/JRE 1.6+"
    fi
else
	echo "Java not Found,Please install JDK,Please Install JDK/JRE 1.6+"
fi
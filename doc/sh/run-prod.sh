#!/bin/bash
#https://www.cnblogs.com/tonyauto/p/10330043.html
#JAVA_HOME=/usr/java/default


#java虚拟机启动参数
JAVA_OPTS="-server -Xms800m -Xmx800m -Xmn256m -Xss256k -XX:PermSize=256M -XX:MaxPermSize=512M -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=5 -XX:GCTimeRatio=19 -Xnoclassgc -XX:+DisableExplicitGC -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+UseCMSCompactAtFullCollection -XX:CMSFullGCsBeforeCompaction=0 -XX:-CMSParallelRemarkEnabled -XX:CMSInitiatingOccupancyFraction=70 -XX:SoftRefLRUPolicyMSPerMB=0 -XX:+PrintClassHistogram -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC -Xloggc:/app/logs/newslib-app-api/gc.log "

#这里可替换为你自己的执行程序，其他代码无需更改
APP_NAME=wya-prod-1.0.0-SNAPSHOT.jar
APP_USER=tomcat

#SHUTDOWN_WAIT is wait time in seconds for java proccess to stop
SHUTDOWN_WAIT=120

workdir=/app/web/msp

#使用说明，用来提示输入参数
usage() {
    echo "Usage: sh 执行脚本.sh [start|stop|restart|status]"
    exit 1
}

#检查程序是否在运行
is_exist(){
  pid=`ps -ef|grep $APP_NAME|grep -v grep|awk '{print $2}' `
  #如果不存在返回1，存在返回0
  if [ -z "${pid}" ]; then
   return 1
  else
    return 0
  fi
}

# @args <beg> <end>
# return random integer in [<beg>, <end>)
function random_range() {
    local beg=$1
    local end=$2
    echo $((RANDOM % ($end - $beg) + $beg))
}

#启动方法
start(){
  echo "${APP_NAME}"
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is already running. pid=${pid} ."
  else
    #port=$(random_range 30000 60000)
    port=8090
    nohup java $JAVA_OPTS -jar $workdir/$APP_NAME --server.port=$port > /dev/null 2>&1 >nohup.$port &
    nohup java $JAVA_OPTS -jar $workdir/$APP_NAME --server.port=8091 > /dev/null 2>&1 >nohup.8091 &
    #nohup java $JAVA_OPTS -jar $workdir/$APP_NAME --server.port=8092 > /dev/null 2>&1 >nohup.8092 &
    is_exist
    if [ $? -eq "0" ]; then
       echo "${APP_NAME} started. pid=${pid}, port=$port."
    fi
  fi
}

#停止方法
stop(){
  is_exist
  if [ $? -eq "0" ]; then
    kill $pid


    let kwait=$SHUTDOWN_WAIT
    count=0;
    #until [ `ps -p $pid | grep -c $pid` = '0' ] || [ $count -gt $kwait ]
    #do
    #  echo -n -e "\n\e[00;31mwaiting for processes to exit\e[00m";
    #  sleep 1
    #  let count=$count+1;
    #done
    echo -n -e "\n\e[00;31mwaiting for processes to exit\e[00m";

    if [ $count -gt $kwait ]; then
      echo -n -e "\n\e[00;31mkilling processes didn't stop after $SHUTDOWN_WAIT seconds\e[00m"
      kill -9 $pid
    fi

    echo "${APP_NAME} stoped."
  else
    echo "${APP_NAME} is not running"
  fi
}

#输出运行状态
status(){
  is_exist
  if [ $? -eq "0" ]; then
    echo "${APP_NAME} is running. Pid is ${pid}"
  else
    echo "${APP_NAME} is NOT running."
  fi
}

#重启
restart(){
  stop
  sleep 2
  start
}

#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
  "start")
    start
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  "restart")
    restart
    ;;
  *)
    usage
    ;;
esac

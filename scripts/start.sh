#!/bin/bash

ROOT_PATH="/home/ec2-user/LikeKNU"
JAR="$ROOT_PATH/LikeKNU.jar"

APP_LOG="$ROOT_PATH/application.log"
ERROR_LOG="$ROOT_PATH/error.log"
START_LOG="$ROOT_PATH/start.log"

NOW=$(date +%c)

echo "[$NOW] Copy $JAR" >> $START_LOG
cp $ROOT_PATH/build/libs/*.jar $JAR

echo "[$NOW] Run $JAR" >> $START_LOG
source ~/.bash_profile
nohup java -jar $JAR --spring.profiles.active=prod > $APP_LOG 2> $ERROR_LOG &

SERVICE_PID=$(pgrep -f $JAR)
echo "[$NOW] Application PID: $SERVICE_PID" >> $START_LOG
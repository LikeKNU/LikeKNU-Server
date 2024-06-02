#!/bin/bash

ROOT_PATH="/home/ec2-user/LikeKNU"
JAR="$ROOT_PATH/LikeKNU.jar"
STOP_LOG="$ROOT_PATH/stop.log"
SERVICE_PID=$(pgrep -f $JAR)

if [ -z "$SERVICE_PID" ]; then
  echo "Service not found" >> $STOP_LOG
else
  echo "Terminate service " >> $STOP_LOG
  kill -9 "$SERVICE_PID"
fi
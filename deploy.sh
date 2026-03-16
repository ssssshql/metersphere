#!/bin/bash

set -e

IMAGE="ssssshql/metersphere:v3.x"
CONTAINER_NAME="metersphere"
PORT=8081

echo "=== metersphere 一键重装脚本 ==="

# 停止并删除现有容器
if docker ps -a --format '{{.Names}}' | grep -q "^${CONTAINER_NAME}$"; then
    echo "停止并删除旧容器..."
    docker stop $CONTAINER_NAME >/dev/null 2>&1 || true
    docker rm $CONTAINER_NAME >/dev/null 2>&1 || true
fi

# 拉取最新镜像
echo "拉取最新镜像..."
docker pull $IMAGE


# 启动新容器
echo "启动新容器..."

msctl reload

echo ""
echo "=== 启动成功 ==="
echo "访问地址: http://localhost:$PORT"
echo ""
echo "查看日志: docker logs -f $CONTAINER_NAME"
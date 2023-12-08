# 使用官方的 OpenJDK 8 镜像作为基础镜像
FROM openjdk:8-jdk-alpine

# 设置工作目录
WORKDIR /app

# 将本地的 JAR 文件复制到工作目录
COPY novel-service.jar .

# 暴露应用程序的端口（如果有需要的话）
EXPOSE 8001

# 定义启动命令
CMD ["java", "-jar", "novel-service.jar"]

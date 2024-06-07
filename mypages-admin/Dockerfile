# 如果 docker 镜像中没有 java8，会自动下载
FROM java:8
# 维护者信息
MAINTAINER M1Yellow
# 将本地文件夹挂载到当前容器，指定/tmp目录并持久化到Docker数据文件夹，因为Spring Boot使用的内嵌Tomcat容器默认使用/tmp作为工作目录
VOLUME /tmp

# 添加自己的项目到 app.jar 中，app 名字自定
ARG JAR_FILE
ADD target/${JAR_FILE} mypages.jar
#ADD *.jar mypages.jar
#COPY *.jar /mypages.jar
# 运行过程中创建一个app.jar文件
#RUN bash -c 'touch /mypages.jar'

#CMD ["--server.port=8081"]

# 指定时区
ENV TZ='Asia/Shanghai'
#RUN ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo Asis/Shanghai > /etc/timezone

# 开放端口
EXPOSE 8081

# ENTRYPOINT 指定容器运行后默认执行的命令
#ENTRYPOINT ["java", "-jar", "/mypages.jar", "--spring.profiles.active=dev"]
# java.security.egd=file:/dev/./urandom 的目的是为了缩短 Tomcat 启动的时间
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/mypages.jar"]

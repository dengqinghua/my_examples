FROM ubuntu_dengqinghua
MAINTAINER dengqinghua "dengqinghua.42@gmail.com"
RUN apt-get update -yqq; apt-get install -yqq redis-server redis-tools
EXPOSE 6379
ENTRYPOINT ["/usr/bin/redis-server", "--protected-mode no"]

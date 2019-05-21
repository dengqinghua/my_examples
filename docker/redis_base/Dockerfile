# docker build -t dengqinghua/redis .
FROM ubuntu_dengqinghua
MAINTAINER dengqinghua "dengqinghua.42@gmail.com"

RUN apt-get -yqq update
RUN apt-get -yqq install software-properties-common python-software-properties
RUN add-apt-repository ppa:chris-lea/redis-server
RUN apt-get -yqq update
RUN apt-get -yqq install redis-server redis-tools

VOLUME [ "/var/lib/redis", "/var/log/redis" ]

EXPOSE 6379

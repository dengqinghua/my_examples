FROM ubuntu_dengqinghua
MAINTAINER dengqinghua "dengqinghua.42@gmail.com"
RUN apt-get update -yqq; apt-get install -yqq ruby ruby-dev build-essential redis-tools
RUN gem install --no-ri --no-rdoc sinatra json redis

RUN mkdir -p /opt/webapp

EXPOSE 4567
CMD ["/opt/webapp/bin/webapp"]

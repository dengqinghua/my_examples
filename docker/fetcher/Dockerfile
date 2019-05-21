# docker build -t dengqinghu/fetcher .
# docker run -t -i --name fetcher dengqinghu/fetcher https://tomcat.apache.org/tomcat-7.0-doc/appdev/sample/sample.war
FROM ubuntu_dengqinghua
MAINTAINER dengqinghua "dengqinghua.42@gmail.com"
RUN apt-get -yqq update
RUN apt-get -yqq install wget
VOLUME [ "/var/lib/tomcat7/webapps/" ]
WORKDIR /var/lib/tomcat7/webapps/
ENTRYPOINT [ "wget" ]
CMD [ "-?" ]

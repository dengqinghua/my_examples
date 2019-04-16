# Version: 0.0.1
FROM ubuntu_dengqinghua
MAINTAINER dengqinghua "dengqinghua.42@gmail.com"
RUN apt-get update; apt-get install -y nginx
RUN echo "daemon off;" >> /etc/nginx/nginx.conf
RUN echo "Hi, I am in your container" > /var/www/html/index.html
EXPOSE 80
ENTRYPOINT ["usr/sbin/nginx"]

# docker build -t dengqinghua/redis_primary .
# docker run -d -h redis_primary --net express --name redis_primary dengqinghua/redis_primary
FROM dengqinghua/redis
MAINTAINER dengqinghua "dengqinghua.42@gmail.com"

ENTRYPOINT [ "redis-server", "--logfile /var/log/redis/redis-server.log" ]

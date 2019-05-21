# docker build -t dengqinghua/redis_replica .
# docker run -d -h redis_replica1 --name redis_replica1 --net express dengqinghua/redis_replica
FROM dengqinghua/redis
MAINTAINER dengqinghua "dengqinghua.42@gmail.com"

ENTRYPOINT [ "redis-server", "--logfile /var/log/redis/redis-server.log", "--slaveof redis_primary 6379" ]

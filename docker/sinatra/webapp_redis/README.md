docker network create app
docker run -d --name db dengqinghua/redis
docker run -d -p 4567 --name webapp_redis -v $PWD/:/opt/webapp dengqinghua/webapp
docker network connect app db
docker network connect app webapp_redis

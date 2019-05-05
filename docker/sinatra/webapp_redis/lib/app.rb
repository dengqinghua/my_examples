require 'rubygems'
require 'sinatra'
require 'json'
require 'redis'

class App < Sinatra::Application
  # 这里的 db 是一个 container 的名字, db 这个名字很特别, 他是一个 host, 但是
  # 在上下文又没有出现过, docker 会如何解析这个 `db` 呢?
  #
  # 这里用到了 docker internal network, 需要做下面三步
  #
  # 1. 创建一个名字为 `app` 的network
  #   docker network create app
  # 2. 分别创建 db 和 当前 webapp 的 container
  #   docker run -d --name db dengqinghua/redis
  #   docker run -d --name webapp_redis dengqinghua/webapp
  # 3. 将 app 和 db, webapp_redis 关联
  #   docker network connect app db
  #   docker network connect app webapp_redis
  #
  # 这样, 我们在 webapp_redis 这个 container 里面, 可以解析 db 这个域名, 也可以写成 db.app
  redis = Redis.new(host: 'db', port: '6379')

  set :bind, '0.0.0.0'

  get '/' do
    '<h1>DockerBook Test Redis-enabled Sinatra app</h1>'
  end

  get '/json' do
    params = redis.get 'params'
    params.to_json
  end

  post '/json/?' do
    redis.set 'params', [params].to_json
    params.to_json
  end
end

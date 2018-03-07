require 'socket'

server = Socket.new(:INET, :STREAM)
addr   = Socket.pack_sockaddr_in(4481, '0.0.0.0')

server.bind(addr)
# 5 代表可以容忍的最大pending数量, 如果多于5个, 则服务则会说无法连接
# Mac中最大值: Socket::SOMAXCONN 128
server.listen(6)

#   - socket
#   - create
#   - bind
#   - listen
#   - accept
#   - connect
#   - read/write
#   - close
#   - shutdown
#   - gethostbyname and gethostbyaddr
#
# read/write/connect/accept 都是block的

# 等待client来进行连接
# connection 也是一个 Socket 对象

# loop do
#
# # accept 是 block 的, 这个server的socket会有一个 listen queue, 等待连接进来
# 如果没有连接, 则会一直 block 住
# _, connection = server.accept
# handle connection
# connection.close

  # shutdown 和 close 的区别, shutdown 会关闭掉 当前socket和它的copies
  # connection.dup
  # connection.shutdown
# end

server = TCPServer.new(4481)

# 可以用 echo hai | nc localhost 4481 来进行测试
Socket.accept_loop(server) do |connection|
  # handle connection
  # read 后面如果不加参数, 是 block 的, 也就是假如 client 一直传, 它会
  # 一直接收 但是如果 后面指定了 长度: connection.read(1024) 则表示读了
  # 1kb 的数据之后就停止
  #
  # read是 block 的, 如果后面指定了 1024, 但是它只发送了 500 怎么办, 两种途径可以让他结束block
  # 1. client发送 EOF 请求, 如 echo ds | nc localhost 4481
  # 2. 不用read, 用 partialread, partialread 是 eager, 而 read 是 lazy
  #
  #
  # buffer: read的时候也有buffer
  puts connection.read(10)

  # Close the connection once we're done reading. Lets the client
  # know that they can stop waiting for us to write something back.
  connection.close
end

# 最终可以简化为:
# Socket.tcp_server_loop(4481) do |connection|
#   connection.close
# end

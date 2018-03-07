require 'socket'

client = Socket.new(:INET, :STREAM)

# bind, connect 但是我们不调用bind方法
remote_addr = Socket.pack_sockaddr_in(80, 'baidu.com')
client.connect(remote_addr)

# client = TCPSocket.new("baidu.com", 80)
# client.write("ds")
# client.close

Socket.tcp("localhost", "4481") do |connection|
  # write buffers
  #
  # write 返回的时候, 并不是已经okay了 只是说 ruby 的IO系统将这些字节流
  # 给了操作系统
  #
  # buffer: 直接通过网络发送请求是很慢的, 发送很多小的包(send small packets)
  # 也是很慢的, 操作系统希望能够先将请求放在buffer中, 然后打包将小的信息组合
  # 起来一起发送
  #
  # 有了 buffer, 我们不用考虑一次 write 多少东西
  #
  connection.write "dsgv587" + "1" + "dsgv587"
  # 在 connection 被 close 的时候, 可以认为是 发送了 EOF 的信号给 server
  connection.close
end

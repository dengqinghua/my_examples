require 'socket'

class CloudHashClient
  class << self
    attr_accessor :host, :port
  end

  def self.get(key)
    request("GET #{key}")
  end

  def self.set(key, value)
    request("SET #{key} #{value}")
  end

  def self.request(data)
    # 这里的每一次都会创建一个新的 connection
    Socket.tcp(host, port) do |connection|
      connection.write(data)

      # 注意, 在这里不能直接 close, 而是要close write, 告诉server端, 已经写完了
      # 也就是发送一个 EOF, 如果不加这一行, server认为没有传完, 如果server用的是read
      # 而且read的字符小于 data.size 他就会等待着...
      # 然后 就一直等着... 直到 connection.close
      connection.close_write

      connection.read
    end
  end
end

CloudHashClient.host = "localhost"
CloudHashClient.port = 4481

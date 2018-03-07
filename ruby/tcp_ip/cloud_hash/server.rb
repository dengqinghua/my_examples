require 'socket'

class CloudHashServer
  def initialize(port)
    @server = TCPServer.new(port)
    puts "绑定IP端口地址为: #{@server.local_address.ip_port}"

    @storage = {}
  end

  def start
    Socket.accept_loop(@server) do |connection|
      handle(connection)
      connection.close
    end
  end

  def handle(connection)
    # 一直读, 读到 EOF
    request = connection.read(10)

    puts request

    connection.write process(request)
  end

  def process(request)
    command, key, value = request.split

    case command.upcase
    when "GET"
      @storage[key]
    when "SET"
      @storage[key] = value
    end
  end
end

CloudHashServer.new(4481).start

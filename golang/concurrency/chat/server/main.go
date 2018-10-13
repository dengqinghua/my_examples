package main

import (
	"bufio"
	"fmt"
	"log"
	"math/rand"
	"net"
)

// 假如有 N 个 client, 则会有 2*N + 1 个 goroutine
// go run concurrency/chat/server/main.go
func main() {
	listener, err := net.Listen("tcp", "localhost:8080")

	if err != nil {
		log.Fatal(err)
	}

	go broardcast()

	for {
		conn, err := listener.Accept()

		if err != nil {
			log.Print(err)
			continue
		}

		// 每一个连接过来, 就会有一个 goroutine, 即一个 client
		go handleConn(conn)
	}
}

// 一些衍生的功能思考
//	1. 连接超时: 需要设置一个 time.Tick, 每隔一段时间检查一下cli的最后一条消息的发送时间
//	2.聊天室的人数限制, 我们可以设置一个 buffered client, 即
//		entering = make(chan client, 10) // 限制一次只能进来10个人

var (
	entering = make(chan client)
	leaving  = make(chan client)
	messages = make(chan string)
)

// broardcast 包括两部分
//	1. 给 cli 发消息
//	2. 维护 leaving 和 entering
func broardcast() {
	clients := make(map[client]bool)

	for {
		select {
		case message := <-messages:
			for cli := range clients {
				// 需要给 cli 发送 string 的消息, 所以设置 cli 为 chan<- string
				cli.ch <- message
			}
		case cli := <-entering:
			clients[cli] = true

		// 该部分为什么不在 leaving 的时候去 close, 而是要到这里进行close
		// 估计是为了维护 clients 这个 map 吧!
		case cli := <-leaving:
			delete(clients, cli)
			cli.closeCh()
		}
	}
}

func interactiveWithCli(conn net.Conn, cli client) {
	input := bufio.NewScanner(conn)

	for input.Scan() {
		messages <- cli.name() + ": " + input.Text()
	}
}

func clientLeft(cli client) {
	leaving <- cli
	messages <- cli.name() + " has left"
}

func handleConn(conn net.Conn) {
	cli := newClient(getClientName(conn), conn.RemoteAddr().String())

	informNewClientArrival(cli)

	go cli.write(conn)

	// Blocking Way
	interactiveWithCli(conn, cli)

	// 如果到这一行, 则说明客户端已经失去连接了
	clientLeft(cli)
	conn.Close()
}

type client struct {
	rawName string
	ch      chan string
	addr    string
	color   string
}

func (c *client) setName(name string) {
	c.rawName = name
}

func (c *client) name() string {
	return fmt.Sprintf("%s%s\x1b[0m", c.color, c.rawName)
}

func newClient(name string, addr string) client {
	return client{
		addr:    addr,
		rawName: name,
		ch:      make(chan string),
		color:   colors[rand.Intn(len(colors))],
	}
}

func (c *client) closeCh() {
	close(c.ch)
}

func (c *client) write(conn net.Conn) {
	for msg := range c.ch {
		fmt.Fprintln(conn, msg)
	}
}

func informNewClientArrival(cli client) {
	entering <- cli
	messages <- cli.name() + " has arrived"
}

func getClientName(conn net.Conn) string {
	fmt.Fprintln(conn, "What is your name?")

	var name string
	scanner := bufio.NewScanner(conn)
	for scanner.Scan() {
		name = scanner.Text()
		fmt.Fprintln(conn, "============= Chat Begin =============")
		break
	}

	return name
}

var colors = []string{
	"\x1b[30;1m",
	"\x1b[31;1m",
	"\x1b[32;1m",
	"\x1b[33;1m",
	"\x1b[34;1m",
	"\x1b[35;1m",
	"\x1b[36;1m",
	"\x1b[37;1m",
	"\x1b[0m",
}

// echo server
package main

import (
	"bufio"
	"fmt"
	"log"
	"net"
	"strings"
	"time"
)

func main() {
	listener, err := net.Listen("tcp", "localhost:8000")

	if err != nil {
		log.Fatal(err)
	}

	for {
		conn, err := listener.Accept()

		if err != nil {
			log.Print(err)
			continue
		}

		// 如果没有添加 go, 则是 sequential 的, 一次只能处理一个请求
		go handleConn(conn)
	}
}

func handleConn(c net.Conn) {
	defer c.Close()

	input := bufio.NewScanner(c)

	for input.Scan() {
		echo(c, input.Text(), 1*time.Second)
	}

	c.Close()
}

func echo(c net.Conn, shout string, delay time.Duration) {
	fmt.Fprintln(c, "\t", strings.ToUpper(shout))

	time.Sleep(delay)

	fmt.Fprintln(c, "\t", shout)

	time.Sleep(delay)

	fmt.Fprintln(c, "\t", strings.ToLower(shout))
}

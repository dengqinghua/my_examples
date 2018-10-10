package main

import (
	"io"
	"log"
	"net"
	"time"
)

// go run concurrency/goroutines/clock/main.go
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

	for {
		t := time.Now().Format("15:04:05\n")
		_, err := io.WriteString(c, t)

		if err != nil {
			return
		}

		time.Sleep(1 * time.Second)
	}
}

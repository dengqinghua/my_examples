package main

import (
	"io"
	"log"
	"net"
	"os"
)

// go run concurrency/goroutines/nc/main.go
func main() {
	conn, err := net.Dial("tcp", "localhost:8000")

	if err != nil {
		log.Fatal(err)
	}

	defer conn.Close()
	// 将 server 端的数据, 打印到标准输出流
	go mustCopy(os.Stdout, conn)

	// 将用户输入的数据, 发送到 server 端
	mustCopy(conn, os.Stdin)
}

func mustCopy(dst io.Writer, src io.Reader) {
	if _, err := io.Copy(dst, src); err != nil {
		log.Fatal(err)
	}
}

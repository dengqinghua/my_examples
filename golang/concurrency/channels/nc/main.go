package main

import (
	"io"
	"log"
	"net"
	"os"
)

// go run concurrency/channels/nc/main.go
func main() {
	conn, err := net.Dial("tcp", "localhost:8080")

	if err != nil {
		log.Fatal(err)
	}

	// 这个 done 是 线程安全的吗? 会不会多个 goroutine 同时写?
	done := make(chan struct{})

	goroutineFunc := func() {
		// 将 server 端的数据, 打印到标准输出流
		mustCopy(os.Stdout, conn)
		done <- struct{}{}
	}

	go goroutineFunc()

	// 将用户输入的数据, 发送到 server 端
	mustCopy(conn, os.Stdin)

	conn.Close()

	// synchronization
	// 实际上都走不到这条语句...
	<-done // 等待 goroutineFunc 执行完了之后, 才退出; 这个类似于 Actor 中的 wait
}

func mustCopy(dst io.Writer, src io.Reader) {
	if _, err := io.Copy(dst, src); err != nil {
		log.Fatal(err)
	}
}

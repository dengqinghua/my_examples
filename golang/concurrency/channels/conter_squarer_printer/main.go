package main

import "fmt"

// go run concurrency/channels/conter_squarer_printer/main.go
func main() {
	naturals := make(chan int)
	squares := make(chan int)

	// Counter
	go func() {
		for x := 0; x < 100; x++ {
			naturals <- x
		}

		// 为什么不 close 的话, 会 panic deadlock?
		// fatal error: all goroutines are asleep - deadlock!

		// goroutine 1 [chan receive]:
		// main.main()
		// 	~/git/my_examples/golang/concurrency/channels/conter_squarer_printer/main.go:43 +0x138
		//
		// goroutine 6 [chan receive]:
		// main.main.func2(0xc42007a060, 0xc42007a0c0)
		// 	~/git/my_examples/golang/concurrency/channels/conter_squarer_printer/main.go:35 +0x66
		// created by main.main
		// 	~/git/my_examples/golang/concurrency/channels/conter_squarer_printer/main.go:34 +0xb5
		// exit status 2

		close(naturals)
	}()

	// Squarer
	go func() {
		for x := range naturals {
			squares <- x * x
		}

		close(squares)
	}()

	// Printer
	for x := range squares {
		fmt.Println(x)
	}
}

//  chan<-int:  send-only channel
//  <-chan int: receive-only channel

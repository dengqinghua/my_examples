package main

import "fmt"

var res []int

func mainRefactored() {
	naturals := make(chan int)
	squares := make(chan int)

	go counter(naturals)
	go squarer(naturals, squares)

	res = printer(squares)
}

// unidirectional channel types

// chan<-int 为 send-only channel
// 如果调用了 m := <-out 则直接编译不过
func counter(out chan<- int) {
	for x := 0; x < 100; x++ {
		fmt.Printf("\ncounter, %v\n", x)
		out <- x
	}

	close(out)
}

// <-chan int 为 receive-only channel
// 如果调用了 in <- 1 则直接编译不过
func squarer(in <-chan int, out chan<- int) {
	for x := range in {
		fmt.Printf("squarer, %v\n", x)
		out <- x * x
	}

	close(out)
}

func printer(in <-chan int) (res []int) {
	for x := range in {
		fmt.Printf("printer, %v\n", x)
		res = append(res, x)
	}

	return
}

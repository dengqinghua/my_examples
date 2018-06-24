package concurrency

import (
	"fmt"
	"time"
)

// M:N 模型
// M 个 gorouties 对应 N 个 OS线程
func runProcess() {
	fmt.Println("start")

	go process()

	time.Sleep(time.Millisecond * 10)
	fmt.Println("Finished")
}

func process() {
	fmt.Println("It is processing")
}

var N = 0

func dangerIncr() {
	N++
}

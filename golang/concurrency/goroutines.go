package concurrency

import (
	"fmt"
	"sync"
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

// 这里是放在一组了, 分开写也是可以的
var (
	M    = 0
	lock sync.Mutex
)

func dangerIncr(wg *sync.WaitGroup) {
	defer wg.Done()
	N++
}

func safeIncr(wg *sync.WaitGroup) {
	defer wg.Done()
	lock.Lock()
	defer lock.Unlock()

	M++
}

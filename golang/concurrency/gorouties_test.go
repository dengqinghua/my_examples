package concurrency

import (
	"fmt"
	. "github.com/franela/goblin"
	"testing"
	"time"
)

// go test my_examples/golang/concurrency -run TestRunProcess
func TestRunProcess(t *testing.T) {
	g := Goblin(t)

	g.Describe("run process in goroutines", func() {
		g.It("should run", func() {
			runProcess()
		})
	})
}

// go test my_examples/golang/concurrency -run TestRaceCondition
func TestRaceCondition(t *testing.T) {
	g := Goblin(t)

	g.Describe("incr 1000 times in goroutines", func() {
		g.It("should less than 1000", func() {
			for i := 0; i < 1000; i++ {
				go dangerIncr()
			}

			time.Sleep(time.Millisecond * 100)
			fmt.Println(N)
			// 偶偶尔会报错
			g.Assert(N < 1000).IsTrue()
		})
	})
}

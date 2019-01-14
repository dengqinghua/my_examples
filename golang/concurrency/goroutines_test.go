package concurrency

import (
	"fmt"
	. "github.com/smartystreets/goconvey/convey"
	"runtime"
	"sync"
	"testing"
	"time"
)

// go test -v my_examples/golang/concurrency -run TestStrangeGoroutine -count 4 -cpu 4 -race
func TestStrangeGoroutine(t *testing.T) {
	Convey("run in goroutines", t, func() {
		Convey("should run", func() {
			// FIXME: 为什么我每次跑都是一样的?
			// x = 0; y = 1

			// 设置到 count 10000 次就可以了! 可以看到输出结果五花八门的
			var x, y int
			go func() {
				y = 1
				fmt.Printf("x: %v\n", x)
			}()

			go func() {
				x = 1
				fmt.Printf("y: %v\n", y)
			}()
		})
	})
}

// go test -v my_examples/golang/concurrency -run TestRunProcess
func TestRunProcess(t *testing.T) {
	Convey("run process in goroutines", t, func() {
		Convey("should run", func() {
			runProcess()
		})
	})
}

// go test -v my_examples/golang/concurrency -run TestRaceCondition
func TestRaceCondition(t *testing.T) {
	Convey("incr 1000 times in goroutines", t, func() {
		Convey("should less than 1000", func() {
			for i := 0; i < 1000; i++ {
				go dangerIncr()
			}

			time.Sleep(time.Millisecond * 100)
			// 偶偶尔会报错
			So(N, ShouldBeLessThanOrEqualTo, 1000)
		})
	})

	Convey("incr 1000 times in goroutines with LOCK", t, func() {
		Convey("should exactly equal 1000", func() {
			for i := 0; i < 1000; i++ {
				go safeIncr()
			}

			time.Sleep(time.Millisecond * 100)

			So(M, ShouldEqual, 1000)
		})
	})
}

// go test -v my_examples/golang/concurrency -run TestGoRoutineMemConsumed -cpu 4
//
//		Each OS thread has a fixed-size block of memory (often as large as 2MB) for its stack,
// 	the work area where it saves the local variables of function calls that are in progress
// 	or temporarily suspended while another function is called.
// 	This fixed-size stack is simultaneously too much and too little.
func TestGoRoutineMemConsumed(t *testing.T) {
	Convey("GoRoutineMemConsumed", t, func() {
		memConsumed := func() uint64 {
			// goroutines are not garbage collected with the runtime’s ability
			// so runtime.GC() is ok
			runtime.GC()

			var s runtime.MemStats
			runtime.ReadMemStats(&s)

			return s.Sys
		}

		var c <-chan interface{}
		var wg sync.WaitGroup

		operation := func() {
			// 这里会一直阻塞, 因为没有地方往 c 这个 channel 里面传输东西
			// 所以能确保每一个这个测试里面 goroutine 都没有退出
			wg.Done()
			<-c
		}

		const numGoroutines = 1e5

		// 一共起了 1e5 个 goroutine, 即10万个 goroutine
		wg.Add(numGoroutines)

		before := memConsumed()

		for i := numGoroutines; i > 0; i-- {
			go operation()
		}

		wg.Wait()

		after := memConsumed()

		result := float64(after-before) / numGoroutines / 1000
		fmt.Printf("\n  average goroutine: %.3fkb", result)
		// 平均一个 goroutine 消耗的内存小于 3k
		So(result, ShouldBeLessThan, 3)
	})
}

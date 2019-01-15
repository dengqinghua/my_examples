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
			times := 1000

			var wg sync.WaitGroup

			wg.Add(times)
			for i := 0; i < times; i++ {
				go dangerIncr(&wg)
			}

			So(N, ShouldBeLessThanOrEqualTo, 1000)
		})
	})

	Convey("incr 1000 times in goroutines with LOCK", t, func() {
		Convey("should exactly equal 1000", func() {
			var wg sync.WaitGroup

			times := 1000
			wg.Add(times)
			for i := 0; i < 1000; i++ {
				go safeIncr(&wg)
			}

			wg.Wait()

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

		// 使用 wg.Wait 是为了确保每一个 operation() 都执行了
		wg.Wait()

		after := memConsumed()

		result := float64(after-before) / numGoroutines / 1000
		fmt.Printf("\n  average goroutine: %.3fkb", result)
		// 平均一个 goroutine 消耗的内存小于 3k
		So(result, ShouldBeLessThan, 3)
	})
}

// go test -v my_examples/golang/concurrency -run TestOnce
func TestOnce(t *testing.T) {
	Convey("It Should Be Called Only Once", t, func() {
		var wg sync.WaitGroup
		var once sync.Once

		var m int
		times := 1000
		wg.Add(times)
		for i := 0; i < times; i++ {
			defer wg.Done()
			once.Do(func() { m++ })
		}

		So(m, ShouldEqual, 1)
	})
}

// go test -v my_examples/golang/concurrency -run TestBlockingChannel
func TestBlockingChannel(t *testing.T) {
	Convey("channel is acting in a blocking way", t, func() {
		stream := make(chan interface{})

		go func() {
			// 这里 sleep 了一会儿, 是为了滞后执行
			time.Sleep(1 * time.Millisecond)
			// 如果注释掉 下面这一行, 则会报错 `all goroutines are asleep, deadlock'
			stream <- 1
		}()

		// <-stream 为阻塞的 channel
		v, ok := <-stream
		So(v, ShouldEqual, 1)
		So(ok, ShouldBeTrue)

		go func() {
			// 这里 sleep 了一会儿, 是为了滞后执行
			time.Sleep(1 * time.Millisecond)
			close(stream)
		}()

		_, ok = <-stream
		So(ok, ShouldBeFalse)
	})
}

package concurrency

import (
	"fmt"
	. "github.com/smartystreets/goconvey/convey"
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

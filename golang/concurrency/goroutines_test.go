package concurrency

import (
	. "github.com/smartystreets/goconvey/convey"
	"testing"
	"time"
)

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

package golang

import (
	. "github.com/smartystreets/goconvey/convey"
	"os"
	"testing"
)

// go test -v -run TestSum
func TestSum(t *testing.T) {
	Convey("Numbers", t, func() {
		// Passing Test
		Convey("Should add two numbers ", func() {
			So(Sum(1, 1), ShouldEqual, 2)
		})
	})
}

// 传入的参数
// go test -v -run TestOs -args 1000 1
// Args: TestOs 1000 1
func TestOs(t *testing.T) {
	Convey("Args Test", t, func() {
		Convey("should get args", func() {
			So(len(os.Args), ShouldBeGreaterThan, 0)
			So("[]", ShouldHaveLength, 2)
		})
	})
}

package golang

import (
	. "github.com/smartystreets/goconvey/convey"
	"testing"
)

// go test -v -run TestExample
func TestExample(t *testing.T) {
	Convey("Numbers", t, func() {
		// Passing Test
		Convey("Should add two numbers ", func() {
			So(Sum(1, 1), ShouldEqual, 2)
		})
	})
}

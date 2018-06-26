package golang

import (
	. "github.com/smartystreets/goconvey/convey"
	"testing"
)

// go test -v -run TestFunctions
func TestFunctions(t *testing.T) {
	Convey("TestFunctions", t, func() {
		Convey("function Add", func() {
			Convey("should get right value", func() {
				So(Add(1, 2), ShouldEqual, 3)
			})
		})

		Convey("function addShortCut", func() {
			Convey("should get right value", func() {
				So(addShortCut(1, 2), ShouldEqual, 3)
			})
		})

		Convey("function dsg", func() {
			Convey("should get right value", func() {
				boolResult, val := dsg("ds")

				So(boolResult, ShouldBeTrue)
				So(val, ShouldEqual, "dsgv587")
			})
		})

		Convey("Anonymous function", func() {
			Convey("should act with no name", func() {
				f := func(i int) int { return i }
				So(f(10), ShouldEqual, 10)
			})
		})
	})
}

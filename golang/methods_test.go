package golang

import (
	. "github.com/smartystreets/goconvey/convey"
	"reflect"
	"testing"
)

// go test -v -run TestMethods
func TestMethods(t *testing.T) {
	Convey("TestMethods", t, func() {
		Convey("point Distance", func() {
			Convey("Should return right distance", func() {
				p1 := Point{0, 0}
				p2 := Point{3, 4}

				So(p1.Distance(p2), ShouldEqual, 5)
			})
		})

		Convey("path Distance", func() {
			Convey("Should return right distance", func() {
				// path 和 path1 两种写法都是可以的
				path := Path{
					{0, 0},
					{3, 4},
				}

				path1 := Path{
					Point{0, 0},
					Point{3, 4},
				}

				So(reflect.ValueOf(path), ShouldEqual, 1)
				So(reflect.ValueOf(path1), ShouldEqual, 1)

				So(path.Distance(), ShouldEqual, 5)
				So(path1.Distance(), ShouldEqual, 5)
			})
		})
	})
}

package golang

import (
	. "github.com/smartystreets/goconvey/convey"
	"reflect"
	"strings"
	"testing"
)

const (
	A = iota
	B
)

var result interface{}

// go test github.com/dengqinghua/golang/ -v -run TestFunctions
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

		Convey("function as a value", func() {
			Convey("should call it alright", func() {
				new := dsg

				words, ok := new("scream")

				So(ok, ShouldBeFalse)
				So(words, ShouldEqual, "")
				So(reflect.TypeOf(dsg).Kind(), ShouldEqual, reflect.Func)

				// iota 的用法来着...
				So(A, ShouldResemble, 0)
				So(B, ShouldResemble, 1)
			})
		})

		Convey("function dsg", func() {
			Convey("should get right value", func() {
				val, boolResult := dsg("ds")

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

	Convey("variadic function", t, func() {
		So(sum(), ShouldEqual, 0)
		So(sum(1, 2, 3), ShouldEqual, 6)
		So(sum(1, 2, 3, 4), ShouldEqual, 10)

		values := []int{1, 2, 3, 4}
		// 可以将slice传入, 并用... 表示是多个参数
		So(sum(values...), ShouldEqual, 10)
	})

	Convey("magic function", t, func() {
		var ds func(int) int

		So(func() { ds(1) }, ShouldPanic)
		So(ds, ShouldBeZeroValue)
		So(ds, ShouldBeNil)

		// function literal
		add1 := func(r rune) rune {
			return r + 1
		}

		result = strings.Map(func(r rune) rune { return r + 1 }, "ds")
		result = strings.Map(add1, "ds")

		So(result, ShouldEqual, "et")

		for i, c := range "ds" {
			r := add1(c)

			if i == 0 {
				So(c, ShouldEqual, 100)
				So(r, ShouldEqual, 101)
			}

			if i == 1 {
				So(c, ShouldEqual, 115)
				So(r, ShouldEqual, 116)
			}
		}

		Convey("function can have state!", func() {
			// Bad Examples... 这是自己玩自己吧...
			f := func() func() int {
				var x int

				return func() int {
					x++
					return x * x
				}
			}

			So(f()(), ShouldEqual, 1)
			So(f()(), ShouldEqual, 1)
			So(f()(), ShouldEqual, 1)

			newF := f()

			// 同样, closures 也会产生问题, 如内存泄露, 值变化等
			So(newF(), ShouldEqual, 1)
			So(newF(), ShouldEqual, 4)
			So(newF(), ShouldEqual, 9)
		})
	})
}

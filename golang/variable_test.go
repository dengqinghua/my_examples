package golang

import (
	. "github.com/smartystreets/goconvey/convey"
	"testing"
)

// go test -v -run TestSpec
func TestSpec(t *testing.T) {
	Convey("Given some integer with a starting value", t, func() {
		x := 1

		Convey("When the integer is incremented", func() {
			x++

			Convey("The value should be greater by one", func() {
				So(x, ShouldEqual, 2)
			})
		})
	})
}

// go test -v -run TestVariables
// 使用
//
//   var power int 初始化
//   power := 1 用来初始化并赋值
//   power = 1  用来赋值一个已经初始化过的变量
//
func TestVariables(t *testing.T) {
	Convey("variable use var", t, func() {
		var power = 9000

		Convey("should get right v1", func() {
			So(power, ShouldEqual, 9000)
		})

		powerOne := 9000
		Convey("should get right v2", func() {
			So(powerOne, ShouldEqual, 9000)
		})

		stringA, intB := "ds", 87

		Convey("should get right v3", func() {
			So(stringA, ShouldEqual, "ds")
			So(intB, ShouldEqual, 87)
		})

		Convey("should get changed value", func() {
			// 这个不能放在外面赋值, 不知道为啥
			intB = 88
			So(intB, ShouldEqual, 88)
		})
	})
}

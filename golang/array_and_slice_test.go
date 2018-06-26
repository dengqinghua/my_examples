// package 的名字 和 文件夹的 名字一样
package golang

import (
	. "github.com/smartystreets/goconvey/convey"

	// 这里是 $GOAPTH/src/testing
	// 寻找方式为:
	// 1. ./vendor/testing
	// 2. $GOAPTH/src/testing
	// 3. $GOROOT/src/testing
	// 参考: http://lucasfcosta.com/2017/02/07/Understanding-Go-Dependency-Management.html
	"testing"
)

// One way to think about arrays is as a sort of struct but with indexed rather than named fields: a fixed-size composite value.
//
// go test -v -run TestBasicArrayOperations
func TestBasicArrayOperations(t *testing.T) {
	Convey("init and iterate", t, func() {
		Convey("should successfully init", func() {
			var scores [10]int

			scores[0] = 19
			So(scores[0], ShouldEqual, 19)

			// 轮询操作
			for index, value := range scores {
				if index == 0 {
					So(value, ShouldEqual, 19)
				} else {
					// 默认值为0
					So(value, ShouldEqual, 0)
				}
			}
		})
	})
}

// go test -v -run TestSlice
func TestSlice(t *testing.T) {
	Convey("TestSlice", t, func() {
		Convey("len and cap", func() {
			slices := make([]byte, 5, 10)
			slicesTwo := make([]byte, 8)

			Convey("should get length and capacity", func() {
				So(slices, ShouldHaveLength, 5)
				So(cap(slices), ShouldEqual, 10)

				So(slicesTwo, ShouldHaveLength, 8)
				So(cap(slicesTwo), ShouldEqual, 8)
			})
		})

		slices := make([]int, 0, 10)

		// testify乱入... 貌似 goblin 不支持测试 Panics ...
		// 直接赋值是不行的, 因为没有预分配 len
		So(func() { slices[4] = 122 }, ShouldPanic)

		Convey("append", func() {
			// 可以使用append
			slices = append(slices, 10)

			Convey("should append ok", func() {
				So(slices[0], ShouldEqual, 10)
			})
		})

		Convey("re-slice", func() {
			// 可以使用append
			slicesTwo := slices[0:8]

			Convey("should append ok", func() {
				slicesTwo[7] = 1024
				So(slicesTwo[0], ShouldEqual, 0)

				// 不再 panic
				// []int{0, 0, 0, 0, 0, 0, 0, 1024}
				So(slicesTwo[7], ShouldEqual, 1024)
			})
		})
	})
}

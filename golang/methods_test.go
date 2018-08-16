package golang

import (
	. "github.com/smartystreets/goconvey/convey"
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

				So(path.Distance(), ShouldEqual, 5)
				So(path1.Distance(), ShouldEqual, 5)
			})
		})
	})

	// 如果需要更新 Point, 则方法需要使用 (p *Point) scaleBy 的方式
	Convey("scaleBy and scaleByV2", t, func() {
		var p Point

		p = Point{1, 2}
		p.scaleBy(0.5)

		So(p.X, ShouldEqual, 0.5)
		So(p.Y, ShouldEqual, 1)

		p = Point{5, 6}
		p.scaleByV2(0.5)

		So(p.X, ShouldEqual, 5)
		So(p.Y, ShouldEqual, 6)

		p = Point{3, 4}
		// 这个&p啊, 其实就是一个参数, 我们传入指针参数的时候, 需要使用 &p
		// 等价于 p.scaleBy(10)
		(&p).scaleBy(10)

		So(p.X, ShouldEqual, 30)
		So(p.Y, ShouldEqual, 40)

		p = Point{7, 8}
		// &p 也可以调用到 scaleByV2, 也不管用
		(&p).scaleByV2(10)

		So(p.X, ShouldEqual, 7)
		So(p.Y, ShouldEqual, 8)

		// 结论: 感觉是 method 写在 Point 还是 *Point 都是可以的
		// 都能被 p 所调用到, 但是 *Point 可以去改变 p 本身, Point 的不行

		// 另外, 可以用 p 和 &p 进行调用, 两者没有什么区别
	})
}

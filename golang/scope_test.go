package golang

import (
	"testing"

	"fmt"
	. "github.com/smartystreets/goconvey/convey"
)

// golang 的 Scope Gate 有多少种情况? 在 Ruby 种有 def/class/module
// 三种, Golang 看起来只有 {} 这一种, 还有 if a := 1, a > 0 {} 中
// 的 a 这种

// go test -v -run TestStrangeScope
func TestStrangeScope(t *testing.T) {
	Convey("TestStrangeScope", t, func() {
		Convey("it should get Captital One ", func() {
			x := "hello!"

			for i := 0; i < len(x); i++ {
				x := x[i]

				if x != '!' {
					x := x + 'A' - 'a' // 变成大写

					fmt.Printf("%c", x)
				}
			}

			So(x, ShouldEqual, "hello!")
		})
	})
}

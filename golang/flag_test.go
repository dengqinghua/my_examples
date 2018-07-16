package golang

import (
	"flag"
	. "github.com/smartystreets/goconvey/convey"
	"testing"
)

// go test -run TestFlag -args -test.v
func TestFlag(t *testing.T) {
	Convey("TestFlag", t, func() {
		Convey("When there is a flag", func() {
			Convey("should parse it", func() {
				flag.String("c", "dsgv587", "Yeah, DSG v587")
				flag.Parse()

				// 需要再看下flag文档
				SkipSo(flag.Args(), ShouldEqual, 1)
			})
		})
	})
}

package golang

import (
	"testing"

	. "github.com/smartystreets/goconvey/convey"
)

// go test -v -run TestSizeOf
func TestSizeOf(t *testing.T) {
	Convey("TestSizeOf", t, func() {
		Convey("Should Return Size", func() {
			So(sizeOf(10), ShouldEqual, "1")
		})
	})
}

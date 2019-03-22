package golang

import (
	. "github.com/smartystreets/goconvey/convey"
	"testing"
)

// go test github.com/dengqinghua/golang/ -v -run TestPaincAndRecover
func TestPaincAndRecover(t *testing.T) {
	Convey("TestPaincAndRecover", t, func() {
		So(paincAndRecover(), ShouldEqual, "dsg-dsgv587")
	})
}

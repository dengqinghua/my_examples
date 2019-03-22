package golang

import (
	. "github.com/smartystreets/goconvey/convey"
	"testing"
)

// go test github.com/dengqinghua/golang/ -v -run TestYuanToFen
func TestYuanToFen(t *testing.T) {
	Convey("YuanToFen", t, func() {
		So(yuanToFen("1.122222222222222"), ShouldBeTrue)
		So(yuanToFen("0.22222"), ShouldBeTrue)
		So(yuanToFen("abc"), ShouldBeFalse)
		So(yuanToFen("1.2a"), ShouldBeFalse)
	})
}

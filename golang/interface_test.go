package golang

import (
	. "github.com/smartystreets/goconvey/convey"
	"testing"
)

// go test github.com/dengqinghua/golang/ -v -run TestMeasureArea
func TestMeasureArea(t *testing.T) {
	Convey("test measure area", t, func() {
		rect := Rect{2, 10.5}
		circle := Circle{2}

		Convey("should cacluate seperately", func() {
			So(MeasureArea(rect), ShouldEqual, 21.0)
			So(MeasureArea(circle), ShouldEqual, 12.566370614359172)
		})
	})
}

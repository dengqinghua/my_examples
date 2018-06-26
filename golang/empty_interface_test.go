package golang

import (
	. "github.com/smartystreets/goconvey/convey"
	"testing"
)

// go test -v -run TestGetType
func TestGetType(t *testing.T) {
	Convey("TestGetType", t, func() {
		Convey("when int", func() {
			Convey("should return int", func() {
				So(getType(1024), ShouldEqual, "int")
			})
		})

		Convey("when bool", func() {
			Convey("should return bool", func() {
				So(getType(true), ShouldEqual, "bool")
			})
		})

		Convey("when string", func() {
			Convey("should return string", func() {
				So(getType("true"), ShouldEqual, "string")
			})
		})

		Convey("when others", func() {
			Convey("should return unknown", func() {
				So(getType(1.24), ShouldEqual, "unknown")
			})
		})
	})
}

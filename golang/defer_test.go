package golang

import (
	. "github.com/smartystreets/goconvey/convey"
	"testing"
)

// go test -v -run TestThirdRuleDefer
func TestThirdRuleDefer(t *testing.T) {
	Convey("test the third defer rule", t, func() {
		Convey("should return 2", func() {
			So(incrI(), ShouldEqual, 2)
			So(incrIV2(), ShouldEqual, 2)
		})
	})
}

// go test -v -run TestDeferInReverseOrder
func TestDeferInReverseOrder(t *testing.T) {
	Convey("test the inverse order rule", t, func() {
		Convey("should print 4321", func() {
			deferInReverseOrder()
			var a = make(map[string]string)

			v, _ := a["ds"]

			So(v == "", ShouldBeTrue)
		})
	})
}

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
		})
	})
}

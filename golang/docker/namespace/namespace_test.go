package namespace

import (
	. "github.com/smartystreets/goconvey/convey"
	"testing"
)

// go test github.com/dengqinghua/golang/docker/namespace -v -run TestNewUTS
func TestNewUTS(t *testing.T) {
	SkipConvey("TestNewUTS", t, func() {
		So(func() { NewUTS() }, ShouldNotPanic)
	})
}

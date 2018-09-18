package golang

import (
	. "github.com/smartystreets/goconvey/convey"
	"testing"
	"unsafe"
)

// go test -v -run TestResourceReflect
func TestResourceReflect(t *testing.T) {
	Convey("TestResourceReflect", t, func() {
		res1 := resource{}
		res2 := resource{}

		So(unsafe.Pointer(&res1), ShouldNotEqual, unsafe.Pointer(&res2))

		So(unsafe.Pointer(nil), ShouldEqual, unsafe.Pointer(nil))
	})
}

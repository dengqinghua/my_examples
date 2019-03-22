package golang

import (
	"fmt"
	. "github.com/smartystreets/goconvey/convey"
	"reflect"
	"testing"
	"unsafe"
)

// go test github.com/dengqinghua/golang/ -v -run TestResourceReflect
func TestResourceReflect(t *testing.T) {
	Convey("TestResourceReflect", t, func() {
		res1 := resource{}
		res2 := resource{}

		So(unsafe.Pointer(&res1), ShouldNotEqual, unsafe.Pointer(&res2))

		So(unsafe.Pointer(nil), ShouldEqual, unsafe.Pointer(nil))

		So(fmt.Sprintf("%T", res1), ShouldEqual, "golang.resource")
		So(reflect.TypeOf(res1).String(), ShouldEqual, "golang.resource")

		// ValueOf 方法好像不能真正的得到一个值
		So(reflect.ValueOf(res1), ShouldNotEqual, res1)
	})
}

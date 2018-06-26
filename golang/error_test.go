package golang

// 在 golang 里面有一些 Predeclared identifiers. 见: https://golang.org/ref/spec#Predeclared_identifiers
//
// Types:
//   bool byte complex64 complex128 error float32 float64
//   int int8 int16 int32 int64 rune string
//   uint uint8 uint16 uint32 uint64 uintptr
//
// Constants:
//   true false iota
//
// Zero value:
//   nil
//
// Functions:
//   append cap close complex copy delete imag len
//   make new panic print println real recover
//
// 注意到其中的 error 也是 built in types, 这个是和其他语言非常不同的

import (
	. "github.com/smartystreets/goconvey/convey"
	"io"
	"testing"
)

// go test -v -run TestGetErrorInfo
func TestGetErrorInfo(t *testing.T) {
	Convey("test GetErrorInfo", t, func() {
		Convey("should get number", func() {
			n1, err1 := getErrorInfo("10")
			So(n1, ShouldEqual, 10)
			So(err1, ShouldBeNil)

			n2, err2 := getErrorInfo("ds")
			So(n2, ShouldEqual, 0)
			So(err2.Error(), ShouldEqual, "strconv.Atoi: parsing \"ds\": invalid syntax")
		})
	})

	Convey("test process", t, func() {
		Convey("should return error or nil", func() {
			So(process(10), ShouldBeNil)
			So(process(0).Error(), ShouldEqual, "Invalid Count")
		})
	})

	Convey("test IO EOF", t, func() {
		Convey("should return EOF", func() {
			So(io.EOF.Error(), ShouldEqual, "EOF")
		})
	})
}

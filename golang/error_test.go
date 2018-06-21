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
	"errors"
	. "github.com/franela/goblin"
	"io"
	"testing"
)

// go test -run TestGetErrorInfo
func TestGetErrorInfo(t *testing.T) {
	g := Goblin(t)

	g.Describe("test GetErrorInfo", func() {
		g.It("should get number", func() {
			n1, err1 := getErrorInfo("10")
			g.Assert(n1).Equal(10)
			g.Assert(err1).Equal(nil)

			n2, err2 := getErrorInfo("ds")
			g.Assert(n2).Equal(0)
			g.Assert(err2.Error()).Equal("strconv.Atoi: parsing \"ds\": invalid syntax")
		})
	})

	g.Describe("test process", func() {
		g.It("should return error or nil", func() {
			g.Assert(process(10)).Equal(nil)
			g.Assert(process(0)).Equal(errors.New("Invalid Count"))
			g.Assert(process(0).Error()).Equal("Invalid Count")
		})
	})

	g.Describe("test IO EOF", func() {
		g.It("should return EOF", func() {
			g.Assert(io.EOF.Error()).Equal("EOF")
		})
	})
}

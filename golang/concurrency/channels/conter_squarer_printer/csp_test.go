package main

import (
	. "github.com/smartystreets/goconvey/convey"
	"testing"
)

// go test -v github.com/dengqinghua/golang/concurrency/channels/conter_squarer_printer -run TestMain
func TestMain(t *testing.T) {
	Convey("It Should Have Right len", t, func() {
		res = make([]int, 0)
		mainRefactored()
		So(res, ShouldHaveLength, 100)
	})
}

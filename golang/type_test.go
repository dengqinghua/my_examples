package golang

import (
	"reflect"
	"testing"

	. "github.com/smartystreets/goconvey/convey"
)

// 这个类似于一个新的宏, 跟Ruby的C语言中的 VALUE 类似
type human string
type boy string
type girl string

const (
	Dqh boy   = "dengqinghua"
	Wjj girl  = "wangjinjin"
	Gsy human = "gaoshengyang"
)

// go test -v -run TestTypeUsage
func TestTypeUsage(t *testing.T) {
	Convey("TestTypeUsage", t, func() {
		Convey("the Dqh type should be a boy", func() {
			So(reflect.TypeOf(Dqh).Kind(), ShouldEqual, reflect.String)
			So(reflect.TypeOf(Dqh).String(), ShouldEqual, "golang.boy")

			So(Dqh, ShouldEqual, "dengqinghua")
			So(a, ShouldEqual, 3)
		})
	})
}

// go test -v -run TestTypeConversionOperarion
func TestTypeConversionOperarion(t *testing.T) {
	Convey("TestTypeConversionOperarion", t, func() {
		Convey("it should conversite type", func() {
			// human 是一个type, 她可以接收一个不是 human 类型的数据
			// 然后将其转为 human
			So(human(Dqh), ShouldEqual, "dengqinghua")
			So(reflect.TypeOf(human(Dqh)).String(), ShouldEqual, "golang.human")
		})
	})
}

// This is very strange
// 下面的代码不会报错, 而是会根据依赖去编译执行, 原文如下:

// Package initialization begins by initializing package-level variables
// in the order in which they are declared, except that dependencies are
// resolved first
var a = b + c
var b = 1
var c = 2

// TODO: package 的加载顺序是什么样的? 多个文件都有 init() 方法的时候, 选择什么样
// 的加载方式?

// go test -v -run TestStrangeInit
func TestStrangeInit(t *testing.T) {
	Convey("TestStrangeInit", t, func() {
		Convey("it should get a's value to be 3", func() {
			So(a, ShouldEqual, 3)
		})
	})
}

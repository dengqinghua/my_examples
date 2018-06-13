package main

import (
	. "github.com/franela/goblin"
	"testing"
)

// go test -run TestVariables
// 使用
//
//   var power int 初始化
//   power := 1 用来初始化并赋值
//   power = 1  用来赋值一个已经初始化过的变量
//
func TestFunctions(t *testing.T) {
	g := Goblin(t)
	g.Describe("function add", func() {
		g.It("should get right value", func() {
			g.Assert(add(1, 2)).Equal(3)
		})
	})

	g.Describe("function addShortCut", func() {
		g.It("should get right value", func() {
			g.Assert(addShortCut(1, 2)).Equal(3)
		})
	})

	g.Describe("function dsg", func() {
		g.It("should get right value", func() {
			g.Assert(dsg("ds")).Equal("dsgv587")
			g.Assert(dsg("dsg")).Equal("Get Out")

			// boolVal, stringResult := dsg("hehe")
			// g.Assert(boolVal).Equal(true)
			// g.Assert(stringResult).Equal("Get Out")
		})
	})
}

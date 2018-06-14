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
func TestVariables(t *testing.T) {
	g := Goblin(t)
	g.Describe("variable use var", func() {
		var power = 9000

		g.It("should get right", func() {
			g.Assert(power).Equal(9000)
		})

		powerOne := 9000
		g.It("should get right", func() {
			g.Assert(powerOne).Equal(9000)
		})

		stringA, intB := "ds", 87

		g.It("should get right", func() {
			g.Assert(stringA).Equal("ds")
			g.Assert(intB).Equal(87)
		})

		g.It("should get changed value", func() {
			// 这个不能放在外面赋值, 不知道为啥
			intB = 88
			g.Assert(intB).Equal(88)
		})
	})
}

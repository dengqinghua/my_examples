package main

import (
	"fmt"
	. "github.com/franela/goblin"
	"os"
	"testing"
)

// go test -run TestSum
func TestSum(t *testing.T) {
	g := Goblin(t)
	g.Describe("Numbers", func() {
		// Passing Test
		g.It("Should add two numbers ", func() {
			g.Assert(Sum(1, 1)).Equal(2)
		})
	})
}

// 传入的参数
// go test -run TestOs -args 1000 1
// Args: TestOs 1000 1
func TestOs(t *testing.T) {
	g := Goblin(t)

	fmt.Println("ds")
	g.Describe("Args Test", func() {
		g.It("should get args", func() {
			g.Assert(len(os.Args[0]) > 0).IsTrue()
		})
	})
}

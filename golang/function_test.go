package golang

import (
	. "github.com/franela/goblin"
	"testing"
)

// go test -run TestFunctions
func TestFunctions(t *testing.T) {
	g := Goblin(t)
	g.Describe("function Add", func() {
		g.It("should get right value", func() {
			g.Assert(Add(1, 2)).Equal(3)
		})
	})

	g.Describe("function addShortCut", func() {
		g.It("should get right value", func() {
			g.Assert(addShortCut(1, 2)).Equal(3)
		})
	})

	g.Describe("function dsg", func() {
		g.It("should get right value", func() {
			boolResult, val := dsg("ds")

			g.Assert(boolResult).IsTrue()
			g.Assert(val).Equal("dsgv587")

		})
	})

	g.Describe("Anonymous function", func() {
		g.It("should act with no name", func() {
			f := func(i int) int { return i }
			g.Assert(f(10)).Equal(10)
		})
	})
}

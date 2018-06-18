package golang

import (
	. "github.com/franela/goblin"
	"testing"
)

// go test -run TestExample
func TestExample(t *testing.T) {
	g := Goblin(t)
	g.Describe("Numbers", func() {
		// Passing Test
		g.It("Should add two numbers ", func() {
			g.Assert(Sum(1, 1)).Equal(2)
		})
		// Pending Test
		g.It("Should substract two numbers")
	})
}

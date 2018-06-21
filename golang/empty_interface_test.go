package golang

import (
	. "github.com/franela/goblin"
	"testing"
)

// go test -run TestGetType
func TestGetType(t *testing.T) {
	g := Goblin(t)

	g.Describe("when int", func() {
		g.It("should return int", func() {
			g.Assert(getType(1024)).Equal("int")
		})
	})

	g.Describe("when bool", func() {
		g.It("should return bool", func() {
			g.Assert(getType(true)).Equal("bool")
		})
	})

	g.Describe("when string", func() {
		g.It("should return string", func() {
			g.Assert(getType("dsgv587")).Equal("string")
		})
	})

	g.Describe("when others", func() {
		g.It("should return unknown", func() {
			g.Assert(getType(1.24)).Equal("unknown")
		})
	})
}

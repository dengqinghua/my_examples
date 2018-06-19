package golang

import (
	. "github.com/franela/goblin"
	"testing"
)

// go test -run TestMeasureArea
func TestMeasureArea(t *testing.T) {
	g := Goblin(t)

	g.Describe("test measure area", func() {
		rect := Rect{2, 10.5}
		circle := Circle{2}

		g.It("should cacluate seperately", func() {
			g.Assert(MeasureArea(rect)).Equal(21.0)
			g.Assert(MeasureArea(circle)).Equal(12.566370614359172)
		})
	})
}

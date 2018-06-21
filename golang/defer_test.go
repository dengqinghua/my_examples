package golang

import (
	. "github.com/franela/goblin"
	"testing"
)

// go test -run TestThirdRuleDefer
func TestThirdRuleDefer(t *testing.T) {
	g := Goblin(t)

	g.Describe("test the third defer rule", func() {
		g.It("should return 2", func() {
			g.Assert(incrI()).Equal(2)
		})
	})
}

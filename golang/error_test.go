package golang

import (
	. "github.com/franela/goblin"
	"testing"
)

// go test -run TestGetErrorInfo
func TestGetErrorInfo(t *testing.T) {
	g := Goblin(t)

	g.Describe("test GetErrorInfo", func() {
		g.It("should get number", func() {
			g.Assert(getErrorInfo("10")).Equal(10)
			g.Assert(getErrorInfo("ds")).Equal(0)
		})
	})
}

package golang

import (
	. "github.com/mmcloughlin/avo/build"
	. "github.com/smartystreets/goconvey/convey"
	"testing"
)

// go test -v -run TestAvo
// 将 Skiup
func TestAvo(t *testing.T) {
	Convey("test avo", t, func() {
		TEXT("Add", NOSPLIT, "func(x, y uint64) uint64")
		Doc("Add adds x and y.")
		x := Load(Param("x"), GP64())
		y := Load(Param("y"), GP64())
		ADDQ(x, y)
		Store(y, ReturnIndex(0))
		RET()

		Generate()
	})
}

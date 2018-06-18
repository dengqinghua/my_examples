// package 的名字 和 文件夹的 名字一样
package golang

import (
	. "github.com/franela/goblin"
	"github.com/stretchr/testify/assert"

	// 这里是 $GOAPTH/src/testing
	// 寻找方式为:
	// 1. ./vendor/testing
	// 2. $GOAPTH/src/testing
	// 3. $GOROOT/src/testing
	// 参考: http://lucasfcosta.com/2017/02/07/Understanding-Go-Dependency-Management.html
	"testing"
)

// One way to think about arrays is as a sort of struct but with indexed rather than named fields: a fixed-size composite value.
//
// go test -run TestBasicArrayOperations
func TestBasicArrayOperations(t *testing.T) {
	g := Goblin(t)

	g.Describe("init and iterate", func() {
		g.It("should successfully init", func() {
			var scores [10]int

			scores[0] = 19
			g.Assert(scores[0]).Equal(19)

			// 轮询操作
			for index, value := range scores {
				if index == 0 {
					g.Assert(value).Equal(19)
				} else {
					// 默认值为0
					g.Assert(value).Equal(0)
				}
			}
		})
	})
}

// go test -run TestSlice
func TestSlice(t *testing.T) {
	g := Goblin(t)
	testify := assert.New(t)

	g.Describe("len and cap", func() {
		slices := make([]byte, 5, 10)
		slicesTwo := make([]byte, 8)

		g.It("should get length and capacity", func() {
			g.Assert(len(slices)).Equal(5)
			g.Assert(cap(slices)).Equal(10)

			g.Assert(len(slicesTwo)).Equal(8)
			g.Assert(cap(slicesTwo)).Equal(8)
		})
	})

	slices := make([]int, 0, 10)

	// testify乱入... 貌似 goblin 不支持测试 Panics ...
	// 直接赋值是不行的, 因为没有预分配 len
	testify.Panics(func() { slices[4] = 122 })

	g.Describe("append", func() {
		// 可以使用append
		slices = append(slices, 10)

		g.It("should append ok", func() {
			g.Assert(slices[0]).Equal(10)
		})
	})

	g.Describe("re-slice", func() {
		// 可以使用append
		slicesTwo := slices[0:8]

		g.It("should append ok", func() {
			slicesTwo[7] = 1024
			g.Assert(slicesTwo[0]).Equal(10)
			// 不再 panic
			g.Assert(slicesTwo[7]).Equal(1024)

			// testify乱入... 貌似 goblin 不支持 Contains 这种方法...
			testify.Contains(slicesTwo, 10, 0, 0, 0, 0, 0, 0, 1024)
		})
	})
}

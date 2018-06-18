package golang

import (
	. "github.com/franela/goblin"
	"testing"
)

// go test -run TestDecrOneHeadCountInComposition
func TestDecrOneHeadCountInComposition(t *testing.T) {
	g := Goblin(t)

	g.Describe("test Decr use Composition", func() {
		g.It("should also decr", func() {
			module := &SellerModule{
				Department: &Department{
					Name:      "招商团队",
					HeadCount: 33,
				},
				owner: "dengqinghua",
				Name:  "商家模块",
			}

			// 这里是composition的作用, 相当于建立关系之后
			// 就可以将方法代理到 Department 上
			// 相当于: module.Department.DecrOneHeadCount
			module.DecrOneHeadCount()
			g.Assert(module.HeadCount).Equal(32)

			// 虽然 Department可以用作 composition, 则对应的 SellerModule
			// 也有了 Name, HeadCount, DecrOneHeadCount 等属性和方法
			//
			// 但是: 当重名了怎么办. 当module本身也有一个Name呢?
			//
			// 测试发现, 如果有Name字段, 则会优先使用自己的.
			//
			g.Assert(module.Name).Equal("商家模块")
			// 其实是生成了一个默认的叫做 Department 的field
			g.Assert(module.Department.Name).Equal("招商团队")

			// 如果有两个 compostion 都有相同的字段, 会报错
		})
	})
}

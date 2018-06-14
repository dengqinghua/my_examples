package main

import (
	. "github.com/franela/goblin"
	"testing"
)

// go test -run TestStructures
func TestStructures(t *testing.T) {
	g := Goblin(t)
	g.Describe("init", func() {
		g.Describe("assigin", func() {
			zhaoshang := Department{}

			zhaoshang.Name = "招商Ruby"
			zhaoshang.HeadCount = 33

			g.It("should assin Name and HeadCount", func() {
				g.Assert(zhaoshang.Name).Equal("招商Ruby")
				g.Assert(zhaoshang.HeadCount).Equal(33)
			})
		})

		g.Describe("should assin Name and HeadCount", func() {
			zhaoshang := Department{"招商Ruby", 33}

			g.It("should assin Name and HeadCount", func() {
				g.Assert(zhaoshang.Name).Equal("招商Ruby")
				g.Assert(zhaoshang.HeadCount).Equal(33)
			})
		})
	})
}

// go test -run TestPointer

// 为什么要有指针?
// 从功能上来说, Go传参的时候是传递的是 copies
// 从设计上来讲, TODO: 不清楚为什么要这么设计
// 在 the little go book 中有一句话:
//    > Coping a pointer is cheaper than copying a complex structure

// &X: 获得 X的 Address
// *X: 获得 Address X 对应的 Poiter, 注意 X 需要是一个 Address, 这是前提
func TestPointer(t *testing.T) {
	g := Goblin(t)

	g.Describe("given copy", func() {
		g.It("should not change zhaoshang's HeadCount to 34", func() {
			zhaoshang := Department{"招商Ruby", 33}
			addCount(zhaoshang)

			// 并没有加1, 因为传递的是值的拷贝, zhaoshang 本身并没有被修改
			g.Assert(zhaoshang.HeadCount).Equal(33)
		})
	})

	g.Describe("given pointer", func() {
		g.It("should change zhaoshang's HeadCount to 34", func() {
			zhaoshang := &Department{"招商Ruby", 33}
			reallyAddCount(zhaoshang)

			g.Assert(zhaoshang.HeadCount).Equal(34)
		})
	})

	g.Describe("given *&", func() {
		g.It("should equal", func() {
			// 获取值
			zhaoshang := Department{"招商Ruby", 33}
			g.Assert(*(&zhaoshang)).Equal(zhaoshang)
		})
	})

	g.Describe("given &*", func() {
		g.It("should equal", func() {
			zhaoshang := &Department{"招商Ruby", 33}
			g.Assert(&(*zhaoshang)).Equal(zhaoshang)
		})
	})
}

// go test -run TestDecr
func TestDecr(t *testing.T) {
	g := Goblin(t)
	g.Describe("DecrOneHeadCount", func() {
		// 用两种方式均可
		zhaoshang := &Department{"招商Ruby", 33}
		zhaoshangOne := Department{"招商Ruby", 33}

		g.It("should Decr HeadCount by 1", func() {
			zhaoshang.DecrOneHeadCount()
			zhaoshangOne.DecrOneHeadCount()

			g.Assert(zhaoshang.HeadCount).Equal(32)
			g.Assert(zhaoshangOne.HeadCount).Equal(32)
		})
	})
}

// go test -run TestConstructors
func TestConstructors(t *testing.T) {
	g := Goblin(t)
	g.Describe("newDepartment", func() {
		// 用两种方式均可
		zhaoshang := newDepartment("招商Ruby", 22)

		g.It("should return a newDepartment", func() {
			g.Assert(zhaoshang.Name).Equal("招商Ruby")
			g.Assert(zhaoshang.HeadCount).Equal(22)
		})
	})

	g.Describe("newDepartmentVersionTwo", func() {
		// 用两种方式均可
		zhaoshang := newDepartmentVersionTwo("招商Ruby", 12)

		g.It("should return a newDepartment", func() {
			g.Assert(zhaoshang.Name).Equal("招商Ruby")
			g.Assert(zhaoshang.HeadCount).Equal(12)
		})
	})

	g.Describe("newDepartmentVersionPointer", func() {
		// 用两种方式均可
		zhaoshang := newDepartmentVersionPointer("招商Ruby", 22)

		g.It("should return a newDepartment", func() {
			g.Assert(zhaoshang.Name).Equal("招商Ruby")
			g.Assert(zhaoshang.HeadCount).Equal(22)
		})
	})
}

// * 为 poiter to value of type Department, 获取到指针
func reallyAddCount(department *Department) {
	department.HeadCount += 1
}

func addCount(department Department) {
	department.HeadCount += 1
}

package golang

import (
	. "github.com/smartystreets/goconvey/convey"
	"testing"
)

// go test github.com/dengqinghua/golang/ -v -run TestStructures
func TestStructures(t *testing.T) {
	Convey("init", t, func() {
		Convey("assigin", func() {
			zhaoshang := Department{}

			zhaoshang.Name = "招商Ruby"
			zhaoshang.HeadCount = 33

			Convey("should assin Name and HeadCount", func() {
				So(zhaoshang.Name, ShouldEqual, "招商Ruby")
				So(zhaoshang.HeadCount, ShouldEqual, 33)
			})
		})

		Convey("should assin Name and HeadCount", func() {
			zhaoshang := Department{"招商Ruby", 33}

			Convey("should assin Name and HeadCount", func() {
				So(zhaoshang.Name, ShouldEqual, "招商Ruby")
				So(zhaoshang.HeadCount, ShouldEqual, 33)
			})
		})
	})
}

// go test github.com/dengqinghua/golang/ -v -run TestPointer

// 为什么要有指针?
// 从功能上来说, Go传参的时候是传递的是 copies
// 从设计上来讲, TODO: 不清楚为什么要这么设计
// 在 the little go book 中有一句话:
//    > Coping a pointer is cheaper than copying a complex structure

// &X: 获得 X的 Address
// *X: 获得 Address X 对应的 Poiter, 注意 X 需要是一个 Address, 这是前提
func TestPointer(t *testing.T) {
	Convey("TestPointer", t, func() {
		Convey("given copy", func() {
			Convey("should not change zhaoshang's HeadCount to 34", func() {
				zhaoshang := Department{"招商Ruby", 33}
				addCount(zhaoshang)

				// 并没有加1, 因为传递的是值的拷贝, zhaoshang 本身并没有被修改
				So(zhaoshang.HeadCount, ShouldEqual, 33)
			})
		})

		Convey("given pointer", func() {
			Convey("should change zhaoshang's HeadCount to 34", func() {
				zhaoshang := &Department{"招商Ruby", 33}
				reallyAddCount(zhaoshang)

				So(zhaoshang.HeadCount, ShouldEqual, 34)
			})
		})

		Convey("given *&", func() {
			Convey("should equal", func() {
				// 获取值
				zhaoshang := Department{"招商Ruby", 33}
				So(*(&zhaoshang), ShouldNotEqual, zhaoshang)
			})
		})

		Convey("given &*", func() {
			Convey("should equal", func() {
				zhaoshang := &Department{"招商Ruby", 33}
				So(&(*zhaoshang), ShouldEqual, zhaoshang)
			})
		})
	})
}

// go test github.com/dengqinghua/golang/ -v -run TestDecr
func TestDecr(t *testing.T) {
	Convey("DecrOneHeadCount", t, func() {
		// 用两种方式均可
		zhaoshang := &Department{"招商Ruby", 33}
		zhaoshangOne := Department{"招商Ruby", 33}

		Convey("should Decr HeadCount by 1", func() {
			zhaoshang.DecrOneHeadCount()
			zhaoshangOne.DecrOneHeadCount()

			So(zhaoshang.HeadCount, ShouldEqual, 32)
			So(zhaoshangOne.HeadCount, ShouldEqual, 32)
		})
	})
}

// go test github.com/dengqinghua/golang/ -v -run TestConstructors
func TestConstructors(t *testing.T) {
	Convey("newDepartment", t, func() {
		// 用两种方式均可
		zhaoshang := newDepartment("招商Ruby", 22)

		Convey("should return a newDepartment", func() {
			So(zhaoshang.Name, ShouldEqual, "招商Ruby")
			So(zhaoshang.HeadCount, ShouldEqual, 22)
		})
	})

	Convey("newDepartmentVersionTwo", t, func() {
		// 用两种方式均可
		zhaoshang := newDepartmentVersionTwo("招商Ruby", 12)

		Convey("should return a newDepartment", func() {
			So(zhaoshang.Name, ShouldEqual, "招商Ruby")
			So(zhaoshang.HeadCount, ShouldEqual, 12)
		})
	})

	Convey("newDepartmentVersionPointer", t, func() {
		// 用两种方式均可
		zhaoshang := newDepartmentVersionPointer("招商Ruby", 22)

		Convey("should return a newDepartment", func() {
			So(zhaoshang.Name, ShouldEqual, "招商Ruby")
			So(zhaoshang.HeadCount, ShouldEqual, 22)
		})
	})
}

// * 为 poiter to value of type Department, 获取到指针
func reallyAddCount(department *Department) {
	department.HeadCount++
}

func addCount(department Department) {
	department.HeadCount++
}

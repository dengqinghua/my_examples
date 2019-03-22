package golang

import (
	. "github.com/smartystreets/goconvey/convey"
	"reflect"
	"testing"
)

// go test github.com/dengqinghua/golang/ -v -run TestSpec
func TestSpec(t *testing.T) {
	Convey("Given some integer with a starting value", t, func() {
		x := 1

		Convey("When the integer is incremented", func() {
			x++

			Convey("The value should be greater by one", func() {
				So(x, ShouldEqual, 2)
			})
		})
	})
}

// go test github.com/dengqinghua/golang/ -v -run TestVariables
// 使用
//
//   var power int 初始化
//   power := 1 用来初始化并赋值
//   power = 1  用来赋值一个已经初始化过的变量
//
func TestVariables(t *testing.T) {
	Convey("variable use var", t, func() {
		var power = 9000

		Convey("should get right v1", func() {
			power++
			So(power, ShouldEqual, 9001)
		})

		powerOne := 9000
		Convey("should get right v2", func() {
			// 注意这里! power 是被隔离的!! 又变成9000 了
			// 所以这里没有before的语法, 自带了这种功能
			So(power, ShouldEqual, 9000)

			So(powerOne, ShouldEqual, 9000)
		})

		stringA, intB := "ds", 87

		Convey("should get right v3", func() {
			So(stringA, ShouldEqual, "ds")
			So(intB, ShouldEqual, 87)
		})

		Convey("should get changed value", func() {
			// 这个不能放在外面赋值, 不知道为啥
			intB = 88
			So(intB, ShouldEqual, 88)
		})
	})
}

// go test github.com/dengqinghua/golang/ -run TestPointerAsReturnVal
func TestPointerAsReturnVal(t *testing.T) {
	Convey("TestPointerAsReturnVal", t, func() {
		Convey("when func return *x", func() {
			Convey("should be weird", func() {
				// 我理解的是分配的两个地址
				// i1                            i2
				So(strangeFunc(), ShouldNotEqual, strangeFunc())
				// i1          和                  i2
				// 都指向了同一个值: 1
				So(*strangeFunc(), ShouldEqual, *strangeFunc())
			})
		})
	})
}

// go test github.com/dengqinghua/golang/ -run TestPointerAsStrangeIncr -v
func TestPointerAsStrangeIncr(t *testing.T) {
	Convey("TestPointerAsStrangeIncr", t, func() {
		Convey("should change the value itself", func() {
			i := 1

			// 这里的 i 和 strangeIncr方法 中的 *p 是一样的; 在 <The Go Programming Language>
			// 书中, 称为 Pointer aliasing:

			// Pointer aliasing is useful because it allows us to access a variable
			// without using its name, but this is a double-edged sword: to find all
			// the statements that access a variable, we have to know all its
			// aliases It’s not just pointers that create aliases; aliasing also
			// occurs when we copy values of other reference types like slices,
			// maps, and channels, and even structs, arrays, and interfaces that
			// contain these types
			j := strangeIncr(&i)

			So(i, ShouldEqual, 2)
			So(j, ShouldEqual, 2)
			So(i, ShouldEqual, j)

			// 可以用一些反射来获取Kind, Name, String等
			So(reflect.TypeOf(i).Kind(), ShouldEqual, reflect.Int)
			So(reflect.TypeOf(i).Name(), ShouldEqual, "int")
			So(reflect.TypeOf(i).String(), ShouldEqual, "int")

			So(*(&i), ShouldEqual, i)
			So(*(&i), ShouldEqual, 2)
			So(&(*(&i)), ShouldEqual, &i)
			So(*(&(*(&i))), ShouldEqual, 2) // 疯了啊... 无聊!

			So(reflect.TypeOf(&i).Kind(), ShouldEqual, reflect.Ptr)
			So(reflect.TypeOf(&i).Name(), ShouldEqual, "")
			So(reflect.TypeOf(&i).String(), ShouldEqual, "*int")

			// 两个变量的地址是不一样的!
			So(&i, ShouldNotEqual, &j)
		})

		Convey("should get the t type and kind use reflect", func() {
			// 传入的参数, t 是一个 Ptr 类型
			So(reflect.TypeOf(t).String(), ShouldEqual, "*testing.T")
			So(reflect.TypeOf(t).Kind(), ShouldEqual, reflect.Ptr)
		})
	})
}

// go test github.com/dengqinghua/golang/ -v -run TestNew
// new用得比较少, 毕竟不太直观
func TestNew(t *testing.T) {
	Convey("new is return a Ptr", t, func() {
		Convey("new int is not the same", func() {
			So(new(int), ShouldNotEqual, new(int))
		})

		Convey("new struct is the same ... OR NOT", func() {
			So(new(struct{}), ShouldEqual, new(struct{}))
			So(new([0]int), ShouldEqual, new([0]int))
			So(new([]int), ShouldNotEqual, new([]int))
		})

		Convey("type is a Ptr to int", func() {
			So(reflect.TypeOf(new(int)).Kind(), ShouldEqual, reflect.Ptr)
			So(reflect.TypeOf(new(int)).String(), ShouldEqual, "*int")
		})
	})
}

// &X: 获得 X的 Address
// *X: 获得 Address X 对应的 Poiter, 注意 X 需要是一个 Address, 这是前提
// 到底什么是 Poiter? *X 到底是什么呢? 我认为 *X 代表的就是真实的内存
// 我们改变 *X, 即真实地改变了X本身.
//
// Also See TestPointer() in structures_test.go
func strangeFunc() *int {
	var i = 1
	return &i
}

// 传入的参数, p 是一个 Ptr 类型
func strangeIncr(p *int) int {
	// p++ 会报错, 因为 p 不是 int, 而是一个 Pointer,
	// 通过 reflect.TypeOf(p).Kind() 可以知道, 她是一个 reflect.Ptr
	// 如果是 Ptr 类型, 需要通过 *Ptr 取到他的内存的值

	// 在上面的例子可以知道 i: = 1; &i 也是 reflect.Ptr 类型
	// 那么 *(&i) 也可以取到 i 在内存的值
	*p++ // 需要使用 *p 取到 int

	return *p
}

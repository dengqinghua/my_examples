package golang

func Add(a int, b int) int {
	return a + b
}

// 简便的方式, 跟 add 方法类似
func addShortCut(a, b int) int {
	return a + b
}

// FIXME: 什么时候会需要这个bool?
// 解答: 有时候不需要知道错误是什么, 所以采用的 bool, 告诉
//  我成功还是失败, 其他的我不care
// 如果说需要错误原因的话, 则需要用errors; 可以使用 errors.New() 方法
func dsg(name string) (string, bool) {
	if name == "ds" {
		return "dsgv587", true
	} else {
		return "", false
	}
}

// Functions are first-class types

type firstClassFunction func(a, b int) int

// 这个例子举得不好, 以后有好例子再添加上, 暂时不写测试了
func makeFunc(a, b int) int {
	var f firstClassFunction

	return f(1, 2)
}

func sum(allValues ...int) int {
	var total int

	for _, n := range allValues {
		total += n
	}

	return total
}

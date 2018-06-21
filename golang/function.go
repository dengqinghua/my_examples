package golang

func Add(a int, b int) int {
	return a + b
}

// 简便的方式, 跟 add 方法类似
func addShortCut(a, b int) int {
	return a + b
}

// Fixme: 什么时候会需要这个bool?
func dsg(name string) (bool, string) {
	if name == "ds" {
		return true, "dsgv587"
	} else {
		return false, "Get Out"
	}
}

// Functions are first-class types

type firstClassFunction func(a, b int) int

// 这个例子举得不好, 以后有好例子再添加上, 暂时不写测试了
func makeFunc(a, b int) int {
	var f firstClassFunction

	return f(1, 2)
}

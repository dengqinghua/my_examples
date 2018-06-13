package main

func add(a int, b int) int {
	return a + b
}

// 简便的方式, 跟 add 方法类似
func addShortCut(a, b int) int {
	return a + b
}

// Fixme: 什么时候会需要这个bool?
func dsg(name string) (bool string) {
	if name == "ds" {
		return "dsgv587"
	} else {
		return "Get Out"
	}
}

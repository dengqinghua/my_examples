package main

import (
	"fmt"
	// 这里的 my_examples/golang 很难以理解, 因为
	// 已经在 my_examples 目录下了...

	// 解释为: "my_examples/golang" 目录是针对于 src 目录下的
	// 真实的是加载了 $GOAPTH/src/my_examples/golang/*.go
	"my_examples/golang"
)

func main() {
	fmt.Println("dsgv587")
	fmt.Println(golang.Add(1, 2))
}

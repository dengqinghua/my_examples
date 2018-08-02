package golang

import (
	"fmt"
)

func incrI() (i int) {
	defer func() { i++ }()
	i = 1

	return
}

func incrIV2() (i int) {
	defer func() { i++ }()
	i = 1

	return i
}

func deferInReverseOrder() {
	// 打印顺序为 4, 3, 2, 1
	defer func() {
		fmt.Print("1")
	}()

	defer func() {
		fmt.Print("2")
	}()

	defer func() {
		fmt.Print("3")
	}()

	defer func() {
		fmt.Print("4")
	}()
}

package golang

import (
	"fmt"
	"strconv"
)

//
// Atoi 这个方法不会panic, 而是将错误作为返回值
// 在 Go Proverbs 中 提到
//   - Errors are values.
//   - Don't panic.
//
func getErrorInfo(str string) int {
	n, err := strconv.Atoi(str)

	if err != nil {
		fmt.println(err)

		return 0
	} else {
		return n
	}
}

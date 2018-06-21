package golang

import (
	"errors"
	"strconv"
)

//
// Atoi 这个方法不会panic, 而是将错误作为返回值
// 在 Go Proverbs 中 提到
//   - Errors are values.
//   - Don't panic.
//
// 参考: https://stackoverflow.com/a/22171548/8186609
//
func getErrorInfo(str string) (n int, err error) {
	// 还可以这样! https://blog.golang.org/defer-panic-and-recover 学习到了!
	n, err = strconv.Atoi(str)
	return
}

// 返回的是一个 error 类型. Errors are values
func process(count int) error {
	if count < 1 {
		return errors.New("Invalid Count")
	} else {
		return nil
	}
}

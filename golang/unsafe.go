package golang

import (
	"fmt"
	"unsafe"
)

func sizeOf(num int) string {
	return fmt.Sprint(unsafe.Sizeof(false))
}

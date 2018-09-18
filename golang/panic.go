package golang

import (
	"fmt"
)

func paincAndRecover() (res string) {
	defer func() {
		r := recover()

		if r != nil {
			res = fmt.Sprint(r) + "-" + "dsgv587"
		}
	}()

	panic("dsg")
}

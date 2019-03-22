package concurrency

import (
	. "github.com/smartystreets/goconvey/convey"
	"sync"
	"testing"
)

// go test -v github.com/dengqinghua/golang/concurrency -run TestPool
func TestPool(t *testing.T) {
	Convey("It Should Be Act Like a Pool", t, func() {
		var numsCreated int

		calcPool := &sync.Pool{
			New: func() interface{} {
				numsCreated++
				mem := make([]byte, 1024) // 每一次New都会有1KB
				return &mem
			},
		}

		calcPool.Put(calcPool.New())
		calcPool.Put(calcPool.New())
		calcPool.Put(calcPool.New())
		calcPool.Put(calcPool.New())

		works := 1024 * 1024

		var wg sync.WaitGroup
		wg.Add(works)

		for i := works; i > 0; i-- {
			go func() {
				defer wg.Done()

				mem := calcPool.Get().(*[]byte)
				defer calcPool.Put(mem)
			}()
		}

		wg.Wait()

		So(numsCreated, ShouldEqual, 4)
	})
}

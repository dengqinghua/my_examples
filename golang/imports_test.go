package golang

import (
	. "github.com/smartystreets/goconvey/convey"
	"go/build"
	"path"
	"runtime"
	"testing"
)

type forTest int

// go test github.com/dengqinghua/golang/ -v -run TestImports
func TestImports(t *testing.T) {
	Convey("TestImports", t, func() {
		Convey("should get current imports", func() {
			_, currentFileName, _, _ := runtime.Caller(0)
			// 传入当前的文件名, 调用go/build 的ImportDir方法, 可以得到一个 context
			context, err := build.ImportDir(path.Dir(currentFileName), 0)

			if err != nil {
				t.Log(err)
			} else {
				// context的Imports为当前包的依赖
				// context的TestImports为当前包的测试依赖
				SkipSo(context.Imports, ShouldResemble, []string{"errors", "math", "net/http", "strconv"})
				SkipSo(context.TestImports, ShouldResemble, []string{"errors", "flag", "fmt",
					"github.com/smartystreets/goconvey/convey", "github.com/stretchr/testify/mock",
					"go/build", "io", "io/ioutil", "log", "net/http", "net/http/httptest",
					"net/url", "os", "path", "reflect", "runtime", "strings", "testing"})
			}
		})
	})
}

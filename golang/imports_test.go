package golang

import (
	. "github.com/smartystreets/goconvey/convey"
	"go/build"
	"path"
	"runtime"
	"testing"
)

type forTest int

// go test -v -run TestImports
func TestImports(t *testing.T) {
	Convey("TestImports", t, func() {
		Convey("should get current imports", func() {
			res, _ := getBaiduContent("ds")
			So(res.StatusCode, ShouldEqual, 200)
			So(res.Status, ShouldEqual, "200 OK")

			_, currentFileName, _, _ := runtime.Caller(0)
			context, err := build.ImportDir(path.Dir(currentFileName), 0)

			if err != nil {
				t.Log(err)
			} else {
				SkipSo(context.Imports, ShouldResemble, []string{"errors", "math", "net/http", "strconv"})
				SkipSo(context.TestImports, ShouldResemble, []string{"errors", "flag", "fmt",
					"github.com/smartystreets/goconvey/convey", "github.com/stretchr/testify/mock",
					"go/build", "io", "io/ioutil", "log", "net/http", "net/http/httptest",
					"net/url", "os", "path", "reflect", "runtime", "strings", "testing"})
			}
		})
	})
}

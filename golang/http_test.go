package golang

import (
	. "github.com/smartystreets/goconvey/convey"
	"io/ioutil"
	"testing"
)

// go test -v -run TestGetBaiduContent
func TestGetBaiduContent(t *testing.T) {
	SkipConvey("TestGetBaiduContent", t, func() {
		Convey("should return baidu content", func() {
			res, _ := getBaiduContent("ds")
			So(res.StatusCode, ShouldEqual, 200)
			So(res.Status, ShouldEqual, "200 OK")

			defer res.Body.Close()

			body, _ := ioutil.ReadAll(res.Body)

			So(string(body), ShouldEqual, "200 OK")
		})
	})
}

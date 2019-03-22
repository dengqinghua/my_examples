package graphql

import (
	. "github.com/smartystreets/goconvey/convey"
	"testing"
)

// go test -v github.com/dengqinghua/golang/graphql -run TestHa
func TestHa(t *testing.T) {
	Convey("test avo", t, func() {
		So(Ha(), ShouldEqual, `{"data":{"hello":"world"}}`)
	})
}

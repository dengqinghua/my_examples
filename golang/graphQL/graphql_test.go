package graphql

import (
	. "github.com/smartystreets/goconvey/convey"
	"testing"
)

// go test -v my_examples/golang/graphql -run TestHa
func TestHa(t *testing.T) {
	Convey("test avo", t, func() {
		So(Ha(), ShouldEqual, `{"data":{"hello":"world"}}`)
	})
}

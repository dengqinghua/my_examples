package golang

import (
	"flag"
	. "github.com/smartystreets/goconvey/convey"
	"testing"
)

// go test github.com/dengqinghua/golang/ -v -run TestFlags
func TestFlags(t *testing.T) {
	SkipConvey("TestFlags", t, func() {
		var conf = flag.String("c", "DefaultConfig", "config files")
		flag.Parse()

		Convey("When no config files inside", func() {
			Convey("It should use default value", func() {
				So(*conf, ShouldEqual, "DefaultConfig")
			})
		})
	})
}

// go test github.com/dengqinghua/golang/ -v -run TestSpecialFlags -args -c "SpecailConfig"
func TestSpecialFlags(t *testing.T) {
	SkipConvey("TestFlags", t, func() {
		var conf = flag.String("c", "DefaultConfig", "config files")
		flag.Parse()

		Convey("When config files inside", func() {
			Convey("It should use value in command line", func() {
				So(*conf, ShouldEqual, "SpecailConfig")
			})
		})
	})
}

// The lifetime of a variable is determined only by whether or not it is reachable

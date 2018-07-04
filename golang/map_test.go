package golang

import (
	. "github.com/smartystreets/goconvey/convey"
	"testing"
)

// go test -v -run TestSetImplement
// Set的简单实现 https://stackoverflow.com/a/34020023
func TestSetImplement(t *testing.T) {
	Convey("init Set", t, func() {
		names := map[string]bool{}

		Convey("it should get", func() {
			value, ok := names["ds"]

			So(value, ShouldEqual, false)
			So(ok, ShouldBeFalse)
		})

		Convey("it should set", func() {
			names["ds"] = true
			_, ok := names["ds"]

			So(ok, ShouldBeTrue)
		})
	})
}

// go test -v -run TestBasicMap
func TestBasicMap(t *testing.T) {
	Convey("TestBasicMap", t, func() {
		Convey("get, put", func() {
			Convey("should get right", func() {
				oneMap := make(map[string]int)

				oneMap["dsgv"] = 587

				value, exist := oneMap["dsgv"]

				So(exist, ShouldBeTrue)
				So(value, ShouldEqual, 587)

				value, exist = oneMap["dsg"]

				So(exist, ShouldBeFalse)
				So(value, ShouldEqual, 0)
			})
		})

		Convey("init len", func() {
			Convey("should set init len", func() {
				oneMap := make(map[string]int, 10)
				oneMap["dsgv"] = 587

				So(oneMap, ShouldHaveLength, 1)
			})
		})

		Convey("set", func() {
			Convey("should init and range right", func() {
				oneMap := map[string]int{
					"dsgv": 587,
					"wjj":  523,
				}

				for name, value := range oneMap {
					if name == "dsgv" {
						So(value, ShouldEqual, 587)
					}

					if name == "wjj" {
						So(value, ShouldEqual, 523)
					}
				}
			})
		})

		Convey("init and range", func() {
			Convey("should init and range right", func() {
				oneMap := map[string]int{
					"dsgv": 587,
					"wjj":  523,
				}

				for name, value := range oneMap {
					if name == "dsgv" {
						So(value, ShouldEqual, 587)
					}

					if name == "wjj" {
						So(value, ShouldEqual, 523)
					}
				}
			})
		})
	})
}

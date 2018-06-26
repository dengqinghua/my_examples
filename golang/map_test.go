package golang

import (
	. "github.com/smartystreets/goconvey/convey"
	"testing"
)

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

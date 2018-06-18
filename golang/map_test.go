package golang

import (
	. "github.com/franela/goblin"
	"testing"
)

// o test -run TestBasicMap
func TestBasicMap(t *testing.T) {
	g := Goblin(t)

	g.Describe("get, put", func() {
		g.It("should get right", func() {
			oneMap := make(map[string]int)

			oneMap["dsgv"] = 587

			value, exist := oneMap["dsgv"]

			g.Assert(exist).IsTrue()
			g.Assert(value).Equal(587)

			value, exist = oneMap["dsg"]

			g.Assert(exist).IsFalse()
			g.Assert(value).Equal(0)
		})
	})

	g.Describe("init len", func() {
		g.It("should set init len", func() {
			oneMap := make(map[string]int, 10)
			oneMap["dsgv"] = 587

			g.Assert(len(oneMap)).Equal(1)
		})
	})

	g.Describe("init and range", func() {
		g.It("should init and range right", func() {
			oneMap := map[string]int{
				"dsgv": 587,
				"wjj":  523,
			}

			for name, value := range oneMap {
				if name == "dsgv" {
					g.Assert(value).Equal(587)
				}

				if name == "wjj" {
					g.Assert(value).Equal(523)
				}
			}
		})
	})
}

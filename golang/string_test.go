package golang

import (
	"fmt"
	. "github.com/smartystreets/goconvey/convey"
	"testing"
)

// go test github.com/dengqinghua/golang/ -v -run TestStrings
func TestStrings(t *testing.T) {
	Convey("It Should Prinit origin", t, func() {
		const placeOfInterest = `⌘`

		So(len(placeOfInterest), ShouldEqual, 3)

		const sample = "\xbd\xb2\x3d\xbc\x20\xe2\x8c\x98"

		fmt.Printf("\nplain string ⌘: %s\n", placeOfInterest)
		fmt.Printf("\nplain string: %s\n", placeOfInterest)
		fmt.Printf("quoted string: %+q\n", placeOfInterest)
		fmt.Printf("plain string sample: %s\n", sample)
		fmt.Printf("quoted string sample: %+q\n", sample)

		// hex bytes
		So(fmt.Sprintf("%x", placeOfInterest[0]), ShouldEqual, "e2")
		So(fmt.Sprintf("%x", placeOfInterest[1]), ShouldEqual, "8c")
		So(fmt.Sprintf("%x", placeOfInterest[2]), ShouldEqual, "98")

		Convey("Bytes", func() {
			const nihongo = "日本語"

			for _, v := range []byte(nihongo) {
				fmt.Printf("\n%x", v)
			}

			for index, runeValue := range nihongo {
				fmt.Printf("\n%#U 日本語 starts at byte position %d\n", runeValue, index)
			}

			for index, runeValue := range nihongo {
				fmt.Printf("\n%#x 日本語 Hex starts at byte position %d\n", runeValue, index)
			}

			So(len(nihongo), ShouldEqual, 9)
			for i := range nihongo {
				fmt.Printf("%d", i)
			}

			for index, runeValue := range sample {
				fmt.Printf("\n sample: %#U starts at byte position %d\n", runeValue, index)
			}
		})
	})
}

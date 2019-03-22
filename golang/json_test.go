package golang

import (
	"encoding/json"
	. "github.com/smartystreets/goconvey/convey"
	"testing"
)

// go test github.com/dengqinghua/golang/ -v -run TestJson
func TestJson(t *testing.T) {
	Convey("test json marshal", t, func() {
		movie := Movie{
			Title:  "ds",
			Year:   10,
			Actors: []string{"dsg"},
		}

		Convey("should marshal", func() {
			buffer, err := json.Marshal(movie)

			So(err, ShouldBeNil)
			So(string(buffer), ShouldEqual, "{\"Title\":\"ds\",\"released\":10,\"Actors\":[\"dsg\"]}")

			var newMovie Movie

			err = json.Unmarshal(buffer, &newMovie)

			So(err, ShouldBeNil)
			So(newMovie.Title, ShouldEqual, "ds")
		})
	})
}

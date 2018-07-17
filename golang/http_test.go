package golang

import (
	"fmt"
	. "github.com/smartystreets/goconvey/convey"
	"io/ioutil"
	"log"
	"net/http"
	"net/http/httptest"
	"net/url"
	"strings"
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

func handler(w http.ResponseWriter, r *http.Request) {
	v587 := r.URL.Query().Get("dsg")
	lang := r.URL.Query()["lang"]

	fmt.Println(r.URL.Query())
	fmt.Fprintln(w, "Hello, client: dsg-"+v587+" lang-"+strings.Join(lang, ","))
}

// go test -v -run TestGetMockServer
func TestGetMockServer(t *testing.T) {
	ts := httptest.NewServer(http.HandlerFunc(handler))
	defer ts.Close()

	Convey("HTTP Mock Server", t, func() {
		baseURL, _ := url.Parse(ts.URL)

		params := url.Values{
			"dsg":  []string{"v587"},
			"lang": []string{"ruby", "java", "golang"},
		}

		baseURL.RawQuery = params.Encode()

		fmt.Println(baseURL.RawQuery)

		res, err := http.Get(baseURL.String())
		if err != nil {
			log.Fatal(err)
		}

		greeting, err := ioutil.ReadAll(res.Body)
		defer res.Body.Close()

		if err != nil {
			log.Fatal(err)
		}

		So(strings.TrimSpace(string(greeting)), ShouldEqual, "Hello, client: dsg-v587 lang-ruby,java,golang")
	})
}

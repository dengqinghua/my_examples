package golang

import (
	"net/http"
)

func getBaiduContent(q string) (*http.Response, error) {
	return http.Get("http://baidu.com/" + q)
}

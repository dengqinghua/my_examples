package golang

import (
	"io/ioutil"
	"net/http"
)

func getBaiduContent(q string) (*http.Response, error) {
	return http.Get("http://baidu.com/" + q)
}

func GetOssSts() string {
	res, _ := http.Get("http://docker.xiguacity.cn:3042/api/mobile/common/access/aliyun_oss_token")

	body, _ := ioutil.ReadAll(res.Body)

	return string(body)
}

func httpPostRequest(uri string) string {
	client := &http.Client{}

	req, _ := http.NewRequest("POST", uri, nil)
	req.Header.Set("Content-Type", "application/x-www-form-urlencoded")

	res, _ := client.Do(req)

	body, _ := ioutil.ReadAll(res.Body)

	return string(body)
}

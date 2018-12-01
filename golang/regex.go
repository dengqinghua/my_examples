package golang

import "regexp"

func yuanToFen(yuan string) bool {
	var validID = regexp.MustCompile(`^\d+.{1}\d+$`)

	return validID.MatchString(yuan)
}

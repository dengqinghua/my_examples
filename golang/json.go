package golang

type Movie struct {
	Title  string
	Year   int `json:"released"`
	Actors []string
}

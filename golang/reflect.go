package golang

type customDefineStruct struct {
	a int
	b int
	c string
}

var res resource

type resource struct {
	cds   customDefineStruct
	other int
}

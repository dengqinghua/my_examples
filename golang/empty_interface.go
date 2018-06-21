package golang

//
// [What's the meaning of interface{}](https://stackoverflow.com/q/23148812/8186609)
// [Go Data Structures: Interfaces](https://research.swtch.com/interfaces)
//
func getType(a interface{}) string {
	switch a.(type) {
	case int:
		return "int"
	case bool:
		return "bool"
	case string:
		return "string"
	default:
		return "unknown"
	}
}

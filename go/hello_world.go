package main

// go run hello_world.go
// go build hello_world.go
// :GoRun / :GoBuild
func main() {
	println(Sum(5, 5))
}

func Sum(x int, y int) int {
	return x + y
}

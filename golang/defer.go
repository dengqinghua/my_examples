package golang

func incrI() (i int) {
	defer func() { i++ }()
	i = 1

	return
}

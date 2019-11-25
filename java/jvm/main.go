package main

import (
	"flag"
	"fmt"
	"os"
)

type Cmd struct {
	helpFlag    bool
	versionFlag bool
	cpOption    string
	class       string
	args        []string
}

func main() {
	cmd := parseCmd()
	if cmd.versionFlag {
		fmt.Println("version 0.0.0")
	} else if cmd.helpFlag || cmd.class == "" {
		printUsge()
	} else {
		startJVM(cmd)
	}
}

func startJVM(cmd *Cmd) {
	fmt.Printf("classpath:%s, class %s, args:%v\n", cmd.cpOption, cmd.class, cmd.args)
}

func parseCmd() *Cmd {
	cmd := &Cmd{}
	flag.Usage = printUsge
	flag.BoolVar(&cmd.helpFlag, "help", false, "print help message")
	flag.BoolVar(&cmd.versionFlag, "versionFlag", false, "print version message")
	flag.StringVar(&cmd.cpOption, "classpath", "", "classpath")
	flag.StringVar(&cmd.cpOption, "cp", "", "classpath")
	flag.Parse()

	args := flag.Args()

	if len(args) > 0 {
		cmd.class = args[0]
		cmd.args = args[1:]
	}
	return cmd
}

func printUsge() {
	fmt.Printf("Usage: %s [-options] class [args...]\n", os.Args[0])
}

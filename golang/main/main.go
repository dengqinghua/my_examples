package main

import (
	// 这里的 my_examples/golang 很难以理解, 因为
	// 已经在 my_examples 目录下了...

	// 解释为: "my_examples/golang" 目录是针对于 src 目录下的
	// 真实的是加载了 $GOAPTH/src/my_examples/golang/*.go
	// "github.com/dengqinghua/golang"
	"fmt"
	"github.com/dengqinghua/golang/docker/container"
	log "github.com/sirupsen/logrus"
	"github.com/urfave/cli"
	"os"
)

const usage = `mydocker is a simple container runtime implement,
the purpose of this project is to learn how docker works and how to
write docker by ourselves. Enjoy it, just for fun`

func main() {
	app := cli.NewApp()
	app.Name = "mydocker"
	app.Usage = usage

	app.Commands = []cli.Command{
		// initCommand,
		runCommand,
	}

	app.Before = func(context *cli.Context) error {
		log.SetFormatter(&log.JSONFormatter{})
		log.SetOutput(os.Stdout)
		return nil
	}

	if err := app.Run(os.Args); err != nil {
		log.Fatal(err)
	}
}

var initCommand = cli.Command{
	Name:  "init",
	Usage: "Init Container Process run users's process in container. Do not call it outside",
	Action: func(context *cli.Context) error {
		log.Infof("init come on")
		command := context.Args().Get(0)
		log.Infof("command %s", command)

		//err := container.RunControllerInitProcess(command, nil)
		return nil
	},
}

var runCommand = cli.Command{
	Name: "run",
	Usage: `Create a container with namespace and cgroups limit
	mysql run -it [command]`,
	Flags: []cli.Flag{
		cli.BoolFlag{
			Name:  "ti",
			Usage: "enable tty",
		},
	},
	Action: func(context *cli.Context) error {
		if len(context.Args()) < 1 {
			return fmt.Errorf("Missing container command")
		}

		command := context.Args().Get(0)
		tty := context.Bool("ti")
		Run(tty, command)
		return nil
	},
}

func Run(tty bool, command string) {
	parent := container.NewParentProcess(tty, command)
	if err := parent.Start(); err != nil {
		log.Error(err)
	}

	parent.Wait()
	os.Exit(-1)
}

// Package namespace 为 第二章 的代码示例
//
// Linux 当前的 Namespace 如下:
//		Mount UTS IPC PID Network User
//
// API 实现的接口有
//	clone()
//	unshare()
//	setns()
//
package namespace

import (
	"log"
	"os"
	"os/exec"
	// "os/user"
	// "strconv"
	"syscall"
)

func New() {
	cmd := exec.Command("sh")
	cmd.SysProcAttr = &syscall.SysProcAttr{
		Cloneflags: syscall.CLONE_NEWUTS |
			syscall.CLONE_NEWIPC |
			syscall.CLONE_NEWPID |
			syscall.CLONE_NEWNS |
			syscall.CLONE_NEWUSER,
	}

	cmd.Stdin, cmd.Stdout, cmd.Stderr = os.Stdin, os.Stdout, os.Stderr

	if err := cmd.Run(); err != nil {
		log.Fatal(err)
	}
}

// NewUTS 创建一个新的 UTS namespace
// UTS: UNIX Timesharing System. 他隔离是 hostname
// 和 NIS(Network Information System) Domain Name
//
// sudo go run main/main.go
// sudo pstree -pl 可以看到进程树.
//
// 通过 sudo readlink /proc/进程号/ns/uts
// 可以看到对应的 UTS 的值是不一样的
//
// 验证:
//	1. hostname -b bbb
//  2. 在另外一个地方看 hostname
//
// 可以参考: https://unix.stackexchange.com/q/183717
func NewUTS() {
	cmd := exec.Command("sh")
	cmd.SysProcAttr = &syscall.SysProcAttr{
		Cloneflags: syscall.CLONE_NEWUTS,
	}

	cmd.Stdin, cmd.Stdout, cmd.Stderr = os.Stdin, os.Stdout, os.Stderr

	if err := cmd.Run(); err != nil {
		log.Fatal(err)
	}
}

// NewIPC 创建一个新的 IPC namespace
// IPC: InterProcess Communication. 他隔离的是 消息队列
//
//	查看当前机器的消息队列
//		ipcs -q
//
//	创建一个消息队列
//
//		ipcmk -Q
//
func NewIPC() {
	cmd := exec.Command("sh")
	cmd.SysProcAttr = &syscall.SysProcAttr{
		Cloneflags: syscall.CLONE_NEWIPC,
	}

	cmd.Stdin, cmd.Stdout, cmd.Stderr = os.Stdin, os.Stdout, os.Stderr

	if err := cmd.Run(); err != nil {
		log.Fatal(err)
	}
}

// NewNS 创建一个新的 Mount namespace
//
//	查看当前进程树
//		sudo pstree -pl
//
//	查看当前进程ID
//
//		echo $$ # 可以看到当前输出的进程号为 1
//
//	这里不能用 ps 或者 top, 因为这俩命令会使用 /proc 下的东西, 这块是需要被 Mount Namespace 来进行隔离
func NewNS() {
	cmd := exec.Command("sh")
	cmd.SysProcAttr = &syscall.SysProcAttr{
		Cloneflags: syscall.CLONE_NEWNS,
	}

	cmd.Stdin, cmd.Stdout, cmd.Stderr = os.Stdin, os.Stdout, os.Stderr

	if err := cmd.Run(); err != nil {
		log.Fatal(err)
	}
}

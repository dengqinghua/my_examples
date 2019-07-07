package namespace

import (
	"log"
	"os"
	"os/exec"
	"syscall"
)

// NewUTS 创建一个新的 UTS namespace
// UTS stands for UNIX Timesharing System. 他隔离是 hostname
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

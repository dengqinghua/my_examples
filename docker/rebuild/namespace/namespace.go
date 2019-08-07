// Package namespace 为资源隔离组件
// 使用 namespace, 可以做到 UID 级别的隔离
// UTS namespace: UNIX Timesharing System
//
// 参考: https://unix.stackexchange.com/questions/183717/whats-a-uts-namespace
package namespace

import (
	"log"
	"os"
	"os/exec"
	"syscall"
)

func generate() {
	command := exec.Command("sh")

	command.SysProcAttr = &syscall.SysProcAttr{
		Cloneflags: syscall.CLONE_NEWUTS,
	}

	command.Stdin = os.Stdin
	command.Stdout = os.Stdout
	command.Stderr = os.Stderr
}

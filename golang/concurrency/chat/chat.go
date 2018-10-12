// Package chat 为聊天室的demo
package chat

// server 接收各个 client 的请求, 并进行 broadcast 到所有其他的 clients 中
//	server 用到的 goroutine:
//		1. handleConn 接收 client 的请求, 记录消息的来源
//		2. broadCast 广播给其他所有的 clients
//
// client 和 server 进行长连接, 并接收 server 端的请求
//	client 用到的 goroutine:
//		1. handleConn 接收 server 的请求
//		2. Input 发送消息进行互动

// 其他交互
//	如果新的 client 进入, 则 server 记录该 client
//	如果 client 断开连接, 则 server 删除该 client

// 等待消息则使用 select

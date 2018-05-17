package com.dengqinghua.concurrency;

import org.junit.Ignore;
import org.junit.Test;

// 通过这个命令, 向服务发送数据
// echo ohai | nc localhost 10080
public class ThreadPoolTest {
    @Ignore @Test public void runSingleThreadServerTest() throws Exception {
        ThreadPool.runSingleThreadServer();
    }

    @Ignore @Test public void runMultiThreadServerTest() throws Exception {
        ThreadPool.runMuiltThreadServerWithThreadPool();
    }
}

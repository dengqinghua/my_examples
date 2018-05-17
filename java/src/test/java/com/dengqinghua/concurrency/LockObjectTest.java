package com.dengqinghua.concurrency;

import org.junit.Test;

import java.util.concurrent.locks.Lock;

public class LockObjectTest {
    LockObject object = new LockObject();

    /**
     * 该测试用于验证锁是加在 Object 上的, 虽然他们锁的不是同一块区域,
     * 但是锁只有一把, 所以还是会互相等待
     */
    @Test public void testMutalExclusiveLock() throws Exception {
        Thread t1 = new Thread(object::lockAndSleep);
        Thread t2 = new Thread(object::lockMethod);

        t1.setName("lockAndSleep");
        t2.setName("lockMethod");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
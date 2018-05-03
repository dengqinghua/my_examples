package com.dengqinghua.concurrency;

import com.dengqinghua.util.Util;

public class LockObject {
    public void lockAndSleep() {
        synchronized (this) {
            System.out.println("In lockAndSleep");
            System.out.println("I am sleeping 1000");
            Util.sleep(1000);
            System.out.println("Ok, I release the lock");
        }
    }

    public synchronized void lockMethod() {
        System.out.println("dsgv587");
    }
}

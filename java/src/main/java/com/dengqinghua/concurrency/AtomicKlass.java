package com.dengqinghua.concurrency;

import com.dengqinghua.util.Util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

public class AtomicKlass {
    // volatile int i = 0;
    private int i = 0;
    private AtomicInteger j = new AtomicInteger(0);

    public int getJ() {
        return j.get();
    }

    // 不是线程安全的
    public void incrI() {
        // 为了让她产生Race Condition的可能性更大, 所以先让她睡1ms
        Util.sleep(1);
        i++;
    }

    // 是线程安全的
    public void incrJ() {
        Util.sleep(1);
        j.getAndIncrement();
    }

    public int getI() {
        return i;
    }
}

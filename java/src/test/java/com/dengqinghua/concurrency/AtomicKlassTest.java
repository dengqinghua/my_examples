package com.dengqinghua.concurrency;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AtomicKlassTest {
    @Test public void testRange() {
        assertThat(IntStream.range(0, 1000).toArray().length, is(1000));
        assertThat(IntStream.range(0, 1000).toArray()[0], is(0));
        assertThat(IntStream.range(0, 1000).toArray()[999], is(999));
    }

    AtomicKlass object = new AtomicKlass();

    // 不是线程安全的, 这里起了1000个线程, 将 object.i 从0开始, 加了1000次
    // 如果 打印 object.i 的值, 可以看到每次都不一样
    @Test public void testAddInMultiThread() throws Exception {
        List<Thread> threads = IntStream.range(0, 1000).mapToObj(i -> {
            return new Thread(() -> {
                object.incrI();
            });
        }).collect(Collectors.toList());

        threads.forEach(Thread::start);
        threads.forEach((thread) -> {
            try {
                thread.join();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        assertThat(object.getI(), is(lessThan(1000)));
    }

    // 是线程安全的, 使用了 synchronized 关键词
    @Test public void testAtomicAddInMultiThreadRealSync() {
        List<Thread> threads = IntStream.range(0, 1000).mapToObj(i -> {
            return new Thread(() -> {
                // 这里是线程安全的
                synchronized (object) {
                    object.incrI();
                }
            });
        }).collect(Collectors.toList());

        threads.forEach(Thread::start);
        threads.forEach((thread) -> {
            try {
                thread.join();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        assertThat(object.getI(), equalTo(1000));
    }

    // 是线程安全的, 因为 incrJ 方法是 Atomic 的
    @Test public void testAtomicAddInMultiThread() {
        List<Thread> threads = IntStream.range(0, 1000).mapToObj(i -> {
            return new Thread(() -> {
                object.incrJ();
            });
        }).collect(Collectors.toList());

        threads.forEach(Thread::start);
        threads.forEach((thread) -> {
            try {
                thread.join();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        assertThat(object.getJ(), equalTo(1000));
    }

}
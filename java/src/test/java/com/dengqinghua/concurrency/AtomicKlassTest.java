package com.dengqinghua.concurrency;

import org.junit.Ignore;
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

    @Ignore @Test public void testWait() throws Exception {
        Thread thread = new Thread(() -> {
            System.out.println("==== in wait thread ====");

            synchronized (object) {
                try {
                    object.wait();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                System.out.println("===== I am notified ======");
            }
        });

        Thread threadToNotify = new Thread(() -> {
            System.out.println("==== in notify thread ====");

            synchronized (object) {
                System.out.println("I am began to notify");
                object.notify();
            }
        });

        // 注意, 这里有顺序问题, 如果 notify 先于 wait, 后续没有再 notify 了, 那么 wait 就一直在等待着了
        thread.start();
        threadToNotify.start();

        thread.join();
        threadToNotify.join();
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
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });


        assertThat(object.getJ(), equalTo(1000));
    }
}
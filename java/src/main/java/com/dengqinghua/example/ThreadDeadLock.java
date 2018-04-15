package com.dengqinghua.example;

import com.dengqinghua.util.Util;

/**
 * 死锁例子, 并用 <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/management/jconsole.html">JConsole</a> 工具进行分析
 *
 * <pre>
 *      启动服务
 *
 *          mvn compile exec:java -Dexec.mainClass=com.dengqinghua.example.ThreadDeadLock -o
 *
 *       可以设置Xmx参数后启动:
 *
 *          export MAVEN_OPTS="-Xmx30M"; mvn compile exec:java -Dexec.mainClass=com.dengqinghua.example.ThreadDeadLock -o; unset MAVEN_OPTS
 * </pre>
 *
 */
public class ThreadDeadLock {
    /**
     * 这里构造了一个死锁的例子
     *
     * <pre>
     *     Java中有 Integer Cache 的概念, 会将 [-128, 127] 的数字进行缓存, 所以下面的方法传入的参数
     *
     *     a = 1, b = 2;
     *
     *     其实只会生成两个对象, 故不同的线程同时去获取这两个对象的锁, 但是顺序不同, 就有可能造成死锁
     * </pre>
     *
     * 在我的电脑中, 基本两三次就会遇到 死锁 的情况, 通过 jconsole 的 "Thread -> Dead Thread"
     * 部分可以看到死锁的线程信息
     *
     */
    public static void main(String[] args) {
        Util.printJcommands();

        for (int i = 0; i < 100; i++) {
            new Thread(() -> { asyncAdd(1, 2); } ).start();
            new Thread(() -> { asyncAdd(2, 1); } ).start();
        }
    }

    private static void asyncAdd(int a, int b) {
        Integer boxedA;
        Integer boxedB;

        synchronized (boxedA = Integer.valueOf(a)) {
            synchronized (boxedB = Integer.valueOf(b)) {
                System.out.println(boxedA + boxedB);
            }
        }
    }
}

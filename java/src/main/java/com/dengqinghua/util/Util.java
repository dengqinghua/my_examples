package com.dengqinghua.util;

import java.lang.management.ManagementFactory;

public class Util {
    /**
     * 获取当前进程 pid
     *
     * <pre>
     * 如果是 Java 9, 则可以通过
     *
     *      ProcessHandle.current().getPid();
     *
     *  获取当前pid, 但是这边使用的是 Java 8, 所以使用了 ManagementFactory
     * </pre>
     *
     * 参考: <a href="https://stackoverflow.com/a/7690178/8186609">How can a Java program get its own process ID?</a>
     *
     * @return 返回当前进程 pid
     */
    public static long getPid() {
        final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
        final int index = jvmName.indexOf('@');

        if (index < 1) {
            throw new RuntimeException("无法找到pid");
        }

        try {
            return Long.parseLong(jvmName.substring(0, index));
        } catch (NumberFormatException e) {
            throw new RuntimeException("解析 jvmName: " + jvmName + " 失败");
        }
    }

    public static void sleep() {
        try {
            Thread.sleep(500);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 打印调试信息, 不用每次用 jps 去查进程id
     */
    public static void printJcommands() {
        System.out.println(String.format("jvisualvm --openpid %d ", Util.getPid()));
        System.out.println(String.format("jconsole -interval=1 %d ", Util.getPid()));
    }

    public static void sleep(int duration) {
        try {
            Thread.sleep(duration);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

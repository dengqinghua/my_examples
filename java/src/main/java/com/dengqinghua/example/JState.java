package com.dengqinghua.example;

import com.dengqinghua.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于测试 内存的使用状态, 并用 <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/management/jconsole.html">JConsole</a> 工具进行分析
 *
 * <pre>
 *      启动服务
 *
 *          mvn compile exec:java -Dexec.mainClass=com.dengqinghua.example.JState -o
 *
 *       可以设置Xmx参数后启动:
 *
 *          export MAVEN_OPTS="-Xmx30M"; mvn compile exec:java -Dexec.mainClass=com.dengqinghua.example.JState -o; unset MAVEN_OPTS
 * </pre>
 *
 */
public class JState {
    static class Object {
        // 占位符, 每次都会生成一个 64k 的对象
        byte[] placeHolder = new byte[64 * 1024];
    }

    public static void main(String[] args) {
        // 打印调试命令
        Util.printJcommands();

        fillObject(100);
    }

    /**
     * 传入对象的个数, 该方法会每个 500ms 生成一个对象, 该对象占用 64k 大小
     *
     * <p>通过 jconsole 可以看到内存的增长情况, 通过设置 -Xmx30M 可以使得程序抛出 OOM 错误</p>
     *
     * @param num 对象的个数
     */
    private static void fillObject(int num) {
        List<Object> objectList = new ArrayList<>(num);

        Util.sleep(20 * 1000);

        for (int i = 0; i < num; i++) {
            objectList.add(new Object());

            System.out.println(String.format("生成第 %d 个对象, 内存为 %d kb", i + 1, 64 * (i + 1)));

            Util.sleep();
        }

        // 在这里进行了gc, 不会使得 OLD Eden Space 依旧会存在, 因为 objectList 为被清除
        System.gc();

        while (true) ;
    }
}

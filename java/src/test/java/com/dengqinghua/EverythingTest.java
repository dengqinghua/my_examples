package com.dengqinghua;

import com.dengqinghua.concurrency.LockObject;
import org.junit.Test;

import java.lang.ref.SoftReference;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class EverythingTest {
    private class InnerClass {
        private int i = 1;
    }

    private class InnerClassB {
        private int getI() {
            return (new InnerClass()).i;
        }
    }

    @Test public void testInnerClass() {
        InnerClass innerClass   = new InnerClass();
        InnerClassB innerClassB = new InnerClassB();

        // 在这里, 虽然InnerClass中 i 是 private 的, 但是在此类中 i 是可以被获取到的
        // 所以我觉得, 如果 InnerClass 已经被设置为 private 了, 她里面的变量其实都可以不做任何申明
        assertThat(innerClass.i, is(1));
        assertThat(innerClassB.getI(), is(1));
    }

    /**
     * 一个 Hash Collision 的例子
     *
     * <p>
     * 字符串 plumless 和 buckeroo, 经过 CRC32 的 Hash 算法之后, 得到的结果是一样的
     *
     * <p>
     * 参考: <a href=http://preshing.com/20110504/hash-collision-probabilities/>Hash Collision Probabilities</a>
     */
    @Test public void testHashCollision() {
        String plumless = "plumless";
        String buckeroo = "buckeroo";

        CRC32 crc32One = new CRC32();
        CRC32 crc32Two = new CRC32();

        crc32One.update(plumless.getBytes());
        crc32Two.update(buckeroo.getBytes());

        assertThat(crc32One.getValue(), is(1306201125L));
        assertThat(crc32Two.getValue(), is(1306201125L));

        assertThat(crc32One.getValue(), is(equalTo(crc32Two.getValue())));
    }

    @Test public void getProcessors() {
        assertThat(Runtime.getRuntime().availableProcessors(), is(greaterThan(1)));
    }

    @Test public void testBitOperation() {
        int i = 3, j = 5;

        assertThat(Integer.toBinaryString(i), is("11"));
        assertThat(Integer.toBinaryString(j), is("101"));

        assertThat(i | j, is(7));
        assertThat(Integer.toBinaryString(i | j), is("111"));

        assertThat(i & j, is(1));
        assertThat(Integer.toBinaryString(i & j), is("1"));

        // 这里是 HashMap 的 tableSizeFor 方法, 她接收一个 cap 参数,
        // 输出一个大于等于cap 的 2的幂次方的最小数
        int cap, n;
        cap = 17;
        n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;

        assertThat(n + 1, is(32));

        cap = 71;
        n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;

        Map<Integer, Integer> a = new HashMap<>();

        assertThat(n + 1, is(128));

        int h;
        h = "ds".hashCode();

        assertThat(h, is(3215));
        assertThat(Integer.toBinaryString(h), is("110010001111"));
        assertThat(Integer.toBinaryString(h >> 4), is("11001000"));

        // ^ 为 位异或, 相同位的设置为0, 不同位设置为1
        assertThat(Integer.toBinaryString(h ^ (h >>> 4)), is("110001000111"));
        assertThat(Integer.toBinaryString(18), is("10010"));
    }

    @Test public void testThreadPoolSizeDefinition() throws Exception {
        // 线程池的队列的容量设置为 2^29 - 1
        int count_bits = Integer.SIZE - 3, // 29位
                capacity = (1 << count_bits) - 1;

        int running = -1 << count_bits,       // 11100000000000000000000000000000 -536870912
                shutdown = 0,
                stop = 1 << count_bits,       // 00100000000000000000000000000000 536870912
                tyding = 2 << count_bits,     // 01000000000000000000000000000000 1073741824
                terminated = 3 << count_bits; // 01100000000000000000000000000000 1610612736

        assertThat(Integer.toBinaryString(running), is("11100000000000000000000000000000"));
        assertThat(running, is(-536870912));
        assertThat(Integer.toBinaryString(running).length(), is(32));

        assertThat(Integer.toBinaryString(stop), is("100000000000000000000000000000"));
        assertThat(stop, is(536870912));
        assertThat(Integer.toBinaryString(stop).length(), is(30));

        assertThat(Integer.toBinaryString(tyding), is("1000000000000000000000000000000"));
        assertThat(tyding, is(1073741824));
        assertThat(Integer.toBinaryString(tyding).length(), is(31));

        assertThat(Integer.toBinaryString(terminated), is("1100000000000000000000000000000"));
        assertThat(terminated, is(1610612736));
        assertThat(Integer.toBinaryString(terminated).length(), is(31));

        assertThat(capacity, is(536870911));
        assertThat(Integer.toBinaryString(capacity).length(), is(29));
        assertThat(Integer.toBinaryString(capacity), is("11111111111111111111111111111"));

        assertThat(Integer.toBinaryString(-1), is("11111111111111111111111111111111"));
        assertThat(Integer.toBinaryString(-1).length(), is(32));
    }

    @Test public void testInheritedMethods () throws Exception {
        LockObject object = new Inherited();

        // 在这里虽然 object 被 cast 成了 LockObject
        // 但是 object 的方法依旧是 Inherited 的方法
        object.lockAndSleep();


        int i = 1 << 2;
        // 100
        // 001
        // 101
        assertThat(i, is(4));
        assertThat(~i, is(-5));
        assertThat(Integer.toBinaryString(~i), is("11111111111111111111111111111011"));
        assertThat(1 ^ i, is(5));

        // 由于被 cast 了, inSideMethod 这个方法无法被调用到了
        // object.inSideMethod()
    }

    @Test public void testRetry() throws Exception {
        int i = 0;
        retry:
        for(;;) {
            i++;

            if (i > 10) {
                break retry;
            }
        }
    }

    /**
     * An object has state, behavior, and identity
     *
     * <pre>
     * A final class cannot be extended by any other class
     * A final variable cannot be reassigned to another value
     * A final method cannot be overridden
     *
     * Reference: https://stackoverflow.com/q/5181578/8186609
     */
    @Test public void finalTest() throws Exception {
        final int a = 1;
        final Object b;
        final TestFinalClass finalClass = new TestFinalClass();

        // 设置 a = 2 会无法通过编译
        // a = 2;

        // Object b 由于没有被赋值, 所以 b = 1 设置是ok的
        b = 1;
        // 设置 b = 2 会无法通过编译
        // b = 2;

        // 设置 属性 (state) 是可以成功的
        finalClass.a = 1;

        assertThat(finalClass.a, is(1));
    }

    // TimeUnit 插件, 太棒了!
    @Test public void testNano() throws Exception {
        assertThat(TimeUnit.DAYS.toNanos(1), is(86400000000000L));

        assertThat(TimeUnit.SECONDS.toNanos(1), is(1000000000L));
        assertThat(TimeUnit.SECONDS.toSeconds(1), is(1L));
    }

    @Test public void testIntToLong() throws Exception {
        int[] array = new int[] { 1, 2, 3, 4 };
        long[] array1 = new long[] { 5, 6, 7, 8 };

        List<Long> list = Arrays.stream(array).
                mapToLong(Long::valueOf).
                boxed().
                collect(Collectors.toList());

        assertThat(list, contains(1L, 2L, 3L, 4L));

        list.addAll(Arrays.stream(array1).boxed().collect(Collectors.toList()));

        assertThat(list, contains(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L));

        Map<Integer, List<Long>> map = list.
                stream().
                collect(Collectors.groupingBy(i -> list.indexOf(i) % 3));

        assertThat(map.get(0), contains(1L, 4L, 7L));
        assertThat(map.get(1), contains(2L, 5L, 8L));
        assertThat(map.get(2), contains(3L, 6L));
        assertThat(map.size(), is(3));
    }
}

final class TestFinalClass {
    int a;
}

// final 的 class 不能被 extended

// class TryExtendFinalClass extends TestFinalClass {
// }

class Inherited extends LockObject {
    @Override public void lockAndSleep() {
        System.out.println("In Inherited. Beigin...");

        super.lockAndSleep();

        System.out.println("In Inherited. After...");
    }

    static void staticMethod() {
    }

    public void inSideMethod() {
    }
}

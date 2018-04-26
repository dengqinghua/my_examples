package com.dengqinghua;

import org.junit.Test;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

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
        assertThat(Integer.toBinaryString(18), is(1));
    }
}

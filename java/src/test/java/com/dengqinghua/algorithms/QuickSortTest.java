package com.dengqinghua.algorithms;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class QuickSortTest {
    @Test public void sort_When_Single_One() throws Exception {
        List<Integer> list = Collections.singletonList(1);
        QuickSort.sort(list);

        assertThat(QuickSort.sort(list), is(contains(1)));
    }

    @Test public void sort_When_Two() throws Exception {
        List<Integer> list = Arrays.asList(3, 1);

        System.out.println(QuickSort.sort(list));
        assertThat(QuickSort.sort(list), is(contains(1, 3)));
    }

    @Test public void sort_When_Over_Two() throws Exception {
        List<Integer> list = Arrays.asList(3, 1, 4, 6, 10);

        System.out.println(QuickSort.sort(list));
        assertThat(QuickSort.sort(list), is(contains(1, 3, 4, 6, 10)));
    }

    @Test public void sort_When_Duplicated() throws Exception {
        List<Integer> list = Arrays.asList(3, 1, 3, 4, 6, 10);

        System.out.println(QuickSort.sort(list));
        assertThat(QuickSort.sort(list), is(contains(1, 3, 3, 4, 6, 10)));
    }

    // 在算法中经常用到 >>> 这个符号, 查了一下是位移操作
    // http://www.cnblogs.com/hongten/p/hongten_java_yiweiyunsuangfu.html
    // TODO: 需要看下int的二进制表示方式
    @Test public void testSpecialSymbols() throws Exception {
        int i = 8;
        assertThat(Integer.toBinaryString(i), is("1000"));
        assertThat(Integer.toBinaryString(i << 1), is("10000"));
        assertThat(Integer.toBinaryString(i >> 1), is("100"));
        assertThat(Integer.toBinaryString(i >>> 1), is("100"));

        i = -8;

        assertThat(Integer.toBinaryString(i), is("11111111111111111111111111111000"));
        assertThat(Integer.toBinaryString(i << 1), is("11111111111111111111111111110000"));
        assertThat(Integer.toBinaryString(i >> 1), is("11111111111111111111111111111100"));
        assertThat(Integer.toBinaryString(i >>> 1), is("1111111111111111111111111111100"));
    }
}
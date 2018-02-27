package com.dengqinghua.algorithms;

import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class QuickSortTest {
    static List<Integer> list;
    @Test public void sort_When_Single_One() throws Exception {
        list = Collections.singletonList(1);
        QuickSort.sort(list);

        assertThat(QuickSort.sort(list), is(contains(1)));
    }

    @Test public void sort_When_Two() throws Exception {
        list = Arrays.asList(3, 1);

        assertThat(QuickSort.sort(list), is(contains(1, 3)));
    }

    @Test public void sort_When_Over_Two() throws Exception {
        list = Arrays.asList(3, 1, 4, 6, 10);

        assertThat(QuickSort.sort(list), is(contains(1, 3, 4, 6, 10)));
    }

    @Test public void sort_When_Duplicated() throws Exception {
        list = Arrays.asList(3, 1, 3, 4, 6, 10);

        assertThat(QuickSort.sort(list), is(contains(1, 3, 3, 4, 6, 10)));
    }

    @Test public void sort_Array_When_Single_One() throws Exception {
        int[] array = { 1 };

        QuickSort.sort(array, 0, array.length - 1);
        assertThat(array, equalTo(new int[] { 1 }));
    }

    @Test public void sort_Array_When_Two() throws Exception {
        int[] array = { 2, 1 };

        QuickSort.sort(array, 0, array.length - 1);
        assertThat(array, equalTo(new int[] { 1, 2 }));
    }

    @Test public void sort_Array_When_Over_Two() throws Exception {
        int[] array = { 3, 2, 4, 1 };

        QuickSort.sort(array, 0, array.length - 1);
        assertThat(array, equalTo(new int[] { 1, 2, 3, 4 }));
    }

    @Test public void sort_Array_When_Multi() throws Exception {
        int[] array = { 2, 1, 3, 2, 3, 2 };

        QuickSort.sort(array, 0, array.length - 1);
        assertThat(array, equalTo(new int[] { 1, 2, 2, 2, 3, 3 }));
    }

    // 在算法中经常用到 >>> 这个符号, 查了一下是位移操作
    // http://www.cnblogs.com/hongten/p/hongten_java_yiweiyunsuangfu.html
    // TODO: 需要看下int的二进制表示方式
    @Test public void testSpecialSymbols() throws Exception {
        int i = 8;
        assertThat(Integer.toBinaryString(i), is("1000"));
        assertThat(Integer.toBinaryString(i << 1),  is("10000"));
        assertThat(Integer.toBinaryString(i >> 1),  is("100"));
        assertThat(Integer.toBinaryString(i >>> 1), is("100"));

        i = -8;
        assertThat(Integer.toBinaryString(i), is("11111111111111111111111111111000"));
        assertThat(Integer.toBinaryString(i << 1),  is("11111111111111111111111111110000"));
        assertThat(Integer.toBinaryString(i >> 1),  is("11111111111111111111111111111100"));
        assertThat(Integer.toBinaryString(i >>> 1), is("1111111111111111111111111111100"));

        i = 1;
        assertThat(i++, is(1));
        assertThat(i, is(2));

        i = 1;
        assertThat(++i, is(2));
        assertThat(i, is(2));
    }

    static Method methodSwitchPosition;
    @BeforeClass public static void setMethodVisible() throws Exception {
         methodSwitchPosition = QuickSort.class.
                 getDeclaredMethod("switchPosition", int[].class, int.class, int.class);
         methodSwitchPosition.setAccessible(true);
    }

    @Test public void testSwitchPosition_One() throws Exception {
        int[] datas = { 1 };
        methodSwitchPosition.invoke(QuickSort.class, datas, 0, 0);

        assertThat(datas, equalTo(new int[] { 1 }));
    }

    @Test public void testSwitchPosition_Two() throws Exception {
        int[] datas = { 1, 2 };
        methodSwitchPosition.invoke(QuickSort.class, datas, 0, 1);

        assertThat(datas, equalTo(new int[] { 2, 1 }));
    }

    @Test public void testSwitchPosition_Over_Two() throws Exception {
        int[] datas = { 1, 2, 3 };
        methodSwitchPosition.invoke(QuickSort.class, datas, 0, 2);

        assertThat(datas, equalTo(new int[] { 3, 1, 2 }));
    }

    @Test public void testSwitchPosition_Multi() throws Exception {
        int[] datas = { 1, 2, 3, 8, 9, 1000 };
        methodSwitchPosition.invoke(QuickSort.class, datas, 1, 4);

        assertThat(datas, equalTo(new int[] { 1, 9, 2, 3, 8, 1000 }));
    }
}
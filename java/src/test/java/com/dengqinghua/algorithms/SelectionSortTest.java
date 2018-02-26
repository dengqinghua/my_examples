package com.dengqinghua.algorithms;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class SelectionSortTest {
    @Test public void sort() throws Exception {
        int[] datas  = { 1, 8, 7, 20, 9, 10 };
        int[] result = { 1, 7, 8, 9, 10, 20 };

        assertArrayEquals(SelectionSort.sort(datas), result);
    }

    @Test public void sort_When_Has_Same_Data() throws Exception {
        int[] datas  = { 1, 7, 7, 20, 9, 10 };
        int[] result = { 1, 7, 9, 10, 20 };

        assertArrayEquals(SelectionSort.sort(datas), result);
    }
}
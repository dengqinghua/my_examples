package com.dengqinghua.algorithms;

import org.junit.Test;
import java.util.stream.IntStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BinarySearchTest {
    int[] setIntSortedArrays(int end) {
        return IntStream.range(0, end).toArray();
    }

    @Test public void find() throws Exception {
        int[] datas = setIntSortedArrays(1000);
        assertTrue(BinarySearch.find(datas, 60));
        assertTrue(BinarySearch.find(datas, 0));
        assertFalse(BinarySearch.find(datas, 1001));
    }

    @Test public void find_Complicated_Case() throws Exception {
        int[] datas = { 1, 3, 5, 8, 9, 10 };

        assertTrue(BinarySearch.find(datas, 5));
        assertFalse(BinarySearch.find(datas, 7));
        assertFalse(BinarySearch.find(datas, 90));
    }
}
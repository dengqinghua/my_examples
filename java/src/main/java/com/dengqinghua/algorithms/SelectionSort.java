package com.dengqinghua.algorithms;

import java.util.Arrays;
import java.util.stream.IntStream;

public class SelectionSort {
    /**
     * 选择排序, 时间复杂度: <b> O(n x n) </b>
     *
     *
     * @param sourceDatas 待排序的数组
     * @return 已经排好序的数组
     */
    public static int[] sort(int[] sourceDatas) {
        int[] datas = IntStream.of(sourceDatas).distinct().toArray(), // 需要去一下重复的数据
                result = new int[datas.length];
        int data, size = datas.length;

        for (int i = 0; i < size; i++) {
            data = getSmallestData(datas);
            result[i] = data;
            datas = removeData(data, datas);
        }

        return result;
    }

    /**
     * Java里面貌似没有好的直接从数组里面删除某个元素的 API, 故写了一个使用stream方式过滤的方法, 参考
     *
     *      <a href="https://stackoverflow.com/questions/112503/how-do-i-remove-objects-from-an-array-in-java">这里</a>
     *
     * @param dataToRemoved 需要移除的数据
     * @param datas 原始数组
     * @return 移除了 dataToRemoved 之后的数组
     */
    private static int[] removeData(int dataToRemoved, int[] datas) {
        return Arrays.stream(datas).
                filter(data -> data != dataToRemoved).
                toArray();
    }

    /**
     * 顺序查找最小的数, 时间复杂度: <b> O(n) </b>
     *
     * @param datas 数组
     * @return 返回 datas 中最小的数
     */
    private static int getSmallestData(int[] datas) {
        int smallestData = datas[0];

        for (int data : datas) {
            if (data < smallestData) {
                smallestData = data;
            }
        }

        return smallestData;
    }
}

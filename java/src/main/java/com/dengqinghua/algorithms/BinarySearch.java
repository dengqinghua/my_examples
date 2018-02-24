package com.dengqinghua.algorithms;

public class BinarySearch {
    /**
     * 二分查找 时间复杂度: <b>O(logn)</b>
     *
     * <pre>
     *  假设需要查找 5
     *
     *     数组 datas      left  right index
     *     1 3 5 8 9 10    0     5     3
     *     1 3 5           0     2     1
     *         5           2     2     2
     *
     *   注意 left, right 和 index 的变化情况, 尤其是 index, 她是 (leftIndex + rightIndex + 1)/2
     * </pre>
     *
     * @param datas 数组对象, 要求已经是有序的
     * @return true OR false
     */
    public static boolean find(int[] datas, int dataToFind) {
        int leftIndex = 0, rightIndex = datas.length - 1,
                index = datas.length / 2;

        while(rightIndex - leftIndex >= 0) {
            if (leftIndex == rightIndex) {
                return dataToFind == datas[index];
            }

            if (dataToFind == datas[index]) {
                return true;
            }

            if (dataToFind < datas[index]) {
                rightIndex = index - 1;
            } else {
                leftIndex = index + 1;
            }

            index = (leftIndex + rightIndex + 1) / 2;
        }

        return false;
    }
}

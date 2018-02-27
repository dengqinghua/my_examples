package com.dengqinghua.algorithms;

import java.util.ArrayList;
import java.util.List;

public class QuickSort {
    /**
     * 快速排序, 平均复杂度: O(n * logn), Worst: O(n * n), Best: O(n * logn)
     *
     * <pre>
     *     Base Case: ONE or EMPTY array
     *     Reduce Case: split array to left and right array, left side is smaller whereas right side is bigger
     * </pre>
     *
     * <pre>
     *      算法复杂度分析
     *
     *
     *      假设 输入为 [5, 3, 2, 4, 1, 8, 10, 9]
     *
     *      首先取 第一个参数为 pivot, 还剩下 3, 2, 4, 1, 8, 10, 9
     *      对于 3, 2, 4, 1, 8, 10, 9 每个都需要去和 5 比较,
     *      故这部分的算法复杂度为 O(n)
     *
     *      其次对于迭代而言, 每一个都分为两组, bigger 和 smaller, 这部分的平均复杂度为 O(logn)
     *      举个例子(下面每次都取第一个元素为pivot)
     *
     *                 3, 2, 4, 1, 8, 10, 9          O(n)
     *                  /                 \
     *               2, 1           4, 8, 10, 9      O(n)
     *               /                       \
     *             1                      8, 10, 9   O(n)
     *                                        \
     *                                        10, 9  O(n)
     *                                        /
     *                                       9       O(n)
     *
     *     因为每次都是分成左右两个部分, 最优的情况每次都是平均分 那么层数 i 满足
     *
     *          2ⁱ ≈ n
     *
     *     所以 层数 i 可以认为是 log(n)
     *
     *     上面的每一层的复杂度为 O(n) 层数的平均复杂度为 O(logn)
     *
     *     故总的复杂度为 O(n * logn)
     * </pre>
     *
     * JAVA SDK 中的 快速排序为 {@link java.util.DualPivotQuicksort}, 它用了两个pivot.
     *
     * <p>另外, 在数组长度不同的情况下, 排序的选择不一样.
     *
     * @param sourceDatas 待排序的数组
     * @return 排好序的数组
     * @see java.util.DualPivotQuicksort#sort(int[], int, int, boolean)
     */
    public static List<Integer> sort(List<Integer> sourceDatas) {
        switch (sourceDatas.size()) {
            case 0: // Base Case
                return new ArrayList<>();
            case 1: // Base Case
                return sourceDatas;
            default:
                int pivot = sourceDatas.get(0); // pivot, 枢轴

                List<Integer> smallerDatas = new ArrayList<>(),
                        biggerDatas = new ArrayList<>();

                for (int i = 1; i < sourceDatas.size(); i++) {
                    // Reduce Case
                    // 小于 pivot 的放在 smaller 组, 大于的放在 bigger 组
                    if (pivot > sourceDatas.get(i)) {
                        smallerDatas.add(sourceDatas.get(i));
                        // 将 smaller 组里面进行排序
                        smallerDatas = sort(smallerDatas);
                    } else {
                        biggerDatas.add(sourceDatas.get(i));
                        // 将 bigger 组里面进行排序
                        biggerDatas = sort(biggerDatas);
                    }
                }

                // 将结果组合起来
                smallerDatas.add(pivot);
                smallerDatas.addAll(biggerDatas);
                return smallerDatas;
        }
    }

    /**
     * 数组的 快速排序
     *
     * <p>
     *     数组的 快速排序 稍麻烦, 需要将数组元素的元素互换位置, 所以写出来远没有 List 排序的
     *  代码可读. 但是思路都是一样的
     *
     * @param sourceDatas 输入的数组
     * @param from        待排序的起始位置
     * @param to          待排序的结束位置
     */
    public static void sort(int[] sourceDatas, int from, int to) {
        // Base Case to == from
        if (to > from) {
            int pivot = from; // 取第一个元素的位置为 pivot

            for (int i = from + 1; i <= to; i++) {
                // Reduce Case
                // 小于 sourceDatas[pivot] 元素 放在 pivot 坐标的左边
                if (sourceDatas[pivot] > sourceDatas[i]) {
                    // 3 1 4 2 6  3为pivot对应的元素 和 1 比较, 发现 1小于3, 而且 1 的 index 比 3 大
                    // 要将元素 3, 1 的位置互换
                    if (pivot < i) {
                        switchPosition(sourceDatas, pivot, i);
                        pivot = i;
                    }
                // 大于等于 sourceDatas[pivot] 元素 放在 pivot 坐标的右边
                } else {
                    // 7 6 4   6为pivot对应的元素, 7 和 6 比较, 发现 7大于6, 而且 7 的 index 比 6 小
                    // 要将元素 7, 6 的位置互换
                    if (pivot > i) {
                        switchPosition(sourceDatas, i, pivot);
                        pivot = i;
                    }
                }
            }

            if (from < pivot) {
                sort(sourceDatas, from, pivot - 1);
            }

            if (to > pivot) {
                sort(sourceDatas, pivot + 1, to);
            }
        }
    }

    /**
     *  将 位置 index1 和 index2 的数组的值进行交换
     *
     * <pre>
     *  注意: 交换了之后, 她右边的也得同步移动
     *  如
     *      2, 3, 4, 1
     *
     *
     *  当 3 和 1 比较的时候, 需要将 1 移动到 3 的左边 所以 3, 4 都得往后移动一位, 变成了 2, 1, 3, 4
     *
     *  1. 1 占到 3 的位置 用临时变量保存 3
     *  2. 依次往后移动, 直到 biggerIndex
     *
     * @param datas   数组
     */
    private static void switchPosition(int[] datas, int smallerIndex, int biggerIndex) {
        if (smallerIndex > biggerIndex) {
            String msg = "Wrong params. smallerIndex: " + smallerIndex + ", biggerIndex: " + biggerIndex;
            throw new RuntimeException(msg);
        }

        if (smallerIndex == biggerIndex) {
            return;
        }

        int tmp;

        tmp = datas[smallerIndex];
        datas[smallerIndex] = datas[biggerIndex];

        int i = smallerIndex + 1;
        while (i <= biggerIndex) {
            int tmp1 = tmp;
            tmp = datas[i];  // 将datas[i]的值保存起来
            datas[i] = tmp1; // 将datas[i]的值替换

            i++;
        }
    }
}

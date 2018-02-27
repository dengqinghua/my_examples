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
     *     因为每次都是分成左右两个部分, 最优的情况每次都是平均分 那么层数m满足
     *
     *          2**m ~ n
     *
     *     其中 ** 代表阶层, ~ 代表 约等于
     *
     *     所以 层数 m 可以认为是 log(n)
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
}

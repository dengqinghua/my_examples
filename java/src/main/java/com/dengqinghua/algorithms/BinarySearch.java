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
     * <p>
     *     本示例用的是 int 作为数组的元素, 在 java 原生的 API 中, 她提供的是 List<T>.
     *
     *     在阅读了 java.util.Collections.binarySearch() 的源码之后, 发现 对于 binarySearch, 不同
     *  形式的 List , 实现方式还不一样, 部分源码如下:
     *
     * <pre>
     *     public static <T> int binarySearch(List<? extends T> list, T key, Comparator<? super T> c) {
     *          if (c==null) {
     *              return binarySearch((List<? extends Comparable<? super T>>) list, key);
     *          }
     *
     *          if (list instanceof RandomAccess || list.size() < BINARYSEARCH_THRESHOLD) {
     *              return Collections.indexedBinarySearch(list, key, c);
     *          } else {
     *              return Collections.iteratorBinarySearch(list, key, c);
     *          }
     *     }
     * </pre>
     *
     * 由上面的例子可以看到 这一句
     *
     * <pre>
     *     list instanceof RandomAccess || list.size() < BINARYSEARCH_THRESHOLD
     * </pre>
     *
     * 判断了 list 是否支持 随机读(RandomAccess), ArrayList 是支持的, 像 LinkedList 这种是不支持的, 她是 SequentialAccess
     *
     * <p>
     * 对于不支持 随机读 的大(长度 大于 BINARYSEARCH_THRESHOLD = 5000) List,
     * SDK 中选择了 iteratorBinarySearch
     *
     * @param dataToFind 待查找的数组
     * @param datas 数组对象, 要求已经是有序的
     * @return true OR false
     * @see java.util.Collections#binarySearch(java.util.List, java.lang.Object, java.util.Comparator)
     *
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

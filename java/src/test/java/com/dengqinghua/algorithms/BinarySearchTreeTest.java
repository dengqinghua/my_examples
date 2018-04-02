package com.dengqinghua.algorithms;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class BinarySearchTreeTest {
    static List<Integer> dataList;
    static Integer[] datas;
    static BinarySearchTree tree;

    @Test public void insert_One() throws Exception {
        dataList = Collections.singletonList(1);
        datas = dataList.toArray(new Integer[dataList.size()]);

        tree = BinarySearchTree.build(datas);

        assertThat(tree.preOrderTraversal(), contains(1));
        assertThat(tree.inOrderTraversal(), contains(1));
        assertThat(tree.postOrderTraversal(), contains(1));
    }

    /**
     *     3
     *  2    null
     */
    @Test public void insert_Two() throws Exception {
        dataList = Arrays.asList(3, 2);
        datas = dataList.toArray(new Integer[dataList.size()]);

        tree = BinarySearchTree.build(datas);

        assertThat(tree.preOrderTraversal(), contains(3, 2));
        assertThat(tree.inOrderTraversal(), contains(2, 3));
        assertThat(tree.postOrderTraversal(), contains(2, 3));
    }

    /**
     *    3
     *  2   4
     */
    @Test public void insert_Three() throws Exception {
        dataList = Arrays.asList(3, 2, 4);
        datas = dataList.toArray(new Integer[dataList.size()]);

        tree = BinarySearchTree.build(datas);

        assertThat(tree.preOrderTraversal(), contains(3, 2, 4));
        assertThat(tree.inOrderTraversal(), contains(2, 3, 4));
        assertThat(tree.postOrderTraversal(), contains(2, 4, 3));
    }

    /**
     *               6
     *          4        9
     *        3  5     8   10
     *
     */
    @Test public void insert_Complicated_Case() throws Exception {
        dataList = Arrays.asList(6, 4, 9, 3, 8, 5, 10);
        datas = dataList.toArray(new Integer[dataList.size()]);

        tree = BinarySearchTree.build(datas);

        assertThat(tree.preOrderTraversal(), contains(6, 4, 3, 5, 9, 8, 10));
        assertThat(tree.inOrderTraversal(), contains(3, 4, 5, 6, 8, 9, 10));

        assertThat(tree.postOrderTraversal(), contains(3, 5, 4, 8, 10, 9, 6));
    }

    @Test public void isEmpty() throws Exception {
        dataList = new ArrayList<>();
        datas = dataList.toArray(new Integer[dataList.size()]);

        tree = BinarySearchTree.build(datas);
        assertThat(tree.isEmpty(), is(true));

        dataList = Arrays.asList(6, 4, 9, 3, 8, 5, 10);
        datas = dataList.toArray(new Integer[dataList.size()]);
        BinarySearchTree tree = BinarySearchTree.build(datas);
        assertThat(tree.isEmpty(), is(false));
    }

    @Test public void getMinAndMax_When_Empty() throws Exception {
        dataList = new ArrayList<>();
        datas = dataList.toArray(new Integer[dataList.size()]);

        tree = BinarySearchTree.build(datas);

        assertThat(tree.getMin(), is(Integer.MIN_VALUE));
        assertThat(tree.getMax(), is(Integer.MAX_VALUE));
    }

    @Test public void getMinAndMax() throws Exception {
        dataList = Arrays.asList(6, 4, 9, 3, 8, 5, 10);
        datas = dataList.toArray(new Integer[dataList.size()]);

        tree = BinarySearchTree.build(datas);

        assertThat(tree.getMin(), is(3));
        assertThat(tree.getMax(), is(10));
    }

    @Test public void containsTest() throws Exception {
        dataList = Arrays.asList(6, 4, 9, 3, 8, 5, 10);
        datas = dataList.toArray(new Integer[dataList.size()]);

        tree = BinarySearchTree.build(datas);

        assertThat(tree.contains(5), is(true));
        assertThat(tree.contains(0), is(false));
        assertThat(tree.contains(null), is(false));
    }

    @Test public void remove_When_OnlyOneOrZero() throws Exception {
        dataList = Collections.singletonList(1);
        datas = dataList.toArray(new Integer[dataList.size()]);

        tree = BinarySearchTree.build(datas);

        assertThat(tree.remove(1), is(true));

        dataList = new ArrayList<>();
        datas = dataList.toArray(new Integer[dataList.size()]);

        tree = BinarySearchTree.build(datas);
        assertThat(tree.remove(1), is(false));
    }

    @Test public void remove_When_Two() throws Exception {
        dataList = Arrays.asList(1, 2);
        datas = dataList.toArray(new Integer[dataList.size()]);

        tree = BinarySearchTree.build(datas);

        assertThat(tree.remove(3), is(false));

        assertThat(tree.remove(2), is(true));
        assertThat(tree.preOrderTraversal(), contains(1));

        assertThat(tree.remove(3), is(false));
    }

    /**
     * 删除 2
     *              4      ->     4
     *            2   5              5
     */
    @Test public void remove_When_No_Child() throws Exception {
        dataList = Arrays.asList(4, 5, 2);
        datas = dataList.toArray(new Integer[dataList.size()]);

        tree = BinarySearchTree.build(datas);
        assertThat(tree.preOrderTraversal(), contains(4, 2, 5));

        assertThat(tree.remove(2), is(true));
        assertThat(tree.preOrderTraversal(), contains(4, 5));
        assertThat(tree.postOrderTraversal(), contains(5, 4));
    }

    /**
     * 删除 4
     *              4      ->     2
     *            2   5             5
     */
    @Test public void remove_When_has_Child() throws Exception {
        dataList = Arrays.asList(4, 5, 2);
        datas = dataList.toArray(new Integer[dataList.size()]);

        tree = BinarySearchTree.build(datas);
        assertThat(tree.preOrderTraversal(), contains(4, 2, 5));

        assertThat(tree.remove(4), is(true));
        assertThat(tree.preOrderTraversal(), contains(2, 5));
        assertThat(tree.inOrderTraversal(), contains(2, 5));
        assertThat(tree.postOrderTraversal(), contains(5, 2));
    }

    /**
     * 删除 7, 7 只有 左子树
     *
     *                       10                     10
     *                   7       30     ->      4      30
     *                 4       20  40         3   6  20  40
     *               3  6
     *
     */
    @Test public void remove_When_ComplicatedCases1() throws Exception {
        dataList = Arrays.asList(10, 7, 4, 3, 6, 30, 40, 20);
        datas = dataList.toArray(new Integer[dataList.size()]);

        tree = BinarySearchTree.build(datas);
        assertThat(tree.preOrderTraversal(), contains(10, 7, 4, 3, 6, 30, 20, 40));

        assertThat(tree.remove(0), is(false));
        assertThat(tree.remove(7), is(true));
        assertThat(tree.preOrderTraversal(), contains(10, 4, 3, 6, 30, 20, 40));
        assertThat(tree.inOrderTraversal(), contains(3, 4, 6, 10, 20, 30, 40));
        assertThat(tree.postOrderTraversal(), contains(3, 6, 4, 20, 40, 30, 10));
    }

    /**
     * 删除 7, 7 只有 右子树
     *
     *                       10                     10
     *                   7       30     ->      8      30
     *                    8    20  40             9  20  40
     *                     9
     *
     */
    @Test public void remove_When_ComplicatedCases2() throws Exception {
        dataList = Arrays.asList(10, 7, 30, 8, 9, 20, 40);
        datas = dataList.toArray(new Integer[dataList.size()]);

        tree = BinarySearchTree.build(datas);
        assertThat(tree.preOrderTraversal(), contains(10, 7, 8, 9, 30, 20, 40));

        assertThat(tree.remove(0), is(false));
        assertThat(tree.remove(7), is(true));
        assertThat(tree.preOrderTraversal(), contains(10, 8, 9, 30, 20, 40));
        assertThat(tree.inOrderTraversal(), contains(8, 9, 10, 20, 30, 40));
        assertThat(tree.postOrderTraversal(), contains(9, 8, 20, 40, 30, 10));
    }

    /**
     * 删除 7, 7 有 左右子树
     *
     *                           20                         20
     *                      7          40    ->       6          40
     *                   5    9      30  50        5    9      30  50
     *                 4  6  8  10               4    8  10
     *
     */
    @Test public void remove_When_ComplicatedCases3() throws Exception {
        dataList = Arrays.asList(20, 7, 40, 5, 9, 4, 6, 8, 10, 50, 30);
        datas = dataList.toArray(new Integer[dataList.size()]);

        tree = BinarySearchTree.build(datas);
        assertThat(tree.preOrderTraversal(), contains(20, 7, 5, 4, 6, 9, 8, 10, 40, 30, 50));

        assertThat(tree.remove(0), is(false));
        assertThat(tree.remove(7), is(true));
        assertThat(tree.preOrderTraversal(), contains(20, 6, 5, 4, 9, 8, 10, 40, 30, 50));
        assertThat(tree.inOrderTraversal(), contains(4, 5, 6, 8, 9, 10, 20, 30, 40, 50));
        assertThat(tree.postOrderTraversal(), contains(4, 5, 8, 10, 9, 6, 30, 50, 40, 20));
    }
}

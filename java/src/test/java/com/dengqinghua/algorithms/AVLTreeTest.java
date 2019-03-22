package com.dengqinghua.algorithms;

import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class AVLTreeTest {
    private List<Integer> dataList;
    private Integer[] datas;
    private AVLTree tree;
    private Method methodHeight;

    @BeforeClass public static void setMethodAccessible() throws Exception {
        methodHeight = AVLTree.class.getDeclaredMethod("height", AVLTree.AVLNode.class);
        methodHeight.setAccessible(true);
    }

    @Test public void heightTest_1() throws Exception {
        dataList = Arrays.asList(3, 2);
        datas = dataList.toArray(new Integer[dataList.size()]);

        tree = AVLTree.build(datas);

        assertThat(tree.preOrderTraversal(), is(contains(3, 2)));
        assertThat(methodHeight.invoke(AVLTree.AVLNode.class, tree.getRoot()), is(1));
    }

    @Test public void heightTest_0() throws Exception {
        dataList = Collections.singletonList(2);
        datas = dataList.toArray(new Integer[dataList.size()]);

        tree = AVLTree.build(datas);

        assertThat(tree.preOrderTraversal(), is(contains(2)));
    }

    @Test public void heightTest_3() throws Exception {
        dataList = Arrays.asList(3, 2);
        datas = dataList.toArray(new Integer[dataList.size()]);

        tree = AVLTree.build(datas);
        assertThat(tree.preOrderTraversal(), is(contains(3, 2)));
    }

    /**
     *      3          2
     *    2     ->  1     3
     *  1
     *
     */
    @Test public void balance_three() throws Exception {
        dataList = Arrays.asList(3, 2, 1);
        datas = dataList.toArray(new Integer[dataList.size()]);

        tree = AVLTree.build(datas);
        assertThat(tree.preOrderTraversal(), is(contains(2, 1, 3)));
        assertThat(tree.getRoot().height, is(1));
    }

    /**
     *              20                     10
     *        10        30    ->       5        20
     *     5     15                  1        15   30
     *  1
     *
     */
    @Test public void balance_case1() throws Exception {
        dataList = Arrays.asList(20, 10, 30, 5, 15, 1);
        datas = dataList.toArray(new Integer[dataList.size()]);

        tree = AVLTree.build(datas, false);
        assertThat(tree.postOrderTraversal(), is(contains(1, 5, 15, 10, 30, 20)));
        assertThat(tree.getRoot().height, is(3));

        tree = AVLTree.build(datas);
        assertThat(tree.postOrderTraversal(), is(contains(1, 5, 15, 30, 20, 10)));
        assertThat(tree.getRoot().height, is(2));
    }

    /**
     *    20                30
     *       30       ->  20  40
     *          40
     *
     */
    @Test public void balance_case2_1() throws Exception {
        dataList = Arrays.asList(20, 30, 40);
        datas = dataList.toArray(new Integer[dataList.size()]);

        tree = AVLTree.build(datas, false);
        assertThat(tree.postOrderTraversal(), is(contains(40, 30, 20)));
        assertThat(tree.getRoot().height, is(2));

        tree = AVLTree.build(datas);
        assertThat(tree.postOrderTraversal(), is(contains(20, 40, 30)));
        assertThat(tree.getRoot().height, is(1));
    }

    /**
     *            20                           30
     *       10        30          ->    20         40
     *               25   40           10  25          50
     *                       50
     *
     */
    @Test public void balance_case2() throws Exception {
        dataList = Arrays.asList(20, 10, 30, 25, 40, 50);
        datas = dataList.toArray(new Integer[dataList.size()]);

        tree = AVLTree.build(datas, false);
        assertThat(tree.postOrderTraversal(), is(contains(10, 25, 50, 40, 30, 20)));
        assertThat(tree.getRoot().height, is(3));

        tree = AVLTree.build(datas);
        assertThat(tree.postOrderTraversal(), is(contains(10, 25, 20, 50, 40, 30)));
        assertThat(tree.getRoot().height, is(2));
    }

    /**
     *              20                        15
     *     10             30    ->    10             20
     *   8    15                    8             18    30
     *          18
     *
     */
    @Test public void balance_case3() throws Exception {
        dataList = Arrays.asList(20, 10, 30, 8, 15, 18);
        datas = dataList.toArray(new Integer[dataList.size()]);

        tree = AVLTree.build(datas, false);
        assertThat(tree.postOrderTraversal(), is(contains(8, 18, 15, 10, 30, 20)));
        assertThat(tree.getRoot().height, is(3));

        tree = AVLTree.build(datas);
        assertThat(tree.postOrderTraversal(), is(contains(8, 10, 18, 30, 20, 15)));
        assertThat(tree.getRoot().height, is(2));
    }

    /**
     *            20                     25
     *     10            30     ->    20    30
     *                25    50      10 21     50
     *             21
     *
     */
    @Test public void balance_case4() throws Exception {
        dataList = Arrays.asList(20, 10, 30, 25, 50, 21);
        datas = dataList.toArray(new Integer[dataList.size()]);

        tree = AVLTree.build(datas, false);
        assertThat(tree.postOrderTraversal(), is(contains(10, 21, 25, 50, 30, 20)));

        tree = AVLTree.build(datas);
        assertThat(tree.postOrderTraversal(), is(contains(10, 21, 20, 50, 30, 25)));
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

        tree = AVLTree.build(datas);

        assertThat(tree.preOrderTraversal(), contains(6, 4, 3, 5, 9, 8, 10));
        assertThat(tree.inOrderTraversal(), contains(3, 4, 5, 6, 8, 9, 10));

        assertThat(tree.postOrderTraversal(), contains(3, 5, 4, 8, 10, 9, 6));
    }
}

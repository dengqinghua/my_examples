package com.dengqinghua.algorithms;

import java.util.*;

/**
 * AVL (Adelson-Velskii and Landis) Tree
 *
 * <pre>
 *   需要满足下面两个条件
 *
 *   Binary Tree
 *   任何一个节点的左右子树的高度差的绝对值 <= 1
 * </pre>
 *
 * 在平衡树中, 会使用一个值来存储树的高度, 在 insert 或者 remove 操作时,
 * 需要更新部分节点的高度
 */
public class AVLTree {
    private AVLNode root;

    public static class AVLNode {
        int data;
        AVLNode left, right;
        int height;

        public AVLNode(int data, AVLNode left, AVLNode right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    public AVLNode getRoot() {
        return root;
    }

    public static AVLTree build(Integer[] sourceDatas) {
        return build(sourceDatas, true);
    }

    public static AVLTree build(Integer[] sourceDatas, boolean useBalance) {
        Objects.requireNonNull(sourceDatas, "数据源不能为空");

        AVLTree tree = new AVLTree();
        AVLNode node = null;

        for (int data : sourceDatas) {
            node = tree.insert(data, node, useBalance);
        }

        tree.root = node;

        return tree;
    }

    /**
     * AVL树的插入
     *
     * <p>
     *     先进行插入, 插入完之后进行 balance
     * </p>
     *
     * @param data 插入的值
     * @param node 返回插入后的root节点
     * @param useBalance 是否采用平衡策略, 用于测试对比数据
     */
    public AVLNode insert(Integer data, AVLNode node, boolean useBalance) {
        if (Objects.isNull(node)) {
            return new AVLNode(data, null, null);
        } else {
            if (data > node.data) {
                node.right = insert(data, node.right, useBalance);
            } else {
                node.left = insert(data, node.left, useBalance);
            }

            if (useBalance) {
                node = balance(node);
            }

            setHeight(node);

            return node;
        }
    }

    private static int height(AVLNode node) {
        if (node == null) {
            return -1;
        } else {
            return Math.max(height(node.left), height(node.right)) + 1;
        }
    }

    private final Integer ALLOW_IMBALANCE = 1;

    /**
     * 对当前的节点做平衡处理
     *
     * <pre>
     *     一共有四种情况
     *     case 1. 左子树 的 左子树 插入 一个节点
     *          如下图 10 的 左子树 5, 插入 节点 1
     *
     *                       20                     10
     *                 10        30    ->       5        20
     *              5     15                  1        15   30
     *           1
     *
     *      以20为节点, 向右旋转20的左节点10
     *
     *     case 2. 右子树 的 右子树 插入 一个节点
     *      如下图 30 的 右子树 40, 插入 节点 50
     *
     *            20                           30
     *       10        30          ->    20         40
     *               25   40           10  25          50
     *                       50
     *
     *      以20为节点, 向左旋转20的右节点30
     *
     *     case 3. 左子树 的 右子树 插入 一个节点
     *      如下图 10 的 右子树 15, 插入 节点 11
     *
     *                    20                        15
     *           10             30    ->    10             20
     *         8    15                    8             18    30
     *                18
     *
     *       分两步: 先按照case2旋转10, 再按照case1旋转20
     *
     *     case 4. 右子树 的 左子树 插入 一个节点
     *      如下图 30 的 左子树 25, 插入 节点 21
     *
     *                 20                     25
     *          10            30     ->    20    30
     *                     25    50      10 21     50
     *                  21
     *
     *       分两步: 先按照case1旋转30, 再按照case2旋转20
     * </pre>
     *
     * @param node 输入的节点
     * @return 输出的节点
     */
    private AVLNode balance(AVLNode node) {
        if (Math.abs(height(node.left) - height(node.right)) > ALLOW_IMBALANCE) {
            if (height(node.left) > height(node.right)) {
                // case 1
                if (height(node.left.left) > height(node.left.right)) {
                    node = singleRotateLeftChildren(node);
                    // case 3
                } else {
                    node = DoubleRotateLeftChildren(node);
                }
            } else {
                // case 2
                if (height(node.right.right) > height(node.right.left)) {
                    node = singleRotateRightChildren(node);
                    // case 4
                } else {
                    node = DoubleRotateRightChildren(node);
                }
            }
        }

        return node;
    }

    /**
     * 设置节点的高度
     *
     * @param node 传入的节点
     */
    private void setHeight(AVLNode node) {
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    /**
     * 旋转左边的子树 (左-左 case1)
     *
     *                       20                     10
     *                 10        30    ->       5        20
     *              5     15                  1        15   30
     *           1
     *
     * @param node 节点, 如上左图根节点为20
     * @return 平衡后的节点, 如上右图根节点为10
     */
    private AVLNode singleRotateLeftChildren(AVLNode node) {
        AVLNode returnNode;

        returnNode = node.left;
        node.left  = returnNode.right;
        returnNode.right = node;

        return returnNode;
    }

    /**
     * 旋转左边的子树 (左-右 case3)
     *
     * <p>
     *     条件: height(node.left.left) < height(node.left.right)
     * </p>
     *
     * <pre>
     *     node: 20
     *
     *                    20                        15
     *           10             30    ->    10             20
     *         8    15                    8             18    30
     *                18
     *
     *  可以分为下面两个步骤:
     *
     *               20                                                 20
     *        10         30   以 10 为节点转右子树(case 2) ->        15        30
     *      8    15                                             10  18
     *             18                                         8
     *
     *                                             15
     *  以 20 为 节点 转左子树(case 1) ->      10          20
     *                                    8            18  30
     * </pre>
     *
     * @param node 节点, 如上左图根节点为20
     * @return 平衡后的节点, 如上右图根节点为10
     */
    private AVLNode DoubleRotateLeftChildren(AVLNode node) {
        node.left = singleRotateRightChildren(node.left);
        return singleRotateLeftChildren(node);
    }

    /**
     * 旋转右边的子树 (左-右 case4)
     *
     * <p>
     *     条件: height(node.right.left) > height(node.left.right)
     * </p>
     *
     * <pre>
     *     node: 20
     *
     *                 20                     25
     *          10            30     ->    20    30
     *                     25    50      10 21     50
     *                  21
     *
     *  可以分为下面两个步骤:
     *
     *        20                                              20
     *     10     30       以 25 为节点转左子树(case 1) ->   10    25
     *          25  50                                         21  30
     *        21                                                     50
     *
     *                                      25
     *   以 20 为节点转右子树(case 2) ->   20    30
     *                                10  21     50
     * </pre>
     *
     * @param node 节点, 如上左图根节点为20
     * @return 平衡后的节点, 如上右图根节点为25
     */
    private AVLNode DoubleRotateRightChildren(AVLNode node) {
        node.right = singleRotateLeftChildren(node.right);
        return singleRotateRightChildren(node);
    }

    /**
     * 旋转右边的子树 (右-右 case2)
     *
     *            20                           30
     *       10        30          ->    20         40
     *               25   40           10  25          50
     *                       50
     *
     * @param node 节点, 如上左图根节点为20
     * @return 平衡后的节点, 如上右图根节点为30
     */
    private AVLNode singleRotateRightChildren(AVLNode node) {
        AVLNode returnNode;

        returnNode = node.right;
        node.right = returnNode.left;
        returnNode.left = node;

        return returnNode;
    }

    /**
     * 先序遍历
     *
     * <pre>
     *     假如树为:
     *               6
     *          4        9
     *        3  5     8   10
     *
     *      则 返回值为 6 4 3 5 9 8 10
     *
     *      迭代式地访问 root -> left -> right
     * </pre>
     *
     * <pre>
     *     Base Case: node == null
     * </pre>
     *
     * @param node 访问的节点
     * @param list 最终存储的list, 会一直被改变
     */
    public void preOrderTraversal(AVLNode node, List<Integer> list) {
        if (Objects.nonNull(node)) {
            list.add(node.data);
            preOrderTraversal(node.left, list);
            preOrderTraversal(node.right, list);
        }
    }

    public List<Integer> preOrderTraversal() {
        List<Integer> result = new ArrayList<>();
        preOrderTraversal(this.root, result);

        return result;
    }

    /**
     * 中序遍历
     *
     * <pre>
     *     假如树为:
     *               6
     *          4        9
     *        3  5     8   10
     *
     *      则 返回值为 3 4 5 6 8 9 10
     *
     *      迭代式地访问 left -> root -> right
     *
     *      NOTE: 中序遍历得到的值为按顺序由小到大排列的数组
     * </pre>
     *
     * <pre>
     *     Base Case: node == null
     * </pre>
     *
     * @param node 访问的节点
     * @param list 最终存储的list, 会一直被改变
     */
    public void inOrderTraversal(AVLNode node, List<Integer> list) {
        if (Objects.nonNull(node)) {
            inOrderTraversal(node.left, list);
            list.add(node.data);
            inOrderTraversal(node.right, list);
        }
    }

    public List<Integer> inOrderTraversal() {
        List<Integer> result = new ArrayList<>();
        inOrderTraversal(this.root, result);

        return result;
    }

    /**
     * 后序遍历
     *
     * <pre>
     *     假如树为:
     *               6
     *          4        9
     *        3  5     8   10
     *
     *      则 返回值为 3 5 4 8 10 9 6
     *
     *      迭代式地访问 left -> right -> root
     * </pre>
     *
     * <pre>
     *     Base Case: node == null
     * </pre>
     *
     * @param node 访问的节点
     * @param list 最终存储的list, 会一直被改变
     */
    public void postOrderTraversal(AVLNode node, List<Integer> list) {
        if (Objects.nonNull(node)) {
            postOrderTraversal(node.left, list);
            postOrderTraversal(node.right, list);
            list.add(node.data);
        }
    }

    public List<Integer> postOrderTraversal() {
        List<Integer> result = new ArrayList<>();
        postOrderTraversal(this.root, result);

        return result;
    }
}

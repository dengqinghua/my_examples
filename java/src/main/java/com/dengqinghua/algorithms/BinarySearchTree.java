package com.dengqinghua.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 一些重要的方法包括
 *
 * <pre>
 *     .build              构建一棵树
 *     #insert             插入某个数字
 *     #remove             移除
 *     #contains           是否包含某个数字
 *     #preOrderTraversal  先序遍历
 *     #inOrderTraversal   中序遍历
 *     #postOrderTraversal 后序遍历
 * </pre>
 */
public class BinarySearchTree {
    private BinaryNode root;
    private boolean isRemoved;

    public BinarySearchTree() {
        this.root = null;
    }

    public BinaryNode getRoot() {
        return root;
    }

    public boolean contains(Integer data) {
        return Objects.nonNull(data) && contains(data, root);
    }

    /**
     * 递归的判断 当前 node 是否包含 data.
     *
     * <pre>
     *      Base Case:
     *          node.data == data, 返回 true
     *          node == null, 返回 false
     *
     *      Recrusive Case:
     *          1. data < node.data
     *              查看 node.left 的情况
     *          2. data > node.data
     *              查看 node.right 的情况
     * </pre>
     *
     * @param data 需要检查的数据
     * @param node 当前的节点
     * @return  如果能找到, 返回 true, 否则 false
     */
    private boolean contains(Integer data, BinaryNode node) {
        if (Objects.isNull(node)) {
            return false;
        } else {
            if (data.equals(node.data)) {
                return true;
            } else if (data < node.data) {
                return contains(data, node.left);
            } else {
                return contains(data, node.right);
            }
        }
    }

    /**
     * 构造一棵树
     *
     * <p>
     * 这个代码其实挺难理解的. 但是可认为 树 就是一个 BinaryNode, 它就是root
     * 构造树的过程, 就是改变root的过程
     * 所以 insert 方法, 其需要传入 root, 每次被 insert 操作时, 都改变了 root
     * </p>
     *
     * @param sourceDatas 如 [8, 1, 2, 4, 9, 10]
     * @return BinarySearchTree
     */
    public static BinarySearchTree build(Integer[] sourceDatas) {
        Objects.requireNonNull(sourceDatas, "数据源不能为空");

        BinarySearchTree tree = new BinarySearchTree();

        for (Integer data : sourceDatas) {
            tree.insert(data, tree.root);
        }

        return tree;
    }

    /**
     * 往 node 里面插入数据
     *
     * <pre>
     *     递归地进行比较
     *
     *     Base Case: data == null, 则返回一个没有 children 的节点
     *
     *     首先比较 data 和 node.data 的大小
     *
     *     1. 如果相等, 返回
     *
     *     2. 如果大于, 则要考虑往右边插入该数据
     *          a. 右子树为空, 创建新节点 (Base Case)
     *          b. 不为空,  往右子树 递归插入 insert(data, node.right)
     *
     *     3. 如果小于, 则要考虑往左边插入该数据
     *          a. 左子树为空, 创建新节点 (Base Case)
     *          b. 不为空,  往左子树 递归插入 insert(data, node.left)
     * </pre>
     *
     * @param data 插入的值
     * @param node 返回下一个需要遍历的节点
     */
    public void insert(Integer data, BinaryNode node) {
        if (Objects.isNull(node)) {
            this.root = new BinaryNode(data, null, null);
        } else {
            if (data > node.data) {
                if (Objects.isNull(node.right)) {
                    node.right = new BinaryNode(data, null, null);
                } else {
                    insert(data, node.right);
                }
            } else {
                if (Objects.isNull(node.left)) {
                    node.left = new BinaryNode(data, null, null);
                } else {
                    insert(data, node.left);
                }
            }
        }
    }

    /**
     * 移除一个元素
     *
     * @param data 被移除的数据
     * @return true 代表移除成功, false 代表失败
     */
    public boolean remove(Integer data) {
        isRemoved = true;

        this.root = remove(data, root);

        return isRemoved;
    }

    /**
     * <pre>
     *     1. 先对该元素进行查找, 如果没找到, 则设置为 isRemoved 为 false, 返回
     *     2. 如果找到了, 则进行移除
     *
     *     移除又分下面几种情况
     *
     *         a) 没有子节点, 如移除 2
     *
     *                  4      ->     4
     *                2   5              5
     *
     *             则直接设置 node1.left = null 即可
     *
     *         b) 有一个子节点, 如移除 7
     *              如果 7 有左(右)子树, 但是没有右(左)子树 则 左子树的 根 和 7 的 parent
     *         相连接
     *              case1, 只有左子树
     *
     *                           10                     10
     *                       7       30     ->      4      30
     *                     4       20  40         3   6  20  40
     *                   3  6
     *
     *                  node1.left = node2.left
     *
     *              case2, 只有右子树
     *
     *                           10                     10
     *                       7       30     ->      8      30
     *                        8    20  40             9  20  40
     *                         9
     *
     *                  node1.right = node2.right
     *
     *         c) 有两个子节点, 如移除 7 , 如果 7 有左子树, 而且有右子树,
     *          则
     *              1. 将 7 的 左子树 的 最大的节点(6) 替换 7 的位置,
     *              2. 然后将 最大节点(6) 从 7 的 左子树 中移除
     *
     *                                 20                         20
     *                            7          40    ->       6          40
     *                         5    9      30  50        5    9      30  50
     *                       4  6  8  10               4    8  10
     *
     *          FIXME: 该部分在经历多次删除之后, 会使得树极度不平衡
     *
     *       关于 c) 这一步, 也可以找 右子树的最小值来替换, 如 用 8 来替换 7 的位置
     *
     *                                 20                         20
     *                            7          40    ->       8          40
     *                         5    9      30  50        5    9      30  50
     *                       4  6  8  10               4  6    10
     *
     *        故删除某个节点之后, 得到的结果并不是唯一的
     * </pre>
     *
     * @param data 要移除的值
     * @param node 当前节点
     */
    public BinaryNode remove(Integer data,  BinaryNode node) {
        // 未找到该节点, 直接返回null
        if (Objects.isNull(node)) {
            isRemoved = false;
            return null;
        }

        if (data < node.data) {
            node.left = remove(data, node.left);

            return node;
        } else if (data > node.data) {
            node.right = remove(data, node.right);

            return node;
        } else {
            // node为叶子节点, 直接移除
            if (Objects.isNull(node.left) && Objects.isNull(node.right)) {
                return null;
            } else {
                // 左右子树均不为空, 直接找左子树的最大值, 设置当前节点的值, 并继续递归地移除最大值
                if (Objects.nonNull(node.left) && Objects.nonNull(node.right)) {
                    // 下面两行代码其实查询了两次, getMax 和 remove 都经历了遍历, 为了使代码可读
                    // 还是保持下面的写法.
                    // 获取左子树的最大节点的值
                    node.data = getMax(node.left);
                    // 删除左子树的最大节点
                    node.left = remove(node.data, node.left);

                    return node;
                } else {
                    // 左子树或者右子树为空, 这种情况直接将不为空的树嫁接过去即可
                    // NOTE: 理论上 这一行:
                    // if (Objects.isNull(node.left) && Objects.isNull(node.right))
                    // 也可以合并至下面这一行代码, 但是为了好理解, 还是做区分处理
                    return Objects.nonNull(node.left) ? node.left : node.right;
                }
            }
        }
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
    public void preOrderTraversal(BinaryNode node, List<Integer> list) {
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
    public void inOrderTraversal(BinaryNode node, List<Integer> list) {
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
    public void postOrderTraversal(BinaryNode node, List<Integer> list) {
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

    private class BinaryNode {
        Integer data;
        BinaryNode left, right;

        public BinaryNode(Integer data, BinaryNode left, BinaryNode right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    public boolean isEmpty() {
        return Objects.isNull(root);
    }

    public Integer getMin() {
        return isEmpty() ? Integer.MIN_VALUE : getMinNode().data;
    }

    public Integer getMax() {
        return isEmpty() ? Integer.MAX_VALUE : getMaxNode(root).data;
    }

    /**
     * 获取当前节点下的最大的值
     *
     * @param node 节点
     * @return 返回节点下的最大值
     */
    public Integer getMax(BinaryNode node) {
        return getMaxNode(node).data;
    }

    private BinaryNode getMinNode() {
        BinaryNode node = root;

        while (Objects.nonNull(node.left)) {
            node = node.left;
        }

        return node;
    }

    private BinaryNode getMaxNode() {
        return getMaxNode(root);
    }

    private BinaryNode getMaxNode(BinaryNode sourceNode) {
        BinaryNode node = sourceNode;

        while (Objects.nonNull(node.right)) {
            node = node.right;
        }

        return node;
    }
}

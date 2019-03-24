package com.dengqinghua.algorithms.practice;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

/**
 * 第一章 栈和队列
 */
class PartOne {
    /**
     * ﻿一个栈依次压入1、2、3、4、5, 那么从栈顶到栈底分别为5、4、3、2、1.
     * 将这个栈转置后，从栈顶到栈底为1、2、3、4、5，也就是实现栈中元素的逆序, 但是只能用递归函数来实现, 不能用其他数据结构
     */
    static class ReverseStack {
        static Stack<Integer> reverse(Stack<Integer> stack, Stack<Integer> result) {
            if (Objects.isNull(result)) {
                throw new RuntimeException("请初始化 stack");
            }

            // base condition
            if (stack.isEmpty()) {
                return result;
            }

            result.push(stack.pop());

            // recursive condition
            return reverse(stack, result);
        }

        /**
         * 这种是最优解, 但是不是特别好理解.
         *
         * <p>
         * 使用了两个递归
         * <pre>
         *     getAndRemoveLast, 获取最后一个元素, 并从栈中移除
         *     reverse, 递归地进行reverse, 并将最后一个元素 push 至栈中
         * </pre>
          * @param stack 传入的栈
         */
        static void reverse(Stack<Integer> stack) {
            if (stack.isEmpty()) {
                return;
            }

            int i = getAndRemoveLast(stack);
            reverse(stack);
            stack.push(i);
        }

        static int getAndRemoveLast(Stack<Integer> stack) {
            int data = stack.pop();

            if (stack.isEmpty()) {
                return data;
            } else {
                int data2 = getAndRemoveLast(stack);
                stack.push(data);

                return data2;
            }
        }
    }

    /**
     * 编写一个类，用两个栈实现队列，支持队列的基本操作（add、poll、peek）
     *
     * <pre>
     *    add:  队列添加一个元素
     *    poll: 从队列头部移除一个元素
     *    peek: 从队列头部获取队头元素
     *
     * <pre>
     * 思路:
     *
     *      stackPush(用于 add 操作)    stackPop(用于 poll 和 peek 操作)
     *      4
     *      3
     *      2
     *      1
     *
     *      pool操作如下:
     *
     *      第一步, 先将 stackPush 弹出到 stackPop, 得到
     *              1
     *              2
     *              3
     *              4
     *      第二步, stackPop pop 出 1
     *
     *
     *  如果是 push 的话, 先要判断 stackPop 里面是否有值, 有的话, 需要先 push 到 stackPush 中
     *
     * </pre>
     */
    static class TwoStacksQueue<T> {
        private Stack<T> stackPop, stackPush;

        TwoStacksQueue() {
            stackPop  = new Stack<>();
            stackPush = new Stack<>();
        }

        /**
         * @return 移除队列头部元素, 并返回
         */
        T poll() {
            if (stackPop.isEmpty()) {
                while (!stackPush.isEmpty()){
                    stackPop.push(stackPush.pop());
                }
            }

            return stackPop.pop();
        }

        /**
         * @return 队列头部元素
         */
        T peek() {
            if (stackPop.isEmpty()) {
                while (!stackPush.isEmpty()){
                    stackPop.push(stackPush.pop());
                }
            }

            return stackPop.top();
        }

        /**
         * 往队列添加一个参数
         *
         * <p>需要先看 stackPop 中是否还有数据, 如果有的话, 需要看 pop 到 stackPush 中, 再添加该元素
         * @param num 添加的元素
         */
        void add(T num) {
            while (!stackPop.isEmpty()){
                stackPush.push(stackPop.pop());
            }

            stackPush.push(num);
        }
    }

    /**
     * ﻿实现一个特殊的栈，在实现栈的基本功能的基础上，再实现返回栈中最小元素的操作
     *
     * <p>要求:
     *     <pre>
     *          1. pop、push、getMin操作的时间复杂度都是O(1)
     *          2. 设计的栈类型可以使用现成的栈结构。
     *     </pre>
     *
     * 思路如下:
     *     <pre>
     *          1. 一个栈记录原始数据, 另一个栈记录最小的数据
     *          2. 由于在 push 和 pop 的过程中, 都是需要动态更新 最小数据 的值, 为了实现 O(1), 所以单独使用了一个栈
     *     </pre>
     */
    static class OneStack {
        Stack<Integer> stack, minStack;

        OneStack() {
            stack = new Stack<>();
            // 这里需要用一个 minStack 来存储, 是因为 pop, push 的时候都需要方便得获取数据
            minStack = new Stack<>();
        }

        int getMin() {
            return minStack.top();
        }

        void push(Integer num) {
            stack.push(num);
            tryPushMinStack(num);
        }

        /**
         * tryPushMinStack 尝试将最小的数据 pop 出栈
         *
         * @param num 原始数据
         */
        private void tryPushMinStack(int num) {
            if (minStack.isEmpty()) {
                minStack.push(num);
                return;
            }

            if (getMin() >= num) {
                minStack.push(num);
            }
        }

        /**
         * tryPopMinStack 尝试将最小的数据 pop 出栈
         *
         * @param num 原始数据
         */
        private void tryPopMinStack(int num) {
            if (minStack.isEmpty()) {
                return;
            }

            if (num == getMin()) {
                minStack.pop();
            }
        }

        int pop() {
            int num = stack.pop();

            tryPopMinStack(num);

            return num;
        }
    }
}

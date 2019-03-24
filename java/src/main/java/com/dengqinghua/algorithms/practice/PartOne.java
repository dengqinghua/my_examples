package com.dengqinghua.algorithms.practice;

/**
 * 第一章 栈和队列
 */
class PartOne {
    /**
     * 题目: ﻿实现一个特殊的栈，在实现栈的基本功能的基础上，再实现返回栈中最小元素的操作
     *
     * 要求:
     *
     * <pre>
     * 1. pop、push、getMin操作的时间复杂度都是O(1)
     * 2. 设计的栈类型可以使用现成的栈结构。
     *
     * 时间复杂度 O(1)
     * 空间复杂度 O(n)
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

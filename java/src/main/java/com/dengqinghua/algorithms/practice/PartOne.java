package com.dengqinghua.algorithms.practice;

/**
 * 第一章 栈和队列
 */
class PartOne {
    static class OneStack {
        Stack stack, minStack;

        OneStack() {
            this.stack = new Stack();
            // 这里需要用一个 minStack 来存储, 是因为 pop, push 的时候都需要方便得获取数据
            this.minStack = new Stack();
        }

        int getMin() {
            return minStack.top();
        }

        void push(int num) {
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

            if (minStack.top() >= num) {
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

            int min = minStack.pop();
            if (num != min) {
                minStack.push(min);
            }
        }

        public int pop() {
            int num = stack.pop();

            tryPopMinStack(num);

            return num;
        }


        public int length() {
            return stack.length();
        }
    }
}

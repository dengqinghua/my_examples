package com.dengqinghua.algorithms.practice;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 第一章 栈和队列
 */
class PartOne {
    /**
     * <a href="https://en.wikipedia.org/wiki/Tower_of_Hanoi">汉诺塔</a>问题:
     *
     * <pre>
     *  有三根杆子A，B，C. A杆上有 N 个 (N>1) 穿孔圆盘，盘的尺寸由下到上依次变小。要求按下列规则将所有圆盘移至 C 杆：
     *      1. 每次只能移动一个圆盘；
     *      2. 大盘不能叠在小盘上面。
     * </pre>
     */
    static class HanoiTower {
        static void run() {
            move(3, "A", "C", "B");
        }

        static void move(int n, String from, String to, String mid) {
            if (n == 1) {
                System.out.printf("Move disk 1 from %s to %s\n", from, to);
                return;
            }

            move(n - 1, from, mid, to);
            System.out.printf("Move disk %d from %s to %s\n", n, from, to);
            move(n - 1, mid, to, from);
        }
    }

    /**
     * 一个栈中元素的类型为整型, 现在想将该栈 从顶到底按从大到小 的顺序排序, 只许申请一个栈.
     * 可以申请新的变量, 但不能申请额外的数据结构. 如何完成排序？
     *
     * <pre>
     * 思路:
     *      1. 申请的栈, 是按照 从顶到底按 `从小到大` 的顺序, 最后 pop 回原有的栈
     *      2. 将数据来回 pop push 保证有序性
     *      3. 记录 pop 的位置 i
     * </pre>
     */
    static class SortedStack {
        static void sort(Stack<Integer> stack) {
            Stack<Integer> tmpStack = new Stack<>();

            while(!stack.isEmpty()) {
                sortStack(tmpStack, stack, stack.pop());
            }

            while (!tmpStack.isEmpty()) {
                stack.push(tmpStack.pop());
            }
        }

        private static void sortStack(Stack<Integer> source, Stack<Integer> tmp, Integer data) {
            int i = 0, dataInStack = 0;
            while (!source.isEmpty()) {
                dataInStack = source.top();

                if (dataInStack < data) {
                    tmp.push(source.pop());
                    i++;
                } else {
                    break;
                }
            }

            source.push(data);

            for (int j = i; j > 0; j--) {
                source.push(tmp.pop());
            }
        }
    }

    /**
     * 实现一个猫狗队列, 要求
     *
     * <pre>
     *  1. add 将 cat 类或 dog 类的实例放入队列中；
     *  2. pollAll 将队列中所有的实例按照进队列的先后顺序依次弹出
     *  3. pollDog 将队列中 dog 类的实例按照进队列的先后顺序依次弹出
     *  4. pollCat 将队列中 cat 类的实例按照进队列的先后顺序依次弹出
     *  5. isEmpty 检查队列中是否还有 dog 或 cat 的实例
     *  6. isDogEmpty 检查队列中是否有 dog 类的实例
     *  7. isCatEmpty 检查队列中是否有 cat 类的实例。
     * </pre>
     *
     * <p>
     * 思路: 构造 DogQueue 和 CatQueue, 并添加 DCQueue, DogQueue 和 CatQueue 的 count, 作为时间戳
     */
    static class DCQueue {
        int count;
        Queue<PetEntry> dogQueue = new Queue<>();
        Queue<PetEntry> catQueue = new Queue<>();

        void add(Pet pet) {
            count++;

            if (Objects.equals(pet.getType(), "cat")) {
                catQueue.add(new PetEntry(pet, count));
            }

            if (Objects.equals(pet.getType(), "dog")) {
                dogQueue.add(new PetEntry(pet, count));
            }
        }

        boolean isCatEmpty() {
            return catQueue.isEmpty();
        }

        boolean isDogEmpty() {
            return dogQueue.isEmpty();
        }

        boolean isEmpty() {
            return catQueue.isEmpty() && dogQueue.isEmpty();
        }

        Dog pollDog() {
            PetEntry entry = dogQueue.poll();
            if (Objects.nonNull(entry)) {
                return (Dog) entry.pet;
            }

            return null;
        }

        Cat pollCat() {
            PetEntry entry = catQueue.poll();
            if (Objects.nonNull(entry)) {
                return (Cat) entry.pet;
            }

            return null;
        }

        Pet pollAll() {
            PetEntry dogEntry = dogQueue.peekFirst();
            PetEntry catEntry = catQueue.peekFirst();

            if (Objects.nonNull(dogEntry) && Objects.nonNull(catEntry)) {
                return dogEntry.count > catEntry.count ? dogEntry.pet : catEntry.pet;
            }

            if (Objects.nonNull(dogEntry)) {
                return dogEntry.pet;
            }

            if (Objects.nonNull(catEntry)) {
                return catEntry.pet;
            }

            return null;
        }

        static class PetEntry {
            Pet pet;
            int count;

            PetEntry(Pet pet, int count) {
                this.pet = pet;
                this.count = count;
            }
        }

        static class Pet {
            private String type;

            Pet(String type) {
                this.type = type;
            }

            String getType() {
                return type;
            }
        }

        static class Dog extends Pet {
            Dog() {
                super("dog");
            }
        }

        static class Cat extends Pet {
            Cat() {
                super("cat");
            }
        }
    }

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
    static class TwoStacksQueue<E> {
        private Stack<E> stackPop, stackPush;

        TwoStacksQueue() {
            stackPop  = new Stack<>();
            stackPush = new Stack<>();
        }

        /**
         * @return 移除队列头部元素, 并返回
         */
        E poll() {
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
        E peek() {
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
        void add(E num) {
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

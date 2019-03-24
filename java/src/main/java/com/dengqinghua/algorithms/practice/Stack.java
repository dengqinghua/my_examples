package com.dengqinghua.algorithms.practice;

import java.util.ArrayList;
import java.util.List;

/**
 * Stack 为栈, 先入后出.
 *
 * <p>使用 List 作为其存储结构
 */
class Stack {
    private List<Integer> storage;

    Stack() {
        storage = new ArrayList<>();
    }

    void push(int num) {
        storage.add(num);
    }

    /**
     * @return 返回栈顶的元素
     */
    int top() {
        if (storage.isEmpty()) {
            throw new RuntimeException("stack has no value");
        }

        return storage.get(topIndex());
    }

    private int topIndex() {
        return storage.size() - 1;
    }

    int pop() {
        if (storage.isEmpty()) {
            throw new RuntimeException("stack has no value");
        }

        return storage.remove(topIndex());
    }

    boolean isEmpty() {
        return storage.isEmpty();
    }

    int length() {
        return storage.size();
    }
}

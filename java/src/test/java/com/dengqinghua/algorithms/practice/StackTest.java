package com.dengqinghua.algorithms.practice;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class StackTest {
    @Test public void allTest() throws Exception {
        Stack stack = new Stack();
        assertThat(stack.length(), is(0));

        stack.push(10);
        stack.push(20);
        stack.push(30);

        assertThat(stack.length(), is(3));

        assertThat(stack.pop(), is(30));
        assertThat(stack.length(), is(2));
    }

    @Test(expected = RuntimeException.class)
    public void whenExceptionThrown() throws Exception {
        Stack stack = new Stack();
        // 如果直接 pop 会抛出异常
        stack.pop();
    }
}
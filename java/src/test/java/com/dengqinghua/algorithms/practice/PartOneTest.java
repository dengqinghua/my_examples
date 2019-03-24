package com.dengqinghua.algorithms.practice;

import org.junit.Test;

import static org.junit.Assert.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PartOneTest {
    @Test public void testGetMin() throws Exception {
        PartOne.OneStack stack = new PartOne.OneStack();

        stack.push(10);
        assertThat(stack.getMin(), is(10));

        stack.push(12);
        assertThat(stack.getMin(), is(10));

        stack.push(3);
        assertThat(stack.getMin(), is(3));

        stack.push(3);
        assertThat(stack.getMin(), is(3));

        stack.pop();
        assertThat(stack.getMin(), is(3));
    }
}
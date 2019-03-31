package com.dengqinghua.algorithms.practice;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class PartOneTest {
    @Test public void testHanoiTower() throws Exception {
        PartOne.HanoiTower.run();
    }

    @Test public void testSortedStack() throws Exception {
        Stack<Integer> stack = new Stack<>();

        stack.push(9);
        stack.push(3);
        stack.push(4);
        stack.push(2);
        stack.push(1);

        PartOne.SortedStack.sort(stack);
        assertThat(stack.pop(), is(9));
        assertThat(stack.pop(), is(4));
        assertThat(stack.pop(), is(3));
        assertThat(stack.pop(), is(2));
        assertThat(stack.pop(), is(1));
    }

    @Test public void testDCQueue() throws Exception {
        PartOne.DCQueue queue = new PartOne.DCQueue();
        PartOne.DCQueue.Pet pet;

        assertThat(queue.isEmpty(), is(true));
        assertThat(queue.isCatEmpty(), is(true));
        assertThat(queue.isDogEmpty(), is(true));

        assertThat(queue.pollAll(), is(nullValue()));
        assertThat(queue.pollCat(), is(nullValue()));
        assertThat(queue.pollDog(), is(nullValue()));

        queue.add(new PartOne.DCQueue.Dog());
        assertThat(queue.isEmpty(), is(false));
        assertThat(queue.isCatEmpty(), is(true));
        assertThat(queue.isDogEmpty(), is(false));

        queue.add(new PartOne.DCQueue.Cat());
        assertThat(queue.isEmpty(), is(false));
        assertThat(queue.isCatEmpty(), is(false));
        assertThat(queue.isDogEmpty(), is(false));

        pet = queue.pollAll();
        assertThat(pet.getType(), is("cat"));

        PartOne.DCQueue.Dog dog = queue.pollDog();
        assertThat(dog, is(notNullValue()));
        assertThat(queue.pollDog(), is(nullValue()));
    }

    @Test public void testReverseStack() throws Exception {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);

        Stack<Integer> res = PartOne.ReverseStack.reverse(stack, new Stack<>());
        assertThat(res.pop(), is(1));
        assertThat(res.pop(), is(2));
        assertThat(res.pop(), is(3));
        assertThat(res.pop(), is(4));

        Stack<Integer> stack2 = new Stack<>();
        stack2.push(1);
        stack2.push(2);
        stack2.push(3);
        stack2.push(4);

        PartOne.ReverseStack.reverse(stack2);
        assertThat(stack2.pop(), is(1));
        assertThat(stack2.pop(), is(2));
        assertThat(stack2.pop(), is(3));
        assertThat(stack2.pop(), is(4));
    }

    @Test public void testTwoStacksQueue() throws Exception {
        PartOne.TwoStacksQueue<Integer> queue = new PartOne.TwoStacksQueue<>();

        queue.add(1);
        assertThat(queue.peek(), is(1));

        queue.add(2);
        assertThat(queue.peek(), is(1));
        assertThat(queue.poll(), is(1));
        assertThat(queue.poll(), is(2));

        queue.add(3);
        queue.add(4);
        assertThat(queue.poll(), is(3));
    }

    @Test public void testOneStackGetMin() throws Exception {
        PartOne.OneStack stack = new PartOne.OneStack();

        stack.push(10);
        assertThat(stack.getMin(), is(10));

        stack.push(12);
        assertThat(stack.getMin(), is(10));

        stack.push(3);
        assertThat(stack.getMin(), is(3));

        stack.push(3);
        assertThat(stack.getMin(), is(3));

        assertThat(stack.pop(), is(3));
        assertThat(stack.getMin(), is(3));
    }
}
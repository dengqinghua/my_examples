package com.dengqinghua;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EverythingTest {
    private class InnerClass {
        private int i = 1;
    }

    private class InnerClassB {
        private int getI() {
            return (new InnerClass()).i;
        }
    }

    @Test public void testInnerClass() {
        InnerClass innerClass   = new InnerClass();
        InnerClassB innerClassB = new InnerClassB();

        // 在这里, 虽然InnerClass中 i 是 private 的, 但是在此类中 i 是可以被获取到的
        // 所以我觉得, 如果 InnerClass 已经被设置为 private 了, 她里面的变量其实都可以不做任何申明
        assertThat(innerClass.i, is(1));
        assertThat(innerClassB.getI(), is(1));
    }
}

package com.dengqinghua.algorithms;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DynamicProgrammingTest {
    Map<String , Integer> sourceGains = new HashMap<>();
    Map<String , Integer> sourceCosts = new HashMap<>();
    DynamicProgramming.Cell[][] grid;
    int limit, unit;

    @Test public void run_When_Only_One_Line() throws Exception {
        sourceCosts.put("送快递", 2);
        sourceGains.put("送快递", 10);
        limit = 1;
        unit  = 1;

        grid = DynamicProgramming.run(limit, unit, sourceGains, sourceCosts);
        assertThat(grid, arrayWithSize(1));
        assertThat(grid[0][0].gain, is(0));

        limit = 2;
        unit  = 2;

        grid = DynamicProgramming.run(limit, unit, sourceGains, sourceCosts);
        assertThat(grid, arrayWithSize(1));
        assertThat(grid[0][0].gain, is(10));

        limit = 3;
        unit  = 1;

        grid = DynamicProgramming.run(limit, unit, sourceGains, sourceCosts);
        assertThat(grid, arrayWithSize(1));
        assertThat(grid[0][2].gain, is(10));
        assertThat(grid[0][2].items, contains("送快递"));

        limit = 4;
        unit  = 2;

        grid = DynamicProgramming.run(limit, unit, sourceGains, sourceCosts);
        assertThat(grid, arrayWithSize(1));
        assertThat(grid[0][1].gain, is(20));

        limit = 6;

        grid = DynamicProgramming.run(limit, unit, sourceGains, sourceCosts);
        assertThat(grid, arrayWithSize(1));
        assertThat(grid[0][2].gain, is(30));
    }

    @Test public void run_When_Two_Line() throws Exception {
        sourceCosts.put("送快递", 2);
        sourceGains.put("送快递", 10);

        sourceCosts.put("做家政", 4);
        sourceGains.put("做家政", 20);
        limit = 2;
        unit  = 2;

        grid = DynamicProgramming.run(limit, unit, sourceGains, sourceCosts);
        assertThat(grid, arrayWithSize(2));
        assertThat(grid[0], arrayWithSize(1));
        assertThat(grid[1][0].gain, is(10));

        limit = 4;

        grid = DynamicProgramming.run(limit, unit, sourceGains, sourceCosts);
        assertThat(grid, arrayWithSize(2));
        assertThat(grid[0], arrayWithSize(2));
        assertThat(grid[1][1].gain, is(20));

        limit = 6;

        grid = DynamicProgramming.run(limit, unit, sourceGains, sourceCosts);
        assertThat(grid, arrayWithSize(2));
        assertThat(grid[0], arrayWithSize(3));
        assertThat(grid[1][2].gain, is(30));

        limit = 8;

        grid = DynamicProgramming.run(limit, unit, sourceGains, sourceCosts);
        assertThat(grid, arrayWithSize(2));
        assertThat(grid[0], arrayWithSize(4));
        assertThat(grid[1][3].gain, is(40));
    }

    @Test public void run_When_Multi_Line() throws Exception {
        unit  = 1;

        sourceCosts.put("送快递", 2);
        sourceGains.put("送快递", 21);

        sourceCosts.put("做家政", 3);
        sourceGains.put("做家政", 40);

        sourceCosts.put("做家教", 5);
        sourceGains.put("做家教", 45);

        limit = 1;
        grid = DynamicProgramming.run(limit, unit, sourceGains, sourceCosts);
        assertThat(grid[2][0].gain, is(0));

        limit = 2;
        grid = DynamicProgramming.run(limit, unit, sourceGains, sourceCosts);
        assertThat(grid[2][1].gain, is(21));

        limit = 3;
        grid = DynamicProgramming.run(limit, unit, sourceGains, sourceCosts);
        assertThat(grid[2][2].gain, is(40));
        assertThat(grid[2][2].items, contains("做家政"));

        limit = 4;
        grid = DynamicProgramming.run(limit, unit, sourceGains, sourceCosts);
        assertThat(grid[2][3].gain, is(42));
        assertThat(grid[2][3].items, contains("送快递", "送快递"));

        limit = 5;
        grid = DynamicProgramming.run(limit, unit, sourceGains, sourceCosts);
        assertThat(grid[2][4].gain, is(61));
        assertThat(grid[2][4].items, contains("做家政", "送快递"));

        limit = 6;
        grid = DynamicProgramming.run(limit, unit, sourceGains, sourceCosts);
        assertThat(grid[2][5].gain, is(80));
        assertThat(grid[2][5].items, contains("做家政", "做家政"));

        limit = 7;
        grid = DynamicProgramming.run(limit, unit, sourceGains, sourceCosts);
        assertThat(grid[2][6].gain, is(82));
        assertThat(grid[2][6].items, contains("做家政", "送快递", "送快递"));
    }

    @Test public void run_When_Multi_Line_Complicated() throws Exception {
        unit  = 1;
        sourceCosts.put("送快递", 1);
        sourceGains.put("送快递", 10);

        sourceCosts.put("做家政", 2);
        sourceGains.put("做家政", 20);

        sourceCosts.put("做家教", 4);
        sourceGains.put("做家教", 45);

        sourceCosts.put("送外卖", 2);
        sourceGains.put("送外卖", 30);

        sourceCosts.put("写程序", 3);
        sourceGains.put("写程序", 50);

        limit = 8;

        grid = DynamicProgramming.run(limit, unit, sourceGains, sourceCosts);
        assertThat(grid[4][7].gain, is(130));
        assertThat(grid[4][7].items, contains("写程序", "写程序", "送外卖"));
    }

    @Test public void doubleDimensionArray() throws Exception {
        // |-----------|
        // | 1 | 2 | 3 |
        // |-----------|
        // | 4 | 5 | 6 |
        // |-----------|
        //  两行三列
        int[][] array = new int[2][3];

        // 获取行数
        assertThat(array.length, is(2));
        assertThat(array[0].length, is(3));
        assertThat(array[1].length, is(3));
    }
}
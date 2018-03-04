package com.dengqinghua.algorithms;

import java.util.*;

public class DynamicProgramming {
    /**
     * 动态规划算法 解决 NP-complete 问题途径之一
     *
     * <pre>
     *     问题描述:
     *
     *     先举一个特定的例子: 假设 小明 能支配的时间为 8 小时, 他在这 8 小时可以做下面的事情:
     *
     *     1. 送快递 耗时: 1小时, 收益: 10元
     *     2. 做家政 耗时: 2小时, 收益: 20元
     *     3. 做家教 耗时: 4小时, 收益: 45元
     *     4. 送外卖 耗时: 2小时, 收益: 30元
     *     5. 写程序 耗时: 3小时, 收益: 50元
     *
     *     在同一时间只能做一件事情, 两件事情之间没有相互关联关系
     *
     *     问题: 在 有限的 8 小时内, 小明 要选择 5件事的 哪几件, 能使得他 总收益 最大?
     *
     *     如果算出所有的情况, 则需要
     *      1. 计算 { 1, 2, 3, 4, 5 } 的子集, 如 { 1 }, { (1, 2), { 3 } } 等, 一共有 2⁵ 个集合
     *      2. 分别计算每个子集的 收益
     *
     *     假设一个集合里面有 n 个元素, 则 这个集合的子集数为 2ⁿ, 故上述算法的复杂度为
     *
     *          O(2ⁿ)
     *
     *      这样的算法复杂度是无法接受的. 故会考虑 Greedy 算法或者 Dynamic 算法等. 这里给出的方式为 Dynamic (动态规划)算法
     * </pre>
     *
     * 动态规划思路
     *
     * <pre>
     *     1. 构建一个 grid
     *     2. 将问题变为 平均分隔成 子问题
     *     3. 保存每一个子问题的结果
     *     4. 计算最终的结果
     *
     *     送快递 耗时: 1小时, 收益: 10元
     *     做家政 耗时: 2小时, 收益: 20元
     *     做家教 耗时: 4小时, 收益: 40元
     *     送外卖 耗时: 2小时, 收益: 30元
     *     写程序 耗时: 3小时, 收益: 50元
     *
     *                 1    2    3    4    5    6    7    8
     *             |-----------------------------------------|
     *    送快递    | 10  | 10  | 10 | 10 | 10 | 10 | 10 | 10 |
     *             |-----------------------------------------|
     *    做家政    | 10  | 20  |    |    |    |    |    |    |
     *             |-----------------------------------------|
     *    做家教    |     |     |    |    |    |    |    |    |
     *             |-----------------------------------------|
     *    送外卖    |     |     |    |    |    |    |    |    |
     *             |-----------------------------------------|
     *    写程序    |     |     |    |    |    |    |    |    |
     *             |-----------------------------------------|
     *
     *   1) 横向为时间, 即将 小明 拥有的所有时间划为为了 8 份, 为什么是 8 份? 是因为在
     *  上述 5 件事中, 最小的单位数为 1 小时, 如果多了一件事:
     *
     *     煮饭 耗时: 0.5 小时, 收益 10 元
     *
     *     那么最小单位变为 0.5 小时. 如果不是 0.5 小时呢? 是 0.7 小时? 则无法用该算法
     *
     *   2) 格子中记录的是 在对应的时间内, 小明做对应的时间可以获得的最大的收益
     *
     *   如: (送快递, 1)  对应的值为 10
     *   如: (做家政, 1)  对应的值为 10 (因为1小时无法完成, 所以最大的收益为10, 选择送快递)
     *
     *   3) 当计算到  (做家政, 3) 时, 由于做家政只需要2小时, 则还剩下1小时 算法为:
     *
     *      max { ((做家政, 2) + (做家政, 3 - 2))), (送快递, 3)  }
     *      <=> ((做家政, 2) + (做家政, 1))
     *      <=> ((做家政, 2) + (送快递, 1))
     *
     *    这样能看到, 其实前一个格子的计算结果可以在下一次能用到, 这样将原来的 NP-complete 的问题, 变成
     *    了有限的问题
     *
     *   4) 一直计算至 (写程序, 8), 这个格子里面的值, 则为最终的最佳收益
     * </pre>
     *
     * <pre>
     *      算法复杂度
     *
     *      假设一共有 N 件事, 将整个横轴分为 M 份, 则 算法复杂度为:
     *
     *              O(M * N)
     *
     *      上述的例子中 N = 5
     *
     *      如果全部遍历子集, 为: 2⁵ = 32 次
     *      如果采用动态规划, 为: 5 * 8 = 40 次
     *
     *      如果 N 再增长, 如 N = 10, 则
     *
     *      如果全部遍历子集, 为: 2¹⁰ = 1024 次
     *      如果采用动态规划, 为: 10 * 8 = 80 次
     *
     *      动态规划会随着 列数变多而变得更慢, 如
     *
     *      unit = 10min, 则 M = (8 * 60 / 10) = 48
     *      unit = 1min,  则 M = (8 * 60 / 1)  = 480
     * </pre>
     *
     * @param limit 限制的时间, 上面的例子中的 8 小时
     * @param unit  最小单位时间
     * @param sourceGains 数据源的 获益部分
     * @param sourceCosts 数据源的 时间消费部分
     * @return 返回整个 动态规划 的 cell的二维数组,
     *      其中最后一个 cell 为最优解, 可以通过 cell.gain 和 cell.items 得到 收益 和 详情
     */
    public static Cell[][] run(int limit,
                               int unit,
                               Map<String, Integer> sourceGains,
                               Map<String, Integer> sourceCosts) {
        Cell[][] grid = initGrid(sourceGains, limit, unit);

        // 初始化第一行的值
        // 因为一行的名字都是一样的, 所以就提上来了
        int FirstLineCost = sourceCosts.get(grid[0][0].name) / unit - 1; // 单元化之后的 cost
        for (int i = 0; i < grid[0].length; i++) {
            Cell cell = grid[0][i];

            if (i < FirstLineCost) {
                cell.gain = 0;
            } else if (i == FirstLineCost) {
                cell.gain = sourceGains.get(cell.name);
                cell.items.add(cell.name);
            } else {
                Cell remainedGainCell = grid[0][i - FirstLineCost - 1];
                cell.gain = sourceGains.get(cell.name) + remainedGainCell.gain;
                cell.items.add(cell.name);
                cell.items.addAll(remainedGainCell.items);
            }
        }

        // 计算第一行之后的值
        for (int x = 1; x < grid.length; x++) {
            // 因为一行的名字都是一样的, 所以就提上来了
            int cost = sourceCosts.get(grid[x][0].name) / unit - 1; // 单元化之后的 cost

            for (int y = 0; y < grid[0].length; y++) {
                Cell cell = grid[x][y];
                Cell previousCell = grid[x - 1][y];

                if (y < cost) {
                    // 当时的时间 比 要花费的时间 少, 取 同一列 上一层 的值
                    cell.gain = previousCell.gain;
                    cell.items = previousCell.items;
                } else if (y == cost) {
                    int currentGain = sourceGains.get(cell.name);

                    // 比较当前的值, 和 同一列 上一层 的值 哪一个更大
                    if (currentGain > previousCell.gain) {
                        cell.gain = currentGain;
                        cell.items.add(cell.name);
                    } else {
                        cell.gain = previousCell.gain;
                        cell.items = previousCell.items;
                    }
                } else {
                    // 当时的时间 比 要花费的时间 多, 需要看下能否更多
                    Cell remainedGainCell = grid[x][y - cost - 1];
                    int totalCellGain = sourceGains.get(cell.name) + remainedGainCell.gain;

                    if (totalCellGain > previousCell.gain) {
                        cell.gain = totalCellGain;
                        cell.items.add(cell.name);
                        cell.items.addAll(remainedGainCell.items);
                    } else {
                        cell.gain = previousCell.gain;
                        cell.items = previousCell.items;
                    }
                }
            }
        }

        return grid;
    }

    /**
     * 初始化一个空的 grid
     *
     * @param sourceGains 源数据
     * @param unit        最小单元, 如上述的例子为 1.
     *                    这个值其实是可以用 <a href="https://stackoverflow.com/a/40531215/8186609">gcd(最大公约数) 算法</a>
     *                    算出来的, 但是为了简化起见, 让从参数传入
     * @return 已初始化后的 cell 的二维数组
     */
    private static Cell[][] initGrid(Map<String, Integer> sourceGains, int limit, int unit) {
        int xLength = sourceGains.size();
        int yLength = limit / unit;

        Cell[][] grid = new Cell[xLength][yLength];

        String[] names = sourceGains.keySet().toArray(new String[sourceGains.size()]);

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                grid[x][y] = new Cell(names[x]);
            }
        }

        return grid;
    }

    /**
     * 根据传入的参数得到这些数的 最大公约数 https://en.wikipedia.org/wiki/Euclidean_algorithm
     *
     * 参考自: https://stackoverflow.com/a/40531215/8186609
     *
     * @return 最大公约数
     */
    @Deprecated public static int gcd(Integer... numbers) {
        return Arrays.stream(numbers).reduce(0, DynamicProgramming::gcd);
    }

    @Deprecated private static int gcd(int x, int y) {
        return (y == 0) ? x : gcd(y, x % y);
    }

    static class Cell {
        int gain;
        String name;
        List<String> items = new ArrayList<>();

        public Cell(String name) {
            this.name = name;
        }
    }
}

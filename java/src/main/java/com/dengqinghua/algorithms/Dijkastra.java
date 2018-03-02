package com.dengqinghua.algorithms;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Dijkastra {
    /**
     * Dijkastra(狄克斯特拉) 算法, 计算 从 graph 的 一端到另一端的 最短路径
     *
     * <pre>
     *     图如下所示:
     *
     *      start  __₂₀ node2
     *        \₁₀    /⁵     \₂₅
     *          node1  __₁₀  end
     *
     *      假设现在需要计算 start 到 end 节点的最短路径
     *
     *    数据结构设计:
     *
     *    1. 构建 Graph
     *
     *      HashMap
     *          start: [[node1, 10], [node2, 20]],
     *          node1: [[node2, 5], [end, 15]]
     *          node2: [[end, 25]]
     *
     *       其中 [node1, 10] 使用 对象 Edge 表示
     *
     *    2. 从 start 节点开始, 遍历 start 对应的数据, 再递归地进行调用, 计算 start 到各个点的距离
     *
     *         HashMap
     *            node1: 10,
     *            node2: min(20, 15) = 15
     *            end:   min(25, 40) = 25
     *
     *         [e1, e2]
     *
     *         e1.to.edges [e2, e3]
     *         e2.to.edge  [e3, e4]
     *
     *    3. 需要将最短的 路劲 存储在 一个数据结构中. 为了方便实现, 使用一个 parentNodeMap,
     *    给每一个 节点存储一个 parent, 作为 该节点为最小路径时的 上一个节点
     *
     *    比如上面的例子
     *
     *      start  __₂₀ node2
     *        \₁₀    /⁵     \₂₅
     *          node1  __₁₀  end
     *
     *    有两条路劲到 node2,
     *          start -> node2;
     *          start -> node1 -> node2;
     *
     *    计算完之后发现 start -> node1 -> node2 的权重总和更小, 所以认为 node2 的 parentNode 为 node1
     * </pre>
     *
     * <p>
     * 注意: edge中的 weight 属性需要 >= 0
     *
     * @param graph 初始化好的一张图
     */
    public static void run(Graph graph,
                           Map<Node, Integer> nodeCostMap,
                           Map<Node, Node> parentNodeMap) {
        // 最终的图成为了一个点, 就结束迭代了
        if (graph.start == graph.end) {
            return;
        }

        List<Edge> edges = graph.nodeMap.get(graph.start);
        Objects.requireNonNull(edges);

        edges.forEach(edge -> {
            Integer toNodeWeight   = nodeCostMap.get(edge.to);
            Integer fromNodeWeight = nodeCostMap.getOrDefault(edge.from, 0);

            Integer nowToWeight = fromNodeWeight + edge.weight;
            if (toNodeWeight == null || nowToWeight < toNodeWeight) {
                nodeCostMap.put(edge.to, nowToWeight);
                parentNodeMap.put(edge.to, edge.from);
            }

            graph.start = edge.to;
            run(graph, nodeCostMap, parentNodeMap);
        });
    }
}

class Graph {
    Map<Node, List<Edge>> nodeMap;
    Node start, end;

    Graph(Map<Node, List<Edge>> NodeMap, Node start, Node end) {
        this.nodeMap = NodeMap;
        this.start   = start;
        this.end     = end;
    }
}

class Node {
    String name;
    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Edge {
    Node from, to;
    int weight;

    Edge(Node fromNode, Node toNode, int edgeWeight) {
        this.from   = fromNode;
        this.to     = toNode;
        this.weight = edgeWeight;

        if (weight < 0) {
            throw new RuntimeException("Weight should be bigger than zero");
        }
    }
}

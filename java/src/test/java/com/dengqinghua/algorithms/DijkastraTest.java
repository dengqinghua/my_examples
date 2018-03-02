package com.dengqinghua.algorithms;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DijkastraTest {
    static Graph graph;
    static Map<Node, List<Edge>> nodeMap = new HashMap<>();
    static Node start, node1, node2, end;
    Map<Node, Integer> nodeCostMap = new HashMap<>();
    Map<Node, Node> parentNodeMap = new HashMap<>();

    // start  __₂₀ node2
    //   \₁₀    /⁵     \₂₅
    //     node1  __₁₀  end
    @BeforeClass static public void initNodeAndEdges() {
        start = new Node("start");
        node1 = new Node("node1");
        node2 = new Node("node2");
        end = new Node("end");

        Edge edgeStartToNode1 = new Edge(start, node1, 10),
                edgeStartToNode2 = new Edge(start, node2, 20),
                edgeNode1ToNode2 = new Edge(node1, node2, 5),
                edgeNode1ToEnd = new Edge(node1, end, 10),
                edgeNode2ToEnd = new Edge(node2, end, 25);

        nodeMap = new HashMap<>();
        nodeMap.put(start, Arrays.asList(edgeStartToNode1, edgeStartToNode2));
        nodeMap.put(node1, Arrays.asList(edgeNode1ToNode2, edgeNode1ToEnd));
        nodeMap.put(node2, Collections.singletonList(edgeNode2ToEnd));
        nodeMap.put(end, new ArrayList<>());
    }

    @Test public void run_When_Graph_Only_Has_One_Node() throws Exception {
        graph = new Graph(nodeMap, end, end);
        Dijkastra.run(graph, nodeCostMap, parentNodeMap);

        assertThat(nodeCostMap.keySet(), hasSize(0));
        assertThat(nodeCostMap.get(end), nullValue());
    }

    //  node2
    //       \₂₅
    //        end
    @Test public void run_When_Graph_Only_Has_Two_Nodes() throws Exception {
        graph = new Graph(nodeMap, node2, end);
        Dijkastra.run(graph, nodeCostMap, parentNodeMap);

        assertThat(nodeCostMap.keySet(), hasSize(1));
        assertThat(nodeCostMap.get(end), is(25));
        assertThat(parentNodeMap.get(end), is(node2));
    }

    //         node2
    //       /⁵     \₂₅
    //  node1  __₁₀  end
    @Test public void run_When_Graph_Multi() throws Exception {
        graph = new Graph(nodeMap, node1, end);
        Dijkastra.run(graph, nodeCostMap, parentNodeMap);

        assertThat(nodeCostMap.get(end), is(10));
        assertThat(combileShortestRoute(end, parentNodeMap), is("node1->end"));
    }

    //  start  __₂₀ node2
    //    \₁₀    /⁵     \₂₅
    //      node1  __₁₀  end
    @Test public void run_When_Graph_All() throws Exception {
        graph = new Graph(nodeMap, start, end);

        Dijkastra.run(graph, nodeCostMap, parentNodeMap);
        assertThat(nodeCostMap.get(end), is(20));

        assertThat(combileShortestRoute(end, parentNodeMap), is("start->node1->end"));
    }

    private String combileShortestRoute(Node end, Map<Node, Node> parentNodeMap) {
        List<Node> nodes = new LinkedList<>();
        Node node = end;

        while (Objects.nonNull(node)) {
            nodes.add(node);
            node = parentNodeMap.get(node);
        }

        Collections.reverse(nodes);
        return nodes.stream().map(Node::getName).collect(Collectors.joining("->"));
    }
}
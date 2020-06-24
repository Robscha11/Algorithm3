import java.time.chrono.MinguoEra;
import java.util.ArrayList;
import java.util.HashMap;

// YOU CAN USE ONLY THE ArrayLists AND HashMaps FOR THIS ASSIGNMENT!

/**
 * graphTemplate
 */
public class Main {
    ArrayList<Node> nodes;
    MinHeapTemplate minPriorityQueue;
    boolean isDirected;

    Main(){
        nodes = new ArrayList<Node>();
        minPriorityQueue = new MinHeapTemplate();
        /*parent = null;
        distance = Integer.MAX_VALUE;*/
        isDirected = true;
    }

    public void addNode(String source, String destination, int weight) {
        if (nodes.isEmpty()) {
            Node addSourceNode = new Node(source);
            nodes.add(addSourceNode);
            Node addDestNode = new Node(destination);
            nodes.add(addDestNode);
            addSourceNode.adjacentNodes.put(addDestNode, weight);
            addDestNode.adjacentNodes.put(addSourceNode, weight);
        } else {
            for (Node findDestination: nodes) {
                System.out.println("for - findDestination" + source);
                if (findDestination.label == destination) {
                    for (Node findSource: nodes) {
                        System.out.println("for - findSource" + source);
                        if (findSource.label == source) {
                            System.out.println("foundDesti foundSource" + source);
                            findDestination.adjacentNodes.put(findSource, weight);
                            findSource.adjacentNodes.put(findDestination, weight);
                        } else {
                            System.out.println("foundDesti noSource" + source);
                            Node addSourceNode = new Node(source);
                            addSourceNode.adjacentNodes.put(findDestination, weight);
                            findDestination.adjacentNodes.put(addSourceNode, weight);
                            nodes.add(addSourceNode);
                            System.out.println(nodes.size());
                        }

                    }
					nodes.add(addSourceNode);
                    System.out.println("hallo");
                }else{
                    System.out.println("done nothing" +source);

                }
            }
        }
    }
    public static void main(String[] args) {
        Main first = new Main();

        first.addNode("0", "1", 5);
        first.addNode("5", "1", 4);

        System.out.println(first.nodes.size());
        //first.addNode("0", "7", 4);
		//cd


    }

    /*TODO: implement additional constructors
    TODO: implement method for adding a node
    TODO: implement method for removing a node
    TODO: implement Prim
    Represent a directed weighted graph and implement the Prim algorithm for com-
    puting the Minimum Spanning Tree of a graph. (30%)
    TODO: implement Bellman-Ford
    Represent a directed weighted graph and implement the Bellman-Ford algorithm
    for computing the Single-Source Shortest Path of a graph. (30%)
    TODO: implement printGraph function that generates a file written using the dot format
    Implement a printGraph function that prints a Graph using the dot format nota-
    tion, highlighting also the edges that are part of a Minimum Spanning Tree or a
    Shortest Path. (10%)

    write main using both algorithms with input values*/

}

/**
 * Node
 */
class Node {
    String label;
    HashMap<Node,Integer> adjacentNodes; // Pair connected node + weight
    Node parent;
    int distance; //aka key

/*    Node(){
        label = "";
        adjacentNodes = new HashMap<Node,Integer>();
    }*/

    Node(String name){
        label = name;
        adjacentNodes = new HashMap<Node, Integer>();
    }

    // TODO: implement additional constructors
    // TODO: implement method for adding a connection
    // TODO: implement method for removing a connection
    // TODO: implement methods for manipulating the parent and distance

}

/**
 * MinHeapTemplate
 */
class MinHeapTemplate{
    MinHeapNode root;

    MinHeapTemplate(){
        root = null;
    }
    // TODO: implement method for restructuring the min-priority Queue
    // TODO: implement method for extracting the smaller element from the min-priority Queue
}

/**
 * MinHeapNode
 */
class MinHeapNode{
    Node node;
    MinHeapNode parent;
    MinHeapNode left;
    MinHeapNode right;

    MinHeapNode(){
        node = null;
        parent = null;
        left = null;
        right = null;
    }

    // TODO: implement additional constructors
}






import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

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
        Node parent = null;
        int distance = Integer.MAX_VALUE;
        isDirected = true;
    }

    //gets String and writes into .gv file
    public void saveQV (String qv){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("tree.gv"));
            writer.write("digraph{\n");
            writer.write(qv);
            writer.write("}");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bellmannFordMethod () {
        nodes.get(0).distance = 0;
        for (int i = 0; i < nodes.size(); i++) {
            System.out.println(nodes.get(i).adjacentNodes.keySet());
            for(Node key : nodes.get(i).adjacentNodes.keySet()) {
                System.out.println(nodes.get(i).adjacentNodes.get(key));
                if (key.distance > nodes.get(i).distance + nodes.get(i).adjacentNodes.get(key)) {
                    System.out.println(nodes.get(i).adjacentNodes.get(key));
                    key.distance = nodes.get(i).distance + nodes.get(i).adjacentNodes.get(key);
                }
            }
        }
        System.out.println();
        for (Node eachNode: nodes) {
            System.out.println("Distance of " + nodes.get(0).label + " to " + eachNode.label + ": " + eachNode.distance);
        }
    }


    public int primMethod (Node startNode) {
        if (!nodes.contains(startNode)) {
            return 0;
        }
        int sum = 0;
        int value = Integer.MAX_VALUE;
        Node savedNode = null;
        Node nextNode;
        ArrayList<Node> visited = new ArrayList<>();
        visited.add(startNode);
        while (visited.size() < nodes.size()) {
            for (Node focusNode: visited) {
                System.out.println("Label: " + focusNode.label);
                if (!focusNode.adjacentNodes.isEmpty() && value > Collections.min(focusNode.adjacentNodes.values())) {
                    if (!visited.contains(getKeyByValue(focusNode.adjacentNodes, Collections.min(focusNode.adjacentNodes.values())))) {
                        value = Collections.min(focusNode.adjacentNodes.values());
                        savedNode = focusNode;
                    }
                }
            }
            nextNode = getKeyByValue(savedNode.adjacentNodes, value);
            sum += value;
            visited.add(nextNode);
            savedNode.removeConnection(nextNode);
            value = Integer.MAX_VALUE;
        }
        return sum;
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }


    public void addNode(String source, String destination, int weight) {
        if (nodes.isEmpty()) {
            Node addSourceNode = new Node(source);
            nodes.add(addSourceNode);
            Node addDestNode = new Node(destination);
            nodes.add(addDestNode);
            addSourceNode.adjacentNodes.put(addDestNode, weight);
            addDestNode.adjacentNodes.put(addSourceNode, weight);
            //already nodes in list
        } else {
                if (containingNodes(nodes, source).label == source && containingNodes(nodes, destination).label == destination) {
                    Node savedNode = containingNodes(nodes, destination);
                    containingNodes(nodes, source).adjacentNodes.put(savedNode, weight);
                    savedNode.adjacentNodes.put(containingNodes(nodes, source), weight);
                } else if (containingNodes(nodes, source).label == source && containingNodes2(nodes, destination)) {
                    //do nothing
                } else if (containingNodes2(nodes, source) && containingNodes(nodes, destination).label == destination){
                    Node savedNode = containingNodes(nodes, destination);
                    Node addSourceNode = new Node(source);
                    nodes.add(addSourceNode);
                    addSourceNode.adjacentNodes.put(savedNode, weight);
                    savedNode.adjacentNodes.put(addSourceNode, weight);
                } else {
                    //do nothing
                }
            }
        }

    public Node containingNodes(ArrayList<Node> allNodes, String search) {
        for (int i = 0; i<allNodes.size(); i++) {
            if (allNodes.get(i).label == search) {
                return allNodes.get(i);
            }
        }
        return allNodes.get(0);
    }

    public Boolean containingNodes2(ArrayList<Node> allNodes, String search) {
        for (int i = 0; i<allNodes.size(); i++) {
            if (allNodes.get(i).label == search) {
                return false;
            }
        }
        return true;
    }

    public void removeNode (String toRemove) {
        int ourLook = 0;
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).label == toRemove) {
                ourLook = i;
            }

        }
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).adjacentNodes.remove(nodes.get(ourLook));
            if (nodes.get(i).adjacentNodes.isEmpty()) {
                nodes.remove(i);
            }
        }
        nodes.remove(ourLook);
    }

    public void printGraph () {

    }

    public static void main(String[] args) {
        Main first = new Main();

        first.addNode("0", "1", 2);
        first.addNode("5", "1", 4);
        first.addNode("9", "3", 8);
        first.addNode("8", "1", 6);
        first.addNode("6", "0", 14);
        for (int i = 0; i < first.nodes.size(); i++) {
            System.out.println(first.nodes.get(i).label);
        }
        System.out.println("_______________________");
        //first.removeNode("1");
        System.out.println("_______________________");
        for (int i = 0; i < first.nodes.size(); i++) {
            System.out.println(first.nodes.get(i).label);
        }

        System.out.println(first.primMethod(first.nodes.get(0)));

        first.bellmannFordMethod();


        //System.out.println(first.nodes.get(1).adjacentNodes.keySet());




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

    public void removeConnection (Node connectedNode) {
        connectedNode.adjacentNodes.remove(this);
        this.adjacentNodes.remove(connectedNode);
    }

    public void addingConnection (Node connectedNode, int distance) {
        connectedNode.adjacentNodes.put(this, this.distance);
        this.adjacentNodes.put(connectedNode, distance);
    }



    //implement additional constructors
    //implement method for adding a connection
    //implement method for removing a connection
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






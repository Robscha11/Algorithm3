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
    ArrayList<String> allElements = new ArrayList<>();

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
            BufferedWriter writer = new BufferedWriter(new FileWriter("graph.gv"));
            writer.write("digraph{\n");
            writer.write(qv);
            writer.write("}");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //searching for lowest connection between firstNode and every other node
    public void bellmannFordMethod () {
        //setting table, all distances to infinity and firstNode to 0
        for (Node test: nodes) {
            test.distance = Integer.MAX_VALUE;
        }
        nodes.get(0).distance = 0;
        //looping and deciding which way to go
        for (int i = 0; i < nodes.size(); i++) {
            //System.out.println(nodes.get(i).adjacentNodes.keySet());
            for(Node key : nodes.get(i).adjacentNodes.keySet()) {
                if (key.distance > nodes.get(i).distance + nodes.get(i).adjacentNodes.get(key)) {
                    key.distance = nodes.get(i).distance + nodes.get(i).adjacentNodes.get(key);
                }
            }
        }
        for (Node eachNode: nodes) {
            System.out.println("Distance of " + nodes.get(0).label + " to " + eachNode.label + ": " + eachNode.distance);
        }
    }

    //getting sum of lowest connection between every node in tree
    public int primMethod (Node startNode) {
        if (!nodes.contains(startNode)) {
            return 0;
        }
        int sum = 0;
        int value = Integer.MAX_VALUE;
        Node savedNode = null;
        Node nextNode;
        //list with all members visited
        ArrayList<Node> visited = new ArrayList<>();
        visited.add(startNode);
        //deciding which node in hashmap has the lowest weight and adding them to visited list + adding to sum
        while (visited.size() < nodes.size()) {
            for (Node focusNode: visited) {
                if (!focusNode.adjacentNodes.isEmpty() && value > Collections.min(focusNode.adjacentNodes.values())) {
                    if (!visited.contains(getKeyByValue(focusNode.adjacentNodes, Collections.min(focusNode.adjacentNodes.values())))) {
                        value = Collections.min(focusNode.adjacentNodes.values());
                        savedNode = focusNode;
                    }
                }
            }
            nextNode = getKeyByValue(savedNode.adjacentNodes, value);
            allElements.add(savedNode.label + "->" + nextNode.label + "[color=\"0.650 0.700 0.700\"]");
            //idea adding each highlighted line at this point only unsure how
            //savedNode -> nextNode [color="0.650 0.700 0.700"]
            sum += value;
            visited.add(nextNode);
            savedNode.removeConnection(nextNode);
            value = Integer.MAX_VALUE;
        }
        return sum;
    }

    //getting key by value and returning the node
    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    //adding nodes with existing destination to our tree structure
    public void addNode(String source, String destination, int weight) {
        if (nodes.isEmpty()) {
            Node addSourceNode = new Node(source);
            allElements.add(source + "->" + destination + "[label =" + weight + "]");
            nodes.add(addSourceNode);
            Node addDestNode = new Node(destination);
            allElements.add(destination + "->" + source + "[label =" + weight + "]");
            nodes.add(addDestNode);
            addSourceNode.adjacentNodes.put(addDestNode, weight);
            addDestNode.adjacentNodes.put(addSourceNode, weight);
            //already nodes in list
        } else {
                if (containingNodes(nodes, source).label == source && containingNodes(nodes, destination).label == destination) {
                    Node savedNode = containingNodes(nodes, destination);
                    containingNodes(nodes, source).adjacentNodes.put(savedNode, weight);
                    savedNode.adjacentNodes.put(containingNodes(nodes, source), weight);
                    allElements.add(destination + "->" + source + "[label =" + weight + "]");
                    allElements.add(source + "->" + destination + "[label =" + weight + "]");
                } else if (containingNodes(nodes, source).label == source && containingNodes2(nodes, destination)) {
                    //do nothing
                } else if (containingNodes2(nodes, source) && containingNodes(nodes, destination).label == destination){
                    Node savedNode = containingNodes(nodes, destination);
                    Node addSourceNode = new Node(source);
                    allElements.add(source + "->" + destination + "[label =" + weight + "]");
                    allElements.add(destination + "->" + source + "[label =" + weight + "]");
                    nodes.add(addSourceNode);
                    addSourceNode.adjacentNodes.put(savedNode, weight);
                    savedNode.adjacentNodes.put(addSourceNode, weight);
                } else {
                    //do nothing
                }
            }
        }

    //searches if node with search label exists and return them
    public Node containingNodes(ArrayList<Node> allNodes, String search) {
        for (int i = 0; i<allNodes.size(); i++) {
            if (allNodes.get(i).label == search) {
                return allNodes.get(i);
            }
        }
        return allNodes.get(0);
    }

    //searching if node with search label exists and return true or false for existence
    public Boolean containingNodes2(ArrayList<Node> allNodes, String search) {
        for (int i = 0; i<allNodes.size(); i++) {
            if (allNodes.get(i).label == search) {
                return false;
            }
        }
        return true;
    }

    //removing a node from our tree
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
                allElements.remove(i);
            }
        }
        nodes.remove(ourLook);
        allElements.remove(ourLook);
    }

    public static void main(String[] args) {
        Main first = new Main();

        first.addNode("0", "1", 2);
        first.addNode("5", "1", 4);
        first.addNode("9", "3", 8);
        first.addNode("8", "1", 6);
        first.addNode("6", "0", 14);

        System.out.println("__________Bellmann__________");
        first.bellmannFordMethod();
        System.out.println("____________________________\n\n");

        System.out.println("____________Prim____________");
        System.out.println("Sum minimum spanning tree: " + first.primMethod(first.nodes.get(0)));
        System.out.println("____________________________");

        String allElements = "";
        for (String createGraph: first.allElements) {
            allElements += createGraph + "\n";
        }

        first.saveQV(allElements);
    }
}

/**
 * Node
 */
class Node {
    String label;
    HashMap<Node,Integer> adjacentNodes; // Pair connected node + weight
    Node parent;
    int distance; //aka key

    Node(String name){
        label = name;
        adjacentNodes = new HashMap<Node, Integer>();
    }

    //removing a connection between two nodes
    public void removeConnection (Node connectedNode) {
        connectedNode.adjacentNodes.remove(this);
        this.adjacentNodes.remove(connectedNode);
    }

    //adding a connection between two nodes
    public void addingConnection (Node connectedNode, int distance) {
        connectedNode.adjacentNodes.put(this, this.distance);
        this.adjacentNodes.put(connectedNode, distance);
    }
}

/**
 * MinHeapTemplate
 */
class MinHeapTemplate{
    MinHeapNode root;

    MinHeapTemplate(){
        root = null;
    }
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
}






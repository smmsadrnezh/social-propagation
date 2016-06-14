import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException {
        String nodesFile = "/home/smmsadrnezh/IdeaProjects/social-propagation/src/Nodes.txt";
        String edgesFile = "/home/smmsadrnezh/IdeaProjects/social-propagation/src/Edges.txt";
        String seedsFile = "/home/smmsadrnezh/IdeaProjects/social-propagation/src/Seed.txt";
        Graph graph = new Graph(nodesFile, edgesFile, seedsFile);
        graph.run();
    }
}

class Graph {
    public static ArrayList<Node> nodesArray = new ArrayList();
    public static ArrayList<Edge> edgesArray = new ArrayList();
    public static HashMap<String, ArrayList<Node>> Nodesclass = new HashMap();

    public Graph(String nodesFile, String edgesFile, String seedsFile) throws IOException {

        // Read Input Nodes File
        BufferedReader br = new BufferedReader(new FileReader(nodesFile));
        String line;
        while ((line = br.readLine()) != null) {

            // Create New Node
            String[] splitLine = line.split("\\s+");
            Double[] rate = {Double.parseDouble(splitLine[2]), Double.parseDouble(splitLine[3]), Double.parseDouble(splitLine[4]), Double.parseDouble(splitLine[5])};
            Node node = new Node(Integer.parseInt(splitLine[0]), splitLine[1], rate);

            // Add Node to Nodes List
            nodesArray.add(node);

            // Add Node to Node Class List
            ArrayList nodesClassList;
            if (Nodesclass.get(splitLine[1]) != null) {
                nodesClassList = Nodesclass.get(splitLine[1]);
            } else {
                nodesClassList = new ArrayList();
            }
            nodesClassList.add(node);
            Nodesclass.put(splitLine[1], nodesClassList);
        }

        // Read Input Edges File
        br = new BufferedReader(new FileReader(edgesFile));
        while ((line = br.readLine()) != null) {
            String[] splitLine = line.split("\\s+");
            Edge edge = new Edge(Integer.parseInt(splitLine[0]), Integer.parseInt(splitLine[1]), Double.parseDouble(splitLine[2]));
            edgesArray.add(edge);
        }

        // Read Input Seeds File
        br = new BufferedReader(new FileReader(seedsFile));
        while ((line = br.readLine()) != null) {
            String[] splitLine = line.split("\\s+");
            Integer type = Integer.parseInt(splitLine[0]);

            // Find Seed Nodes and Set newsTypes
            for (Node node : nodesArray) {
                for (int i = 1; i < splitLine.length; i++) {
                    if (node.nodeIndex == Integer.parseInt(splitLine[i])) {
                        node.newsType = type;
                    }
                }
            }

        }
    }

    void run(){

        // Scan number of turns
        Integer turns = new Scanner(System.in).nextInt();

    }
}

class Node {
    Integer nodeIndex;
    String nodeClass;
    Double[] rate = new Double[3];
    Integer newsType = 0;

    public Node(Integer nodeIndex, String nodeClass, Double[] rate) {
        this.nodeIndex = nodeIndex;
        this.nodeClass = nodeClass;
        this.rate = rate;
    }

}

class Edge {
    Integer srcNode;
    Integer destNode;
    Double weight;

    public Edge(Integer srcIndex, Integer destIndex, Double weight) {
        this.srcNode = srcIndex;
        this.destNode = destIndex;
        this.weight = weight;
    }
}
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException {

        // Build Graph From Files
        String path = "/home/smmsadrnezh/IdeaProjects/social-propagation/src/";
        String nodesFile = path + "Nodes.txt";
        String edgesFile = path + "Edges.txt";
        String seedsFile = path + "Seed.txt";
        String outputFile = path + "Out.txt";
        Graph graph = new Graph(nodesFile, edgesFile, seedsFile);

        // Run The Process
        graph.run(outputFile);
    }
}

class Graph {
    public static ArrayList<Node> nodesArray = new ArrayList();
    public static ArrayList<Node> nodesWithNewsArray = new ArrayList();
    public static ArrayList<Edge> edgesArray = new ArrayList();
    public static HashMap<String, ArrayList<Node>> Nodesclass = new HashMap();
    public static Integer all = 0;
    public static Integer[] typeCount = new Integer[3];

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
                        nodesWithNewsArray.add(node);
                    }
                }
            }

        }
    }

    void run(String outputFile) throws FileNotFoundException {

        // Scan number of turns
        Integer turns = new Scanner(System.in).nextInt();

        // Open Output File
        PrintWriter writer = new PrintWriter(outputFile);

        // Set Seed Values
        all = nodesWithNewsArray.size();
        String classes = "";
        HashMap<String, Integer> NodesClassCount = new HashMap();
        for (String e : Nodesclass.keySet()) {
            classes += " " + e;
            NodesClassCount.put(e, Nodesclass.get(e).size());
        }
        for (int i = 0; i < typeCount.length; i++) {
            typeCount[i] = 0;
        }
        for (Node node : nodesWithNewsArray) {
            if (node.newsType != 0)
                typeCount[node.newsType - 1]++;
        }

        // Write Header
        writer.println("Turn All Type1 Type2 Type3 Type4" + classes);

        // Compute Output in Each Turn
        for (int i = 0; i < turns; i++) {

            // Find Nodes Connected to nodesWithNewsArray
            for (Node node : nodesWithNewsArray) {
                for (Edge edge : edgesArray) {
                    if (edge.destNode == node.nodeIndex) {
                        for (Node srcNode : nodesArray) {
                            if (edge.srcNode == srcNode.nodeIndex) {
                                // Check The Probability to see the news
                                if(Math.random() < edge.weight){
                                    // Check If It's not visited before
                                    if(!srcNode.visitedTypes.get(node.newsType-1)){
                                        // Check The Probability to share the news
                                        if(Math.random() < node.rate[node.newsType]){
                                            nodesWithNewsArray.add(srcNode);
                                            srcNode.newsType = node.newsType;
                                            // +1 all related counters
                                            all++;
                                            typeCount[node.newsType-1]++;
                                            NodesClassCount.put(node.nodeClass, NodesClassCount.get(node.nodeClass) + 1);
                                        } else {
                                            srcNode.visitedTypes.put(node.newsType,Boolean.TRUE);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // make and print line
            String nodeClassCountValues = "";
            String typeCountValues = "";
            for (Integer e : NodesClassCount.values()) {
                nodeClassCountValues += " " + e;
            }
            for (Integer e : typeCount) {
                typeCountValues += " " + e;
            }
            writer.println(i + " " + all + typeCountValues + nodeClassCountValues);
        }

        writer.close();

    }
}

class Node {
    Integer nodeIndex;
    String nodeClass;
    Double[] rate = new Double[3];
    Integer newsType = 0;
    HashMap<Integer, Boolean> visitedTypes = new HashMap();

    public Node(Integer nodeIndex, String nodeClass, Double[] rate) {
        this.nodeIndex = nodeIndex;
        this.nodeClass = nodeClass;
        this.rate = rate;
        for (int i = 0; i < 3; i++) {
            visitedTypes.put(i,Boolean.FALSE);
        }
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
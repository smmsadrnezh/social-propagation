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

    void run(String outputFile) throws FileNotFoundException {

        // Scan number of turns
        Integer turns = new Scanner(System.in).nextInt();

        // Open Output File
        PrintWriter writer = new PrintWriter(outputFile);

        // Write Header
        writer.println("Trun All Type1 Type2 Type3 Type4 " + Nodesclass.keySet().toString());

        // Write Seed Values
        Integer all = 0, type1 = 0, type2 = 0, type3 = 0, type4 = 0;
        HashMap<String, Integer> NodesClassCount = new HashMap();
        Object[] classes = Nodesclass.keySet().toArray();
        for (int i = 0; i < classes.length; i++) {
            System.out.println(classes);
        }

        // Compute Output in Each Turn
        for (int i = 0; i < turns; i++) {

            writer.println(i + " " + all + " " + type1 + " " + type2 + " " + type3 + " " + type4);
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
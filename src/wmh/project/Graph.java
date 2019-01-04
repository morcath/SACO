package wmh.project;

import java.util.ArrayList;
import java.util.Iterator;

public class Graph {

    ArrayList<Node> nodes;
    ArrayList<ArrayList<Double>> graph;
    private SudokuLevel level;

    public Graph(SudokuLevel _level)
    {
        level = _level;
    }

    private double initializeWeight()
    {
        switch (level)
        {
            case EASY:
                return 0;   //todo: what initial weight?
            case MEDIUM:
                return 0;   //todo: what initial weight?
            case HARD:
                return 0;   //todo: what initial weight?
        }
        System.exit(-1);
        return -1;
    }

    private void addNode(Node newNode)
    {
        Iterator<ArrayList<Double>> iter = graph.iterator();
        ArrayList<Double> tmp;
        ArrayList<Double> newNodeWeights = new ArrayList<Double>();

        while(iter.hasNext())
        {
            tmp = iter.next();
            tmp.add(initializeWeight());
        }

        for (int i=0; i<graph.size(); ++i)
            newNodeWeights.add(initializeWeight());

        nodes.add(newNode);
    }

    private int getNodeIndex(Node node)
    {
        Iterator<Node> iter = nodes.iterator();
        int result;

        while(iter.hasNext())
        {
            //todo: searching node and return index if find
        }

        addNode(node);
        result = nodes.size();
        return result;
    }

    EdgeDirection areNeighbours(Node first, Node second)
    {
        //todo: searching if nodes are neighbours
        return EdgeDirection.None;
    }

}

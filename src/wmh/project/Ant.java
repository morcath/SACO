package wmh.project;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Ant {

    private ArrayList<Path> paths = new ArrayList<Path>();
    
    private ArrayList<Integer> generateSuccessors(int currentNodeID, Graph graph, SudokuBoard sudokuBoard)
    {
        ArrayList<Integer> successors = new ArrayList<Integer>();
        Node currentNode = graph.getNode(currentNodeID);
        ArrayList<Move> currentNodeMoves = currentNode.getMoves();
        Move predecessorMove = null;
        if(currentNodeMoves.size() > 0)
        {
        	predecessorMove = currentNodeMoves.get(currentNodeMoves.size() - 1);
        }        
        
        ArrayList<Pair<Integer, Integer>> empty = sudokuBoard.getEmptyFields();
        int emptyNum = empty.size();
        for(int i = 0; i < emptyNum; i++)
        {
        	for(int j = i + 1; j < emptyNum; j++)
        	{
        		Pair<Integer, Integer> from = empty.get(i);
        		Pair<Integer, Integer> to = empty.get(j);
        		Move m = new Move(from.getKey(), from.getValue(), to.getKey(), to.getValue());
        		if(!m.equalTo(predecessorMove)) //zabraniamy wykonania zamiany tych pól, co poprzednio, ¿eby nie krêciæ siê w kó³ko
				{
					Node succ = new Node(currentNodeMoves, sudokuBoard);
        			succ.addMove(m, sudokuBoard);
        			int successorIndex = graph.addNode(succ, sudokuBoard);

        			if(currentNodeID != successorIndex)
        				successors.add(successorIndex);
				}
        	}
        }
        if(successors.size() == 0)
        {
        	//TODO: dodaæ poprzednik tego stanu, ale to siê chyba nie zdarzy
        }
        return successors;
    }

    private ArrayList<Pair<Integer, Double>> calculateProbabilities(ArrayList<Integer> successors, int sourceIndex, double alpha, Graph graph)
    {
        int succNum = successors.size();
    	ArrayList<Pair<Integer, Double>> probabilities = new ArrayList<Pair<Integer, Double>>(succNum);
    	double sum = 0;
    	
    	for(int i = 0; i < succNum; i++)
    	{
    		int targetIndex = successors.get(i);
    		double weight = graph.getWeight(sourceIndex, targetIndex);
    		sum += Math.pow(weight, alpha);
    	}
    	
    	for(int i = 0; i < succNum; i++)
    	{
    		int targetIndex = successors.get(i);
    		double weight = graph.getWeight(sourceIndex, targetIndex);
    		double probability = Math.pow(weight, alpha) / sum;
    		Pair<Integer, Double> p = new Pair<Integer, Double>(targetIndex, probability);

			//if(Double.isNaN(p.getValue()))
				//System.out.println("?");

    		probabilities.add(p);
    	}

    	return probabilities;
    }

    //zwraca indeks wybranego nastêpnika w grafie
    private int selectSuccessor(ArrayList<Pair<Integer, Double>> probabilities)
    {
        Random gen = new Random();
    	double random = gen.nextDouble();
    	
        Iterator<Pair<Integer, Double>> iter = probabilities.iterator();
        ArrayList<Pair<Integer, Double>> probabilitiesSum = new ArrayList<Pair<Integer, Double>>(probabilities.size());

        double sum = 0;
        while(iter.hasNext())
		{
			Pair<Integer, Double> p = iter.next();
			sum += p.getValue();
			Pair<Integer, Double> tmp = new Pair<Integer, Double>(p.getKey(), sum);
			probabilitiesSum.add(tmp);

		}
		Iterator<Pair<Integer, Double>> iterSum = probabilitiesSum.iterator();

        while(iterSum.hasNext())
        {
        	Pair<Integer, Double> p = iterSum.next();
        	if(random <= p.getValue())
        	{
        		return p.getKey();
        	}
        }
        return -1;
    }

    //wybiera nastêpnika na podst. prawdopodobieñstwa i dodaje wêz³y do œcie¿ki, dopóki nie znajdzie wêz³a docelowego
    public Path buildPath(double alpha, Graph graph, SudokuBoard sudokuBoard)
    {
        int sourceIndex = 0;
    	Path path = new Path(sourceIndex);
    	boolean isSolved = sudokuBoard.isSolved(graph.getNode(sourceIndex));    	
    	ArrayList<Integer> successors;
    	ArrayList<Pair<Integer, Double>> probabilities;
    	int targetIndex;

    	while(!isSolved)
    	{
			successors = generateSuccessors(sourceIndex, graph, sudokuBoard);
			probabilities = calculateProbabilities(successors, sourceIndex, alpha, graph);
			targetIndex = selectSuccessor(probabilities);
			
			Node successor = graph.getNode(targetIndex);
			path.addNode(targetIndex);
			
			sourceIndex = targetIndex;
			isSolved = sudokuBoard.isSolved(successor);
		}
    	
    	path.removeLoops();
    	paths.add(path);
        return path;
    }

    public void updatePheromone(Graph graph, int iteration)
    {
        Path currentPath = paths.get(iteration);
    	int nodesInPath = currentPath.getNodesNumber();
    	double pheromoneToAdd = 1 / currentPath.evaluationFunction();
    	//System.out.println(pheromoneToAdd);
    	for(int sourceIndex = 0; sourceIndex < nodesInPath - 1; sourceIndex++)
    	{
    		int targetIndex = sourceIndex + 1; //indeks, pod którym w currentPath znajduje siê indeks wêz³a w grafie
    		int source = currentPath.getNode(sourceIndex);
    		int target = currentPath.getNode(targetIndex); //indeksy wêz³ów w grafie
    		graph.updatePheromone(source, target, pheromoneToAdd);
    	}
    }
    
    public ArrayList<Path> getPaths()
    {
    	return paths;
    }
    
    public Path getPathFromIteration(int iteration)
    {
    	return paths.get(iteration);
    }
}

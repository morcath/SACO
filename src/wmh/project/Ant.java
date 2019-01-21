package wmh.project;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Ant {

    private ArrayList<Path> paths = new ArrayList<Path>();
    
//mo�e nie dawa� w nast�pnikach mo�liwo�ci przej�cia do poprzednika
    ArrayList<Integer> generateSuccessors(int currentNodeID, Graph graph, SudokuBoard sudokuBoard)
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
        		if(!m.equalTo(predecessorMove)) //zabraniamy wykonania zamiany tych p�l, co poprzednio, �eby nie kr�ci� si� w k�ko
				{
					Node succ = new Node(currentNodeMoves, sudokuBoard);
        			succ.addMove(m, sudokuBoard);
        			int successorIndex = graph.addNode(succ);

//        			if(currentNode.moves.size() + 1 != graph.getNode(successorIndex).moves.size() && currentNodeID != successorIndex)
//        				System.out.println("??");

        			if(currentNodeID != successorIndex)
        				successors.add(successorIndex);
				}
        	}
        }
        if(successors.size() == 0)
        {
        	//TODO: doda� poprzednik tego stanu, ale to si� chyba nie zdarzy...
        }
        return successors;
    }

    ArrayList<Pair<Integer, Double>> calculateProbabilities(ArrayList<Integer> successors, int sourceIndex, double alpha, Graph graph)
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

			if(Double.isNaN(p.getValue()))
				System.out.println("?");

    		probabilities.add(p);
    	}

    	return probabilities;
    }

    //zwraca indeks wybranego nast�pnika w grafie
    //TODO: mo�e robi� to w p�tli, dop�ki jaki� nast�pnik nie zostanie wybrany?
    int selectSuccessor(ArrayList<Pair<Integer, Double>> probabilities)
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
        System.out.println("co� nie tak z wyborem nast�pnika...");
        return -1;
    }

    Path buildPath(double alpha, Graph graph, SudokuBoard sudokuBoard/*int iteration*/) //wybiera nast�pnika na podst. prawdopodobie�stwa i dodaje w�z�y do �cie�ki, dop�ki nie znajdzie w�z�a docelowego
    {
        int sourceIndex = 0;//czy na pewno, mo�e podawa� jako argument?
    	Path path = new Path(sourceIndex);
    	boolean isSolved = sudokuBoard.isSolved(graph.getNode(sourceIndex));    	
    	ArrayList<Integer> successors;
    	ArrayList<Pair<Integer, Double>> probabilities;
    	int targetIndex;

    	while(!isSolved)
    	{
			//System.out.println("1!");
    		successors = generateSuccessors(sourceIndex, graph, sudokuBoard);
			//System.out.println("2!");
			probabilities = calculateProbabilities(successors, sourceIndex, alpha, graph);
			//System.out.println("3!");
			targetIndex = selectSuccessor(probabilities);
			//System.out.println("4!");

			Node successor = graph.getNode(targetIndex);
			//System.out.println("5!");
			path.addNode(targetIndex);
			//System.out.println("6!");

			sourceIndex = targetIndex;
			//System.out.println("7!");
			isSolved = sudokuBoard.isSolved(successor);
			//System.out.println("8!");
		}
    	

    	
    	path.removeLoops();
    	paths.add(path);
        return path;
    }

    void updatePheromone(Graph graph, int iteration)//przy za�o�eniu, �e iteracje s� numerowane od 0
    {
        Path currentPath = paths.get(iteration);
    	int nodesInPath = currentPath.getNodesNumber();
    	double pheromoneToAdd = 1 / currentPath.evaluationFunction();
    	System.out.println(pheromoneToAdd);
    	for(int sourceIndex = 0; sourceIndex < nodesInPath - 1; sourceIndex++)
    	{
    		int targetIndex = sourceIndex + 1; //indeks, pod kt�rym w currentPath znajduje si� indeks w�z�a w grafie
    		int source = currentPath.getNode(sourceIndex);
    		int target = currentPath.getNode(targetIndex); //indeksy w�z��w w grafie
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

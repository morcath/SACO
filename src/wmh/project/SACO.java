package wmh.project;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;

public class SACO {
    private SudokuBoard inputBoard;
    private double alpha; //?
    private double rho;
    private int antsNum;
    private int maxIterations;
    private double epsilon;
    private double p;
    private SudokuLevel level;
    private String filename;
    private boolean end = false;
    private Graph graph = new Graph();
    private Ant[] ants;// = new Ant[antsNum];
    private ArrayList<Path> paths = new ArrayList<Path>();
    private Path bestSolution;

    public SACO(double _alpha, double _rho, int _antNum, int _maxIterations, int _epsilon, double _p, 
    		SudokuLevel _level, String _filename)
    {
        alpha = _alpha;
        rho = _rho;
        antsNum = _antNum;
        ants = new Ant[antsNum];
        maxIterations = _maxIterations;
        epsilon = _epsilon;
        p =_p;
        level = _level;
        //graph = new Graph(/*level*/);
        filename = _filename;
        
    }

    private void reducePheromone()
    {
        graph.evaporatePheromone(rho);
    }

    private void addNewPath(Path path)
    {
        int pathSize = paths.size();
        for(int i = 0; i < pathSize; i++)
        {
        	if(paths.get(i).equals(path))
        	{
        		return;
        	}
        }
        paths.add(path);
    }

    private Path selectBestSolution()
    {
        int length = paths.size();
    	if(length < 1)
        {
        	return null;
        }
        
    	Path bestPath = paths.get(0);
        double bestEvaluationFunction = bestPath.evaluationFunction();
        Path current;
        double currentEvaluationFunction;
        
        for(int i = 1; i < length; i++)
        {
        	current = paths.get(i);
        	currentEvaluationFunction = current.evaluationFunction();
        	if(currentEvaluationFunction < bestEvaluationFunction)
        	{
        		bestPath = current;
        		bestEvaluationFunction = currentEvaluationFunction;
        	}
        }
        return bestPath;
    }
    
    
    public void execute()
    {
        InputReader input = new InputReader();
        /*SudokuBoard sudokuBoard*/inputBoard = input.readBoard(filename, level);

        //sudokuBoard.displayBoard();

        //initial Node
        ArrayList<Move> initialMoves = new ArrayList<Move>();
        Node initialNode = new Node(initialMoves, inputBoard);
        graph.addNode(initialNode, inputBoard);
        int timestep = 0;

        // bring ants to life!
        for(int i=0; i<antsNum; ++i)
            ants[i] = new Ant();

        
        //System.out.println(graph.getNodeIndex(initialNode));
        int loop = 0;
        // main loop
        while(!end)
        {
            System.out.println(loop);
            loop++;
            // construct path for each ant
            for (int i = 0; i < antsNum; ++i)
                addNewPath(ants[i].buildPath(alpha, graph, inputBoard/*timestep*/));

            //pheromone evaporation
            reducePheromone();

            //update pheromone
            for(int i=0; i<antsNum; ++i)
                ants[i].updatePheromone(graph, timestep);

            timestep += 1;
            //obsï¿½uga warunkï¿½w zakoï¿½czenia
            //przekroczenie maksymalnej liczby iteracji
            if(timestep >= maxIterations)
            {
            	System.out.println("Warunek stopu: przekroczenie maksymalnej liczby iteracji");
            	end = true;
            	continue; //albo po prostu break i juï¿½
            }            
            
            //wiï¿½kszoï¿½ï¿½ mrï¿½wek podï¿½ï¿½a tï¿½ samï¿½ ï¿½cieï¿½kï¿½
            if(!end && p > 0.5)
            {
            	//ï¿½cieï¿½ki wyznaczone przez wszystkie mrï¿½wki w bieï¿½ï¿½cej iteracji
	            ArrayList<Path> currentPaths = new ArrayList<Path>(antsNum);
	            for(int i = 0; i < antsNum; i++)
	            {
	            	currentPaths.add(ants[i].getPathFromIteration(timestep - 1));
	            }
	            
	            for(int i = 0; i < antsNum; i++)
	            {
	            	double frequency = Collections.frequency(currentPaths, currentPaths.get(i));
	            	if(frequency >= p * antsNum)
	            	{
	            		System.out.println("Warunek stopu: wiï¿½kszoï¿½ï¿½ mrï¿½wek podï¿½ï¿½a tï¿½ samï¿½ ï¿½cieï¿½kï¿½");
	            		end = true;
	            		break;
	            	}
	            }
            }
            
            //znaleziono akceptowalne rozwiï¿½zanie
            if(!end && epsilon > 0)
            {
            	for(Path path: paths)
                if(path.evaluationFunction() < epsilon)
                {
                	System.out.println("Warunek stopu: znaleziono akceptowalne rozwiï¿½zanie");
                	end = true;
                    break;
                }
            }
            
        }        
        
        bestSolution = selectBestSolution();
    }
    
    public void displayBestSolution()
    {
    	if(bestSolution == null)
    	{
    		System.out.println("Nie uda³o siê znalezc zadnego rozwiazania");
    		return;
    	}
    	System.out.println("Najlepsze rozwiazanie:\npath = ");
    	bestSolution.display();
    	System.out.println("Kolejne wersje planszy");
    	for(int i = 0; i < bestSolution.getNodesNumber(); i++)
    	{
    		System.out.println("Wersja nr " + (i + 1));
    		int nodeIndex = bestSolution.getNode(i);
    		int[][] board = graph.getNode(nodeIndex).recreateBoard(inputBoard);
    		for(int m = 0; m < 9; m++)
    		{
    			for(int n = 0; n < 9; n++)
    			{
    				System.out.print(board[m][n]);
    			}
    			System.out.println();
    		}
    		System.out.println();
    	}
    }
    
    public void displayGraph()
    {
    	graph.display();
    }
}


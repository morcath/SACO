package wmh.project;

import java.util.ArrayList;
import java.util.List;

public class SACO {
    SudokuBoard inputBoard;
    private double alpha; //?
    private double rho;
    private int antsNum;
    private int maxIterations;
    private double epsilon;
    private double p;
    private SudokuLevel level;
    private boolean end = false;
    private Graph graph = new Graph(SudokuLevel.EASY);
    private Ant[] ants = new Ant[antsNum];
    private List<Path> paths = new ArrayList<Path>();
    private Path bestSolution;

    public SACO(double _alpha, double _rho, int _antNum, int _maxIterations, int _epsilon, double _p, SudokuLevel _level)
    {
        alpha = _alpha;
        rho = _rho;
        antsNum = _antNum;
        maxIterations = _maxIterations;
        epsilon = _epsilon;
        p =_p;
        level = _level;
    }

    private void reducePheromone()
    {
        graph.evaporatePheromone(rho);
    }

    private void addNewPath(Path path)
    {
        //todo
        paths.add(path);
    }

    private Path selectBestSolution()
    {
        //todo
        return paths.get(0);
    }

    public void execute()
    {
        InputReader input = new InputReader();
        SudokuBoard sudokuBoard = input.readBoard("/home/aleksander/github/SACO/src/wmh/project/sudoku", level);

        //sudokuBoard.displayBoard();

        //initial Node
        ArrayList<Move> initialMoves = new ArrayList<Move>();
        Node initialNode = new Node(initialMoves, sudokuBoard);
        graph.addNode(initialNode);
        int timestep = 0;

        // bring ants to life!
        for(int i=0; i<antsNum; ++i)
            ants[i] = new Ant();

        // main loop
        while(!end)
        {
            // construct path for each ant
            for (int i = 0; i < antsNum; ++i)
                addNewPath(ants[i].buildPath(maxIterations));

            //pheromone evaporation
            reducePheromone();

            //update pheromone
            for(int i=0; i<antsNum; ++i)
                ants[i].updatePheromone(rho, graph, timestep);

            timestep += 1;

            for(Path path: paths)
                if(path.evaluationFunction() < epsilon)
                {
                    end = true;
                    break;
                }
        }

        bestSolution = selectBestSolution();
    }
}


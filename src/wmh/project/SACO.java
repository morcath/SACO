package wmh.project;

import java.util.ArrayList;
import java.util.List;

public class SACO {
    SudokuBoard inputBoard;
    private double alpha;
    private double rho;
    private int antsNum;
    private int maxIterations;
    private int epsilon;
    private double p;
    private SudokuLevel level;
    Graph graph = new Graph(SudokuLevel.EASY);
    Ant[] ants = new Ant[antsNum];
    List<Path> paths = new ArrayList<Path>();

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
        //todo
    }

    private void addNewPath(Path path)
    {
        //todo
    }

    private Path selectBestSolution()
    {
        //todo
        return paths.get(0);
    }

    public void execute()
    {
        InputReader input = new InputReader();
        SudokuBoard sudokuBoard = input.readBoard("/home/aleksander/gitlab/Ant/src/com/company/sudoku", level);

        sudokuBoard.displayBoard();


    }

}


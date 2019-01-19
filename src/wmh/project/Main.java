package wmh.project;



public class Main {

	public static void main(String[] args)
    {         
    	/*String filename = "sudoku/easy1.txt";
        InputReader r = new InputReader();        
        SudokuBoard b = r.readBoard(filename, SudokuLevel.EASY);        
        b.displayBoard();*/
    	double alpha = 2; //nigdzie nie ma mowy o tym, z jakiego przedzia³u ma byæ
    	double rho = 0.2;
    	int antNum = 2;
    	int maxIterations = 5;
    	int epsilon = 0;
    	double p = 1;
    	SudokuLevel level = SudokuLevel.EASY;
    	String filename = "test1.txt";
    	
    	SACO saco = new SACO(alpha, rho, antNum, maxIterations, epsilon, p, level, filename);
        saco.execute();
        saco.displayBestSolution();
        saco.displayGraph();
    }
}

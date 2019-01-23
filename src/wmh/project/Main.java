package wmh.project;



public class Main {

	public static void main(String[] args)
    {         
    	double alpha = 1; //nigdzie nie ma mowy o tym, z jakiego przedzia³u ma byæ
    	double rho = 0.2;
    	int antNum = 5;
    	int maxIterations = 5;
    	int epsilon = 0;
    	double p = 1;
    	SudokuLevel level = SudokuLevel.EASY;
    	String filename = "test1.txt";
    	
    	SACO saco = new SACO(alpha, rho, antNum, maxIterations, epsilon, p, level, filename);
        saco.execute();
        saco.displayBestSolution();
    	//saco.displayGraph();
    }
}

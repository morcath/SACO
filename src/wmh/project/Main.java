package wmh.project;



public class Main {

	public static void main(String[] args)
    {         
    	String filename = "sudoku/easy1.txt";
        /*InputReader r = new InputReader();        
        SudokuBoard b = r.readBoard(filename, SudokuLevel.EASY);        
        b.displayBoard();*/
    	
    	SACO saco = new SACO(0,0,0,0,0,0, SudokuLevel.EASY, filename);
        //saco.execute();
    }
}

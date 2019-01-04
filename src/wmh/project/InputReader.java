package wmh.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InputReader {

    private static int[][] board = new int[9][9];
    private static int fullFields = 0;

    SudokuBoard readBoard(String filename, SudokuLevel level)
    {
        try {
            File file = new File(filename);
            Scanner sc = new Scanner(file);

            sc.useDelimiter("\\Z");
            String wholeBoard = sc.next();

            for (int i = 0; i < wholeBoard.length(); ++i) {
                int tmp = Character.getNumericValue(wholeBoard.charAt(i));
                if (tmp >= 0 && tmp <= 9) {
                    board[fullFields / 9][fullFields % 9] = tmp;
                    fullFields += 1;
                }

            }

        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found");
            System.exit(-1);
        }
        finally
        {
            SudokuBoard sudokuBoard = new SudokuBoard(level, board);
            return sudokuBoard;
        }
    }
}


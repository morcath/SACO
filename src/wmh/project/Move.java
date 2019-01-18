package wmh.project;

public class Move {
    int rowFrom;
    int colFrom;
    int rowTo;
    int colTo;

    public Move(int _rowFrom, int _colFrom, int _rowTo, int _colTo)
    {
        rowFrom = _rowFrom;
        colFrom = _colFrom;
        rowTo = _rowTo;
        colTo = _colTo;
    }
    
    public boolean equalTo(Move other)
    {
    	return this.rowFrom == other.rowFrom && this.colFrom == other.colFrom
    			&& this.rowTo == other.rowTo && this.colTo == other.colTo;
    }
}

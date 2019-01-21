//Mark Blasko
//CPSC 2150
package cpsc2150.connectX;
public class GameBoard implements IGameBoard {
    /**
     * @invariant board != null
     * Correspondence number_of_rows = row
     * Correspondence number_of_cols = col
     * Correspondence this = board[0...row][0...col]
     */
    private char[][] board;
    private int row; //top most row index
    private int col; //furthest col to right index
    private int winNumber;
    public final static int FORMAT_STRING = 9;

    /**
     * @param cols number of columns
     * @param rows number of rows
     * @param win number of elements needed to win
     * @post [the top row contains the index of each column and the remaining
     *        cells are initialized to the space character]
     */
    GameBoard(int rows, int cols, int win) {
        winNumber = win;
        row = rows - 1;
        col = cols - 1;
        board = new char[rows+1][cols];
        for (int x = 0; x < rows+1; x++) {
            for (int y = 0; y < cols; y++)
                if (x != rows)
                    board[x][y] = ' ';
                else
                    board[x][y] = (char) y;
        }
    }

    public void placeToken(char p, int c) {
        int x = 0;
        while (board[x][c] != ' ' && x != row) {
            x++;
        }
        board[x][c] = p;
    }

    public char whatsAtPos(int r, int c) {
        if (r > getNumRows() || r < 0 || c > getNumColumns() || c < 0)
            return ' ';
        else
            return board[r][c];
    }

    /**
     * returns friendly representation of board
     *
     * @pre board != null
     * @return string representation of game board
     */
    @Override
    public String toString() {
        String game = "";
        for (int x = board.length - 1; x >= 0; x--) {
            for (int y = 0; y < board[x].length; y++)
                if (x == board.length-1)
                    if (y > FORMAT_STRING)
                        game += "|" + (int) board[x][y];
                    else
                        game += "| " + (int) board[x][y];
                else {
                    game += "| " + board[x][y];
                }
            game += "|\n";
        }
        return game;
    }

    public int getNumRows() {
        return row;
    }

    public int getNumColumns() {
        return col;
    }

    public int getNumToWin() {
        return winNumber;
    }
}
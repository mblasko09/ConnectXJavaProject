package cpsc2150.connectX;

import java.util.HashMap;
import java.util.ArrayList;

public class GameBoardMem implements IGameBoard {
    /**
     * @invariant gameBoard != null
     * Correspondence number_of_rows = row
     * Correspondence number_of_cols = col
     * Correspondence this = HashMap<Integer, ArrayList<Character>>
     */
    private HashMap<Integer, ArrayList<Character>> gameBoard;
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
    GameBoardMem(int rows, int cols, int win) {
        winNumber = win;
        row = rows-1;
        col = cols-1;
        gameBoard = new HashMap<>();
        for (int i = 0; i < cols; i++)
            gameBoard.put(i, new ArrayList<>());
    }

    public void placeToken(char p, int c) {
        gameBoard.get(c).add(p);
    }

    public char whatsAtPos(int r, int c) {
        if (r >= gameBoard.get(c).size())
            return ' ';
        return gameBoard.get(c).get(r);
    }

    /**
     * returns friendly representation of board
     *
     * @pre gameBoard != null
     * @return string representation of game board
     */
    @Override
    public String toString() {
        String game = "";
        for (int i = 0; i < col+1; i++) {
            if (i > FORMAT_STRING)
                game += "|" + i;
            else
                game += "| " + i;
        }
        game += "|\n";
        for (int i = row; i >= 0; i--) {
            for (int j = 0; j <= col; j++) {
                game += "| " + whatsAtPos(i, j);
            }
            game+= "|\n";
        }
        return game;
    }

    public int getNumRows() { return row; }

    public int getNumColumns() { return col; }

    public int getNumToWin() { return winNumber; }

}

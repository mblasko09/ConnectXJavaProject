//Mark Blasko
//CPSC 2150
package cpsc2150.connectX;

/**
 * IGameBoard represents a 2 dimensional grid of characters.
 * Index starts at 0
 * Initialization post: IGameBoard contains only space characters
 * and is DIM_MAX x DIM_MAX or smaller
 * Defines:     number_of_rows: X
 *              number_of_columns: Y
 * Constraints: DIM_MIN <= number_of_rows <= DIM_MAX
 *              DIM_MIN <= number_of_cols <= DIM_MAX
 *
 */

public interface IGameBoard {
    int DIM_MAX = 100;
    int DIM_MIN = 3;
    int MAX_WIN = 25;


    /**
     * places token at specific position in board
     *
     * @param p current player
     * @param c column of token to be placed
     * @pre p = X or p = O and 0 <= c <= getNumColumns
     * @post [token is placed in first open spot of column] and [current column and row are updated]
     */
    void placeToken(char p, int c);

    /**
     * returns character at given position
     *
     * @param r row of token
     * @param c column of token
     * @return character at row r and column c
     * @pre 0 <= r <= getNumRows and 0 <= c <= getNumColumns
     * @post [character at row r and column c is returned] or [empty is out of bounds]
     */
    char whatsAtPos(int r, int c);

    /**
     * gets number of rows in GameBoard
     *
     * @pre this != null
     * @post [the number of rows will be returned]
     * @return number of rows in GameBoard
     */
    int getNumRows();

    /**
     * returns number of columns in GameBoard 2 dimensional array
     * @pre this != null
     * @post [the number of columns will be returned]
     * @return number of columns in GameBoard
     */
    int getNumColumns();

    /**
     * returns number of elements needed in a row to win
     * @pre this != null
     * @post [the number of elements in a row needed to win are returned]
     * @return number of elements in a row necessary to win
     */
    int getNumToWin();


    /**
     * @param c number of columns
     * @return true iff column is free
     * @pre DIM_MIN <=  c <= DIM_MAX
     * @post [true is returned iff position is inbound and column has free spot]
     */
    default boolean checkIfFree(int c) {
        return whatsAtPos(getNumRows(), c) == ' ';
    }

    /**
     * @param c number of columns
     * @return true iff player
     * @pre DIM_MIN <= c <= DIM_MAX
     * @post [true is returned iff player has won horizontally, vertically, or diagonally]
     */
    default boolean checkForWin(int c) {
        int lastRow = 0;
        while (whatsAtPos(lastRow+1, c) != ' ')
            lastRow++;
        return (checkHorizWin(lastRow, c, whatsAtPos(lastRow, c)) || checkVertWin(lastRow, c, whatsAtPos(lastRow, c)) || checkDiagWin(lastRow, c, whatsAtPos(lastRow, c)));
    }

    /**
     * @return true iff all spaces in grid are filled with tokens
     * @pre this != null
     * @post [true is returned iff all positions are filled]
     */
    default boolean checkTie() {
        for (int x = 0; x <= getNumRows(); x++)
            for (int y = 0; y <= getNumColumns(); y++)
                if (whatsAtPos(x, y) == ' ') return false;
        return true;
    }


    /**
     * @param r row of most recent token
     * @param c column of most recent token
     * @param p player that placed most recent token
     * @return true iff player has getNumToWin() tokens in a row horizontally
     * @pre DIM_MIN <= r <= DIM_MAX and DIM_MIN <= c <= DIM_MAX and p = X or p = O
     * @post [returns true iff player has won horizontally]
     */
    default boolean checkHorizWin(int r, int c, char p) {
        int count = 0;
        int y = 0;
        while (y <= getNumColumns()) {
            if (whatsAtPos(r, y) == p)
                count++;
            else
                count = 0;
            if (count == getNumToWin())
                return true;
            y++;
        }
        return false;
    }

    /**
     * @param r row of most recent token
     * @param c column of most recent token
     * @param p player that placed most recent token
     * @return true iff player has getNumToWin() tokens in a row vertically
     * @pre DIM_MIN <= r <= DIM_MAX and DIM_MIN <= c <= DIM_MAX and p = X or p = O
     * @post [returns true iff player has won vertically]
     */
    default boolean checkVertWin(int r, int c, char p) {
        int count = 0;
        int x = 0;
        while (x <= getNumRows()) {
            if (whatsAtPos(x, c) == p) {
                count++;
            } else
                count = 0;
            if (count == getNumToWin())
                return true;
            x++;
        }
        return false;
    }

    /**
     * @param r row of most recent token
     * @param c column of most recent token
     * @param p player that placed most recent token
     * @return true iff player has getNumToWin() tokens in a row diagonally
     * @pre DIM_MIN <= r <= DIM_MAX and DIM_MIN <= c <= DIM_MAX and p = X or p = O
     * @post [returns true iff player has won diagonally]
     */
    default boolean checkDiagWin(int r, int c, char p) {
        int i;
        int j;
        int count;
        for (int x = 0; x <= getNumRows(); x++) {
            for (int y = 0; y <= getNumColumns(); y++) {
                i = x;
                j = y;
                count = 0;
                while (i <= getNumRows() && j <= getNumColumns() && whatsAtPos(i, j) == p) {
                    count++;
                    i++;
                    j++;
                    if (count == getNumToWin())
                        return true;
                }
            }
        }
        for (int x = 0; x <= getNumRows(); x++) {
            for (int y = getNumColumns(); y >= 0; y--) {
                i = x;
                j = y;
                count = 0;
                while (i <= getNumRows() && j >= 0 && whatsAtPos(i, j) == p) {
                    count++;
                    i++;
                    j--;
                    if (count == getNumToWin())
                        return true;
                }
            }
        }
        return false;
    }
}

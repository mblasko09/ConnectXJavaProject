package cpsc2150.connectX;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestIGameBoard {

    public IGameBoard gameBoardFactory(int rows, int cols, int wins) {
        return new GameBoard(rows, cols, wins);
        //return new GameBoardMem(rows, cols, wins);
    }
    public String boardToString(char[][] board) {
        String game = "";
        for (int i = 0; i < board[board.length-1].length; i++)
            if (i > 9) game += "|" + i;
            else game += "| " + i;
            game += "|\n";
            for (int x = board.length - 1; x >= 0; x--) {
                for (int y = 0; y < board[x].length; y++) {
                    if (board[x][y] == '\u0000') board[x][y] = ' ';
                    game += "| " + board[x][y];
                }
            game += "|\n";
        }
        return game;
    }
    @Test
    public void testCheckIfFree_Normal_4() {
        char[][] boardArr = new char[10][10];
        IGameBoard board = gameBoardFactory(10, 10, 10 );
        for (int i = 0; i < 5; i++) {
            board.placeToken('X', i);
            board.placeToken('O', i);
            boardArr[0][i] = 'X';
            boardArr[1][i] = 'O';
        }
        assertEquals(boardToString(boardArr), board.toString());
        assertTrue (board.checkIfFree(4));
    }
    @Test
    public void testCheckIfFree_Furthest_Right_19() {
        char[][] boardArr = new char[20][20];
        IGameBoard board = gameBoardFactory(20, 20, 5);
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue (board.checkIfFree(19));
    }
    @Test
    public void testCheckIfFree_One_Space_Left_In_Column_0() {
        char[][] boardArr = new char[5][5];
        IGameBoard board = gameBoardFactory(5, 5, 3);
        for (int i = 0, j = 0, k = 1; i < board.getNumRows()/2; i++, j+=2, k+=2) {
            board.placeToken('X', 0);
            board.placeToken('O', 0);
            boardArr[j][0] = 'X';
            boardArr[k][0] = 'O';
        }
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.checkIfFree(0));
    }
    @Test
    public void testCheckIfFree_Full_0() {
        char[][] boardArr = new char[3][3];
        IGameBoard board = gameBoardFactory(3, 3, 3);
        board.placeToken('X', 0);
        board.placeToken('O', 0);
        board.placeToken('X', 0);
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                boardArr[i][j] = board.whatsAtPos(i, j);
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue (!board.checkIfFree(0));
    }
    @Test
    public void testCheckHorizWin_Min_Three_0_3_O() {
        char[][] boardArr = new char[5][5];
        IGameBoard board = gameBoardFactory(5, 5, 3);
        for (int i = 0; i < 2; i++) {
            board.placeToken('O', i);
            board.placeToken('X', i);
            boardArr[0][i] = 'O';
            boardArr[1][i] = 'X';
        }
        board.placeToken('O', 2);
        boardArr[0][2] = 'O';
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.checkHorizWin(0, 3, 'O'));
    }
    @Test
    public void testCheckHorizWin_Max_TwentyFive_0_24_X() {
        char[][] boardArr = new char[30][30];
        IGameBoard board = gameBoardFactory(30, 30, 25);
        for (int i = 0; i < 24; i++) {
            board.placeToken('X', i);
            board.placeToken('O', i);
            boardArr[0][i] = 'X';
            boardArr[1][i] = 'O';
        }
        board.placeToken('X', 24);
        boardArr[0][24] = 'X';
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue (board.checkHorizWin(0, 24, 'X'));
    }
    @Test
    public void testCheckHorizWin_Row_On_Top_1_5_O() {
        char[][] boardArr = new char[6][6];
        IGameBoard board = gameBoardFactory(6, 6, 6);
        for (int i = 0; i < 5; i++) {
            board.placeToken('X', i);
            board.placeToken('O', i);
            boardArr[0][i] = 'X';
            boardArr[1][i] = 'O';
        }
        board.placeToken('X', 0);
        board.placeToken('O', 5);
        board.placeToken('X', 1);
        board.placeToken('O', 5);
        boardArr[2][0] = 'X';
        boardArr[0][5] = 'O';
        boardArr[2][1] = 'X';
        boardArr[1][5] = 'O';
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue (board.checkHorizWin(1, 5, 'O'));
    }
    @Test
    public void testCheckHorizWin_Opponent_On_Both_Sides_0_1_X() {
        char[][] boardArr = new char[5][5];
        IGameBoard board = gameBoardFactory(5, 5, 3);
        board.placeToken('X', 1);
        board.placeToken('O', 0);
        board.placeToken('X', 2);
        board.placeToken('O', 4);
        board.placeToken('X', 3);
        boardArr[0][1] = 'X';
        boardArr[0][0] = 'O';
        boardArr[0][2] = 'X';
        boardArr[0][4] = 'O';
        boardArr[0][3] = 'X';
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.checkHorizWin(0, 1, 'X'));
    }
    @Test
    public void testCheckHorizWin_OpponentOnTop_Win_In_Middle_0_0_X() {
        char[][] boardArr = new char[5][10];
        IGameBoard board = gameBoardFactory(5, 10, 5);
        for (int i = 0; i < 5; i++)
            if (i != 2) {
                board.placeToken('X', i);
                board.placeToken('O', i);
                boardArr[0][i] = 'X';
                boardArr[1][i] = 'O';
            }
            board.placeToken('X', 2);
            boardArr[0][2] = 'X';
            assertEquals(board.toString(), boardToString(boardArr));
            assertTrue(board.checkHorizWin(0, 0, 'X'));
    }
    @Test
    public void testCheckVertWin_Min_Three_0_0_X() {
        char[][] boardArr = new char[5][5];
        IGameBoard board = gameBoardFactory(5,5, 3);
        for (int i = 0; i < 2; i++) {
            board.placeToken('X', 0);
            board.placeToken('O', 1);
            boardArr[i][0] = 'X';
            boardArr[i][1] = 'O';
        }
        board.placeToken('X', 0);
        boardArr[2][0] = 'X';
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.checkVertWin(2, 0, 'X'));
    }
    @Test
    public void testCheckVertWin_Max_Twenty_Five_0_0_X() {
        char[][] boardArr = new char[30][30];
        IGameBoard board = gameBoardFactory(30, 30, 25);
        for (int i = 0; i < 24; i++) {
            board.placeToken('X', 0);
            board.placeToken('O', 1);
            boardArr[i][0] = 'X';
            boardArr[i][1] = 'O';
        }
        board.placeToken('X', 0);
        boardArr[24][0] = 'X';
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.checkVertWin(0, 0, 'X'));
    }
    @Test
    public void testCheckVertWin_Win_On_Last_row_4_3_X() {
        char[][] boardArr = new char[5][5];
        IGameBoard board = gameBoardFactory(5, 5,5);
        for (int i = 0; i < 4; i++) {
            board.placeToken('X', 3);
            board.placeToken('O', 1);
            boardArr[i][3] = 'X';
            boardArr[i][1] = 'O';
        }
        board.placeToken('X', 3);
        boardArr[4][3] = 'X';
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.checkVertWin(4, 3, 'X'));
    }
    @Test
    public void testCheckVertWin_Opponent_Below_3_3_O() {
        char[][] boardArr = new char[7][5];
        IGameBoard board = gameBoardFactory(7, 5,3);
        board.placeToken('X', 3);
        boardArr[0][3] = 'X';
        for (int i = 0; i < 2; i++) {
            board.placeToken('O', 3);
            board.placeToken('X', 1);
            boardArr[i+1][3] = 'O';
            boardArr[i][1] = 'X';
        }
        board.placeToken('O', 3);
        boardArr[3][3] = 'O';
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.checkVertWin(3, 3, 'O'));
    }
    @Test
    public void testCheckVertWin_Normal_0_9_X() {
        char[][] boardArr = new char[10][10];
        IGameBoard board = gameBoardFactory(10, 10,5);
        for (int i = 0, j = 0, k = 1; i < 3; i++, j+=2, k+=2) {
            board.placeToken('X', 2);
            board.placeToken('O', 2);
            boardArr[j][2] = 'X';
            boardArr[k][2] = 'O';
        }
        for (int i = 0; i < 4; i++) {
            board.placeToken('X', 9);
            board.placeToken('O', 7);
            boardArr[i][9] = 'X';
            boardArr[i][7] = 'O';
        }
        board.placeToken('X', 9);
        boardArr[4][9] = 'X';
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.checkVertWin(0, 9, 'X'));
    }
    @Test
    public void testCheckDiagWin_Normal_Left_To_Right_0_0_X() {
        char[][] boardArr = new char[10][10];
        IGameBoard board = gameBoardFactory(10, 10,5);
        for (int i = 0, j = 1; i < 5; i+=2, j+=2) {
            board.placeToken('X', i);
            board.placeToken('O', j);
            boardArr[0][i] = 'X';
            boardArr[0][j] = 'O';
        }
        for (int i = 1, j = 2; i < 5; i+=2, j+=2) {
            board.placeToken('X', i);
            board.placeToken('O', j);
            boardArr[1][i] = 'X';
            boardArr[1][j] = 'O';
        }
        for (int i = 2, j = 3; i < 5; i+=2, j+=2) {
            board.placeToken('X', i);
            board.placeToken('O', j);
            boardArr[2][i] = 'X';
            boardArr[2][j] = 'O';
        }
        for (int i = 3, j = 4; i < 5; i+=2, j+=2) {
            board.placeToken('X', i);
            board.placeToken('O', j);
            boardArr[3][i] = 'X';
            boardArr[3][j] = 'O';
        }
        board.placeToken('X', 4);
        boardArr[4][4] = 'X';
        boardArr[1][5] = 'O';
        boardArr[2][5] = ' ';
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.checkDiagWin(0, 0, 'X'));
    }
    @Test
    public void testCheckDiagWin_Normal_Right_To_Left_0_9_X() {
        char[][] boardArr = new char[10][10];
        IGameBoard board = gameBoardFactory(10, 10,5);
        for (int i = 9, j = 8; i > 4; i=i-2, j=j-2) {
            board.placeToken('X', i);
            board.placeToken('O', j);
            boardArr[0][i] = 'X';
            boardArr[0][j] = 'O';
        }
        for (int i = 8, j = 7; i > 4; i=i-2, j=j-2) {
            board.placeToken('X', i);
            board.placeToken('O', j);
            boardArr[1][i] = 'X';
            boardArr[1][j] = 'O';
        }
        for (int i = 7, j = 6; i > 4; i=i-2, j=j-2) {
            board.placeToken('X', i);
            board.placeToken('O', j);
            boardArr[2][i] = 'X';
            boardArr[2][j] = 'O';
        }
       for (int i = 6, j = 5; i > 4; i=i-2, j=j-2) {
            board.placeToken('X', i);
            board.placeToken('O', j);
            boardArr[3][i] = 'X';
            boardArr[3][j] = 'O';
        }
        board.placeToken('X', 5);
        boardArr[4][5] = 'X';
        boardArr[1][4] = 'O';
        boardArr[2][4] = ' ';
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.checkDiagWin(0, 9, 'X'));
    }
    @Test
    public void testCheckDiagWin_Min_Three_Right_To_Left_0_0_X() {
        char[][] boardArr = new char[5][5];
        IGameBoard board = gameBoardFactory(5, 5, 3);
        for (int i = 0, j = 1; i < 3; i += 2, j += 2) {
            board.placeToken('X', i);
            board.placeToken('O', j);
            boardArr[0][i] = 'X';
            boardArr[0][j] = 'O';
        }
        for (int i = 1, j = 2; i < 3; i += 2, j += 2) {
            board.placeToken('X', i);
            board.placeToken('O', j);
            boardArr[1][i] = 'X';
            boardArr[1][j] = 'O';
        }
        board.placeToken('X', 2);
        boardArr[2][2] = 'X';
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.checkDiagWin(0, 0, 'X'));
    }
    @Test
    public void testCheckDiagWin_Win_At_Top_Row_Large_0_1_X() {
        char[][] boardArr = new char[20][20];
        IGameBoard board = gameBoardFactory(20, 20, 3);
        for (int i = 0, j = 0, k = 1; i < 9; i++, j+=2, k+=2) {
            board.placeToken('O', 1);
            board.placeToken('X', 1);
            boardArr[j][1] = 'O';
            boardArr[k][1] = 'X';
        }
        board.placeToken('O', 4);
        boardArr[0][4] = 'O';
        for (int i = 0, j = 0, k = 1; i < 9; i++, j+=2, k+=2) {
            board.placeToken('X', 2);
            board.placeToken('O', 2);
            boardArr[j][2] = 'X';
            boardArr[k][2] = 'O';
        }
        board.placeToken('O', 5);
        boardArr[0][5] = 'O';
        for (int i = 0, j = 0, k = 1; i < 9; i++, j+=2, k+=2) {
            board.placeToken('X', 3);
            board.placeToken('O', 3);
            boardArr[j][3] = 'X';
            boardArr[k][3] = 'O';
        }
        board.placeToken('X', 2);
        boardArr[18][2] = 'X';
        board.placeToken('O', 3);
        boardArr[18][3] = 'O';
        board.placeToken('X', 3);
        boardArr[19][3] = 'X';
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.checkDiagWin(0, 1, 'X'));
    }
    @Test
    public void testCheckDiagWin_Number_To_Win_Equals_Dimension_0_3_X() {
        char[][] boardArr = new char[4][4];
        IGameBoard board = gameBoardFactory(4, 4, 4);
        for (int i = 0, j = 0, k = 1; i < 2; i++, j+=2, k+=2) {
            board.placeToken('O', 3);
            board.placeToken('X', 3);
            boardArr[j][3] = 'O';
            boardArr[k][3] = 'X';
        }
        board.placeToken('O', 2);
        boardArr[0][2] = 'O';
        board.placeToken('X', 2);
        boardArr[1][2] = 'X';
        board.placeToken('O', 1);
        boardArr[0][1] = 'O';
        board.placeToken('X', 2);
        boardArr[2][2] = 'X';
        board.placeToken('O', 2);
        boardArr[3][2] = 'O';
        board.placeToken('X', 1);
        boardArr[1][1] = 'X';
        board.placeToken('O', 1);
        boardArr[2][1] = 'O';
        board.placeToken('X', 0);
        boardArr[0][0] = 'X';
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.checkDiagWin(0, 3, 'X'));
    }
    @Test
    public void testCheckDiagWin_Winning_Token_Placed_In_Middle_Right_To_Left_1_1_X() {
        char[][] boardArr = new char[10][10];
        IGameBoard board = gameBoardFactory(10, 10, 3);
        for (int i = 0, j = 1; i < 3; i+=2, j+=2) {
            board.placeToken('X', i);
            board.placeToken('O', j);
            boardArr[0][i] = 'X';
            boardArr[0][j] = 'O';
        }
        board.placeToken('X', 3);
        boardArr[1][3] = 'X';
        board.placeToken('O', 2);
        boardArr[1][2] = 'O';
        board.placeToken('X', 2);
        boardArr[2][2] = 'X';
        board.placeToken('O', 0);
        boardArr[1][0] = 'O';
        board.placeToken('X', 1);
        boardArr[1][1] = 'X';
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.checkDiagWin(1, 1, 'X'));
    }

    @Test
    public void testCheckDiagWin_Min_Three_Left_To_Right_0_4_X() {
        char[][] boardArr = new char[5][5];
        IGameBoard board = gameBoardFactory(5, 5, 3);
        for (int i = 4, j = 3; i >= 2; i=i-2, j=j-2) {
            board.placeToken('X', i);
            board.placeToken('O', j);
            boardArr[0][i] = 'X';
            boardArr[0][j] = 'O';
        }
        for (int i = 3, j = 2; i >= 2; i=i-2, j=j-2) {
            board.placeToken('X', i);
            board.placeToken('O', j);
            boardArr[1][i] = 'X';
            boardArr[1][j] = 'O';
        }
        board.placeToken('X', 2);
        boardArr[2][2] = 'X';
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.checkDiagWin(0, 4, 'X'));
    }
    @Test
    public void testCheckDiagWin_Winning_Token_Placed_In_Middle_Left_To_Right_1_1_X() {
        char[][] boardArr = new char[10][10];
        IGameBoard board = gameBoardFactory(10, 10, 3);
        for (int i = 6, j = 5; i > 3; i=i-2, j=j-2) {
            board.placeToken('X', i);
            board.placeToken('O', j);
            boardArr[0][i] = 'X';
            boardArr[0][j] = 'O';
        }
        board.placeToken('X', 3);
        boardArr[1][3] = 'X';
        board.placeToken('O', 4);
        boardArr[1][4] = 'O';
        board.placeToken('X', 4);
        boardArr[2][4] = 'X';
        board.placeToken('O', 6);
        boardArr[1][6] = 'O';
        board.placeToken('X', 5);
        boardArr[1][5] = 'X';
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.checkDiagWin(1, 1, 'X'));
    }
    @Test
    public void testCheckTie_Min_Board_Size() {
        char[][] boardArr = new char[3][3];
        IGameBoard board = gameBoardFactory(3, 3, 3);
        board.placeToken('X', 0);
        boardArr[0][0] = 'X';
        board.placeToken('O', 1);
        boardArr[0][1] = 'O';
        board.placeToken('X', 2);
        boardArr[0][2] = 'X';
        board.placeToken('O', 0);
        boardArr[1][0] = 'O';
        board.placeToken('X', 1);
        boardArr[1][1] = 'X';
        board.placeToken('O', 2);
        boardArr[1][2] = 'O';
        board.placeToken('O', 0);
        boardArr[2][0] = 'O';
        board.placeToken('X', 1);
        boardArr[2][1] = 'X';
        board.placeToken('O', 2);
        boardArr[2][2] = 'O';

        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.checkTie());
    }
    @Test
    public void testCheckTie_Top_Right_Place() {
        char[][] boardArr = new char[3][5];
        IGameBoard board = gameBoardFactory(3, 5, 5);
        for (int i = 0, j = 1; j < 5; i+=2, j+=2) {
            board.placeToken('X', i);
            board.placeToken('O', j);
        }
        board.placeToken('X', 4);
        for (int i = 0, j = 1; j < 5; i+=2, j+=2) {
            board.placeToken('O', i);
            board.placeToken('X', j);
        }
        board.placeToken('O', 4);
        for (int i = 0, j = 1; j < 5; i+=2, j+=2) {
            board.placeToken('X', i);
            board.placeToken('O', j);
        }
        board.placeToken('X', 4);
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 5; j++)
                boardArr[i][j] = board.whatsAtPos(i, j);
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.checkTie());
    }
    @Test
    public void testCheckTie_Top_Left_Place() {
        char[][] boardArr = new char[3][5];
        IGameBoard board = gameBoardFactory(3, 5, 5);
        for (int i = 0, j = 1; j < 5; i+=2, j+=2) {
            board.placeToken('X', i);
            board.placeToken('O', j);
        }
        board.placeToken('X', 4);
        for (int i = 0, j = 1; j < 5; i+=2, j+=2) {
            board.placeToken('O', i);
            board.placeToken('X', j);
        }
        board.placeToken('O', 4);
        for (int i = 1, j = 2; j < 5; i+=2, j+=2) {
            board.placeToken('X', i);
            board.placeToken('O', j);
        }
        board.placeToken('O', 0);
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 5; j++)
                boardArr[i][j] = board.whatsAtPos(i,j);
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.checkTie());
    }
    @Test
    public void testCheckTie_Win_In_Middle() {
        char[][] boardArr = new char[3][5];
        IGameBoard board = gameBoardFactory(3, 5, 5);
        for (int i = 0, j = 1; j < 5; i+=2, j+=2) {
            board.placeToken('X', i);
            board.placeToken('O', j);
        }
        board.placeToken('X', 4);
        for (int i = 0, j = 1; j < 5; i+=2, j+=2) {
            board.placeToken('O', i);
            board.placeToken('X', j);
        }
        board.placeToken('O', 4);
        for (int i = 0, j = 1; j < 5; i+=2, j+=2) {
            if (i != 1) {
                board.placeToken('X', i);
                board.placeToken('O', j);
            }
        }
        board.placeToken('X', 4);
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 5; j++)
                boardArr[i][j] = board.whatsAtPos(i, j);
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.checkTie());
    }
    @Test
    public void testWhatsAtPos_Bottom_Left_0_0() {
        char[][] boardArr = new char[5][5];
        IGameBoard board = gameBoardFactory(5, 5, 3);
        board.placeToken('X', 0);
        boardArr[0][0] = 'X';
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.whatsAtPos(0, 0 ) == 'X');
    }
    @Test
    public void testWhatsAtPos_Bottom_Right() {
        char[][] boardArr = new char[5][5];
        IGameBoard board = gameBoardFactory(5, 5, 3);
        board.placeToken('X', 4);
        boardArr[0][4] = 'X';
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.whatsAtPos(0, 4 ) == 'X');
    }
    @Test
    public void testWhatsAtPos_Bottom_Middle() {
        char[][] boardArr = new char[5][5];
        IGameBoard board = gameBoardFactory(5, 5, 3);
        board.placeToken('X', 2);
        boardArr[0][2] = 'X';
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.whatsAtPos(0, 2 ) == 'X');
    }
    @Test
    public void testWhatsAtPos_Top_Right() {
        char[][] boardArr = new char[5][5];
        IGameBoard board = gameBoardFactory(5, 5, 3);
        for (int i = 0; i < 2; i++) {
            board.placeToken('X', 4);
            board.placeToken('O', 4);
        }
        board.placeToken('X', 4);
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                boardArr[i][j] = board.whatsAtPos(i, j);
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.whatsAtPos(4, 4 ) == 'X');
    }
    @Test
    public void testWhatsAtPos_Top_Left() {
        char[][] boardArr = new char[5][5];
        IGameBoard board = gameBoardFactory(5, 5, 3);
        for (int i = 0; i < 2; i++) {
            board.placeToken('X', 0);
            board.placeToken('O', 0);
        }
        board.placeToken('X', 0);
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                boardArr[i][j] = board.whatsAtPos(i, j);
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.whatsAtPos(4, 0 ) == 'X');
    }
    @Test
    public void testWhatsAtPos_Top_Middle() {
        char[][] boardArr = new char[5][5];
        IGameBoard board = gameBoardFactory(5, 5, 3);
        for (int i = 0; i < 2; i++) {
            board.placeToken('X', 2);
            board.placeToken('O', 2);
        }
        board.placeToken('X', 2);
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                boardArr[i][j] = board.whatsAtPos(i, j);
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.whatsAtPos(4, 2 ) == 'X');
    }
    @Test
    public void testWhatsAtPos_Middle_Of_Board_2_3() {
        char[][] boardArr = new char[6][6];
        IGameBoard board = gameBoardFactory(6, 6, 3);
        board.placeToken('X', 3);
        board.placeToken('O', 3);
        board.placeToken('X', 3);
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                boardArr[i][j] = board.whatsAtPos(i, j);
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.whatsAtPos(2, 3 ) == 'X');
    }
    @Test
    public void testWhatsAtPos_Side_Middle_Of_Board_2_0() {
        char[][] boardArr = new char[6][6];
        IGameBoard board = gameBoardFactory(6, 6, 3);
        board.placeToken('X', 0);
        board.placeToken('O', 0);
        board.placeToken('X', 0);
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                boardArr[i][j] = board.whatsAtPos(i, j);
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.whatsAtPos(2, 0 ) == 'X');
    }
    @Test
    public void testPlaceToken_Bottom_Right_X_9() {
        char[][] boardArr = new char[10][10];
        IGameBoard board = gameBoardFactory(10, 10, 3);
        board.placeToken('X', 9);
        boardArr[0][9] = 'X';
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.whatsAtPos(0, 9 ) == 'X');
    }
    @Test
    public void testPlaceToken_Bottom_Left_X_0() {
        char[][] boardArr = new char[10][10];
        IGameBoard board = gameBoardFactory(10, 10, 3);
        board.placeToken('X', 0);
        boardArr[0][0] = 'X';
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.whatsAtPos(0, 0 ) == 'X');
    }
    @Test
    public void testPlaceToken_Top_Right_O_9() {
        char[][] boardArr = new char[5][10];
        IGameBoard board = gameBoardFactory(5, 10, 6);
        for (int i = 0; i < 4; i++) {
            board.placeToken('O', 9);
            board.placeToken('X', 9);
        }
        board.placeToken('O', 9);
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 10; j++)
                boardArr[i][j] = board.whatsAtPos(i, j);
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.whatsAtPos(4, 9 ) == 'O');
    }
    @Test
    public void testPlaceToken_Top_Left_O_0() {
        char[][] boardArr = new char[5][10];
        IGameBoard board = gameBoardFactory(5, 10, 6);
        for (int i = 0; i < 4; i++) {
            board.placeToken('O', 0);
            board.placeToken('X', 0);
        }
        board.placeToken('O', 0);
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 10; j++)
                boardArr[i][j] = board.whatsAtPos(i, j);
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.whatsAtPos(4, 0 ) == 'O');
    }
    @Test
    public void testPlaceToken_Center() {
        char[][] boardArr = new char[10][10];
        IGameBoard board = gameBoardFactory(10, 10, 6);
        for (int i = 0; i < 5; i++) {
            board.placeToken('O', 5);
            board.placeToken('X', 4);
        }
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                boardArr[i][j] = board.whatsAtPos(i, j);
        assertEquals(board.toString(), boardToString(boardArr));
        assertTrue(board.whatsAtPos(4, 5 ) == 'O');
    }
    @Test
    public void testConstructor_Row_Number_10_10_10() {
        char[][] boardArr = new char[10][10];
        IGameBoard board = gameBoardFactory(10, 10, 10);
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                boardArr[i][j] = board.whatsAtPos(i, j);
        assertEquals(boardToString(boardArr), board.toString());
        assertEquals (board.getNumRows(), 9);
    }
    @Test
    public void testConstructor_Column_Number_12_8_10() {
        char[][] boardArr = new char[12][8];
        IGameBoard board = gameBoardFactory(12, 8, 10);
        for (int i = 0; i < 12; i++)
            for (int j = 0; j < 8; j++)
                boardArr[i][j] = board.whatsAtPos(i, j);
        assertEquals(boardToString(boardArr), board.toString());
        assertEquals (board.getNumColumns(), 7);
    }
    @Test
    public void testConstructor_Win_Number_6_6_4() {
        char[][] boardArr = new char[6][6];
        IGameBoard board = gameBoardFactory(6, 6, 4);
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 6; j++)
                boardArr[i][j] = board.whatsAtPos(i, j);
        assertEquals(boardToString(boardArr), board.toString());
        assertEquals (board.getNumToWin(), 4);
    }
}
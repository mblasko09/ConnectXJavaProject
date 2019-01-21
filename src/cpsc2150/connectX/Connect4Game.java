//Mark Blasko
//CPSC 2150
package cpsc2150.connectX;
import java.util.Scanner;

public class Connect4Game {

    public static void main(String[] args) {
        IGameBoard board = new GameBoard(0, 0, 0);
        int MIN_PLAYERS = 2;
        int MAX_PLAYERS = 10;
        Scanner scan = new Scanner(System.in);
        int choice;
        int turn = 0;
        char player;
        int rows = 0;
        int cols = 0;
        int winNumber = 0;
        int num_players = 0;
        char[] players = null;
        int playerCount = 0;
        String gameType = "";

        while (true) {
            if (turn == 0) {
                num_players = 0;
                playerCount = 0;
                gameType = "";
            }
            while (num_players < MIN_PLAYERS || num_players > MAX_PLAYERS) {
                System.out.println("How many players");
                num_players = scan.nextInt();
                if (num_players > MAX_PLAYERS)
                    System.out.println("Must be 10 players or fewer");
                else if (num_players < MIN_PLAYERS)
                    System.out.println("Must be at least 2 players");
                else
                    players = new char[num_players];
            }

            while (playerCount < num_players) {
                boolean is_present = false;
                System.out.println("Enter the character to represent player " + (playerCount+1));
                char player_char = scan.next().charAt(0);
                for (int i = 0; i < players.length; i++)
                    if (players[i] == Character.toUpperCase(player_char))
                        is_present = true;
                if (is_present)
                    System.out.println(player_char + " is already taken as a player token!");
                else {
                    players[playerCount] = Character.toUpperCase(player_char);
                    playerCount++;
                }
            }

            while (rows < board.DIM_MIN || rows > board.DIM_MAX) {
                System.out.println("How many rows should be on the board?");
                rows = scan.nextInt();
                if (rows > board.DIM_MAX)
                    System.out.println("Can have at most 100 rows");
                else if (rows < board.DIM_MIN)
                    System.out.println("Must have at least 3 rows.");
            }

            while (cols < board.DIM_MIN || cols > board.DIM_MAX) {
                System.out.println("How many columns should be on the board?");
                cols = scan.nextInt();
                if (cols > board.DIM_MAX)
                    System.out.println("Can have at most 100 cols");
                else if (cols < board.DIM_MIN)
                    System.out.println("Must have at least 3 cols.");
            }

            while (winNumber < board.DIM_MIN || winNumber > board.MAX_WIN) {
                System.out.println("How many in a row to win?");
                winNumber = scan.nextInt();
                if (winNumber < board.DIM_MIN)
                    System.out.println("Must have at least 3 in a row to win.");
                else if (winNumber > board.MAX_WIN)
                    System.out.println("Can have at most 25 in a row to win");
            }

            while (!(gameType.equalsIgnoreCase("F") || gameType.equalsIgnoreCase("M"))) {
                System.out.println("Would you like a Fast Game (F/f) or a Memory Efficient Game (M/m)?");
                gameType = scan.next();
                if (!(gameType.equalsIgnoreCase("F") || gameType.equalsIgnoreCase("M")))
                    System.out.println("Please enter F or M");
            }

            if (turn == 0) {
                if (gameType.equalsIgnoreCase("M"))
                   board = new GameBoardMem(rows, cols, winNumber);
               else
                   board = new GameBoard(rows, cols, winNumber);
            }

            System.out.println(board.toString());
            player = players[turn%players.length];

            do {
                System.out.println("Player " + player + " what column do you want to place your marker in?");
                choice = scan.nextInt();
                if (choice > board.getNumColumns())
                    System.out.println("Choice can not be greater than " + board.getNumColumns());
                else if (choice < 0)
                    System.out.println("Choice can not be less than 0");
                else if (!board.checkIfFree(choice))
                    System.out.println("Column is full");
            } while (choice > board.getNumColumns() || choice < 0 || !board.checkIfFree(choice));

            board.placeToken(player, choice);
            turn++;
            if (board.checkForWin(choice)) {
                System.out.println(board.toString());
                System.out.println("Player " + player + " won!");
                String play = "";
                while (!(play.equalsIgnoreCase("n") || play.equalsIgnoreCase("y"))) {
                    System.out.println("Would you like to play again? Y/N");
                    play = scan.next();
                    if (play.equalsIgnoreCase("n"))
                        return;
                    else {
                        rows = 0;
                        cols = 0;
                        winNumber = 0;
                        turn = 0;
                    }
                }
            }
            if (board.checkTie()) {
                System.out.println("Tie game! Would you like to play again? Y/N");
                String play = "";
                while (!(play.equalsIgnoreCase("n") || play.equalsIgnoreCase("y"))) {
                    System.out.println("Would you like to play again? Y/N");
                    play = scan.next();
                    if (play.equalsIgnoreCase("n"))
                        return;
                    else {
                        rows = 0;
                        cols = 0;
                        winNumber = 0;
                        turn = 0;
                    }
                }
            }
        }
    }
}

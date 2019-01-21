package cpsc2150.connectX;

/**
 * The controller class will handle communication between our View and our Model (IGameBoard)
 *
 * This is where you will write code
 *
 * You will need to include your IGameBoard interface
 * and both of the IGameBoard implementations from Homework 3
 * If your code was correct you will not need to make any changes to your IGameBoard implementation class
 */

public class ConnectXController {
    //our current game that is being played
    private IGameBoard curGame;


    //The screen that provides our view
    private ConnectXView screen;



    public static final int KEY = -1;
    //our play tokens are hard coded. We could make a screen to get those from the user, but
    //I want to keep this example simple
    private char[] players = {'X', 'O', 'Y', 'Z', 'A', 'K', 'E', 'J', 'N', 'H'};

    private int numPlayers, turn, prevCol;




    /**
     *
     * @param model the board implementation
     * @param view the screen that is shown
     * @post the controller will respond to actions on the view using the model.
     */
    ConnectXController(IGameBoard model, ConnectXView view, int np) {
        this.curGame = model;
        this.screen = view;
        numPlayers = np;
        turn = 0;
        prevCol = KEY;
    }

    /**
     *
     *
     * @param col the column of the activated button
     * @post will allow the player to place a token in the column if it is not full, otherwise it will display an error
     * and allow them to pick again. Will check for a win as well. If a player wins it will allow for them to play another
     * game hitting any button
     */
    public void processButtonClick(int col) {
        char curPlayer = players[turn%numPlayers];
        int i = 0;
        if ((prevCol != KEY && curGame.checkForWin(prevCol)) || curGame.checkTie()) newGame();
        while (curGame.whatsAtPos(i, col) != ' ' && i != curGame.getNumRows()) i++;
        if (curGame.checkIfFree(col)) {
            curGame.placeToken(curPlayer, col);
            screen.setMarker(i, col, curPlayer);
            screen.setMessage("It is " + players[(turn+1)%numPlayers] + "'s turn. ");
        }
        else screen.setMessage("Column is full!");
        if (curGame.checkForWin(col)) {
            screen.setMessage("Player " + curPlayer + " won! Click any button to play again.");
            prevCol = col;
        }
        if (curGame.checkTie())
            screen.setMessage("Game is a tie! Click any button to play again.");
        turn++;
    }

    /**
     * This method will start a new game by returning to the setup screen and controller
     */
    private void newGame()
    {
        //close the current screen
        screen.dispose();
        //start back at the set up menu
        SetupView screen = new SetupView();
        SetupController controller = new SetupController(screen);
        screen.registerObserver(controller);
    }
}

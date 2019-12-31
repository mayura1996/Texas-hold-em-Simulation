import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TexasHoldEm {
    public static int userdecision;
    public static int d;

    public static void main(String[] args) throws Exception {
        // declaring variables
        Deck TexasDeck = new Deck();
        int numPlayers = 3;

        Board board = new Board();

        // initializing the values
        Player[] player = new Player[numPlayers];

        // loopin for 10 rounds
        for (int q = 0; q < 10; q++) {
            System.out.println("\nRound :" + (q + 1));
            System.out.println();
            int cardCounter = 0;

            int burnCounter = 0;

            int count = 0;

            for (int i = 0; i < 3; i++) {
                TexasDeck.shuffle();
            }

            // Cutting the deck
            TexasDeck.cutDeck();

            // Initializing the players
            for (int i = 0; i < numPlayers; i++) {
                player[i] = new Player();
            }

            // dealing the carsd to the playuers
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < numPlayers; j++) {
                    player[j].setCard(TexasDeck.getCard(cardCounter++), i);
                }
            }

            System.out.println("\nThe user's cards are the following in round " + (q + 1));

            player[0].printCards(0);
            userdecision = getuserinput();

            // dealing the board

            // Burning one card before dealing

            board.setBurnCard(TexasDeck.getCard(cardCounter++), burnCounter++);

            // dealing the flop

            for (int i = 0; i < 3; i++) {
                board.setBoardCard(TexasDeck.getCard(cardCounter++), count++);
            }
            System.out.println("\nAfter flop round\n");

            board.printBoard(3);

            if (userdecision == 1) { // user decision 1 checks whether user prefers to continue
                userdecision = getuserinput();
            }
            // burning one card before the turn
            board.setBurnCard(TexasDeck.getCard(cardCounter++), burnCounter++);

            // dealing the turn

            board.setBoardCard(TexasDeck.getCard(cardCounter++), count++);
            System.out.println("\nAfter turn round\n");
            board.printBoard(4);
            if (userdecision == 1) {
                userdecision = getuserinput();
            }

            // burn one card before river stage
            board.setBurnCard(TexasDeck.getCard(cardCounter++), burnCounter++);

            // dealing the river
            board.setBoardCard(TexasDeck.getCard(cardCounter++), count++);

            // end dealing

            System.out.println("After being shuffled in round " + (q + 1));

            // printing the deck
            TexasDeck.printDeck();

            // print board
            System.out.println("\nAfter river round\n");
            board.printBoard(5);

            // printing the playeer cards
            System.out.println("\nThe player cards are the following :");
            if (userdecision != 0) {
                player[0].printCards(0);
            }

            for (int i = 1; i < numPlayers; i++) {
                player[i].printCards(i);
            }

            // print burn cards
            board.printBurnCards();
            if (userdecision != 0) {
                d = 0;

            } else {
                d = 1;
            }

            for (int i = d; i < numPlayers; i++) {
                Hand handtoCheck = new Hand();

                // dsitributing the players with cards
                for (int j = 0; j < player[i].cardSize(); j++) {
                    handtoCheck.insertCard(player[i].getCard(j), j);
                }

                // distributing the board with the cards
                for (int j = player[i].cardSize(); j < (player[i].cardSize() + board.boardSize()); j++) {
                    handtoCheck.insertCard(board.getCard(j - player[i].cardSize()), j);
                }

                System.out.println("Player " + (i + 1) + " hand value: " + handtoCheck.evaluateHand());

                player[i].addscore(handtoCheck.gethandval());

                System.out.println("Player " + (i + 1) + " score : " + player[i].getscore());
            }

            Thread.sleep(10);
        }

        int winner = 0;
        System.out.print("\nTHE WINNER IS : ");
        for (int t = 0; t < 3; t++) {
            if (player[t].getscore() > winner) {
                winner = t;
            }

        }
        if (winner == 0) {
            System.out.println("YOU");
        } else
            System.out.println("player " + (winner + 1));
    }

    protected static int getuserinput() throws Exception {
        int intPlayers = 0;
        String userInput = "";

        // Get the user deccision to fold or continue
        System.out.println("Enter user decision (fold (0)/ continue(1)):");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        if (intPlayers == 0) {
            userdecision = 0;
        }
        // error check
        try {
            userInput = br.readLine();
        } catch (IOException ioe) {
            System.out.println("Error: IO error trying to read input!");
            System.exit(1);
        }

        // converting user input to an integer
        try {
            intPlayers = Integer.parseInt(userInput);
        } catch (NumberFormatException nfe) {
            System.out.println("Error: Input provided is not a valid Integer!");
            System.exit(1);
        }

        if ((intPlayers < 0) || (intPlayers > 1)) {
            throw new Exception("Error:decision must be an integer  1 or 0");
        }

        return intPlayers;
    }
}
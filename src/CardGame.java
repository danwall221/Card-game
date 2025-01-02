import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Main class for the game of cards. This class initialises the game and
 * starts the game loop. It initilises the threads for the players.
 */
public class CardGame {
    public static void main(String[] args) {
        try {
            // Request input from command line.
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter the number of players (positive integer): ");
            int n = Integer.parseInt(reader.readLine()); // Read and parse the number of players
            System.out.println("Enter the filename: ");
            String filename = reader.readLine();
            intialiseGame(n, filename);

        } catch (IOException e) {
            System.out.println(e);
        } catch (NumberFormatException e) {
            System.out.println("Must enter a valid number of  players") ;
        }
    }

    /**
     * Initialises the game with the number of players and the filename.
     * @param n The number of players
     * @param filename The filename of the pack
     * @throws IOException If the file is not found
     */
    public static void intialiseGame(int n, String filename) throws IOException {
        Pack pack = new Pack(filename, n);
        Card[] cards = pack.getCards();
        CardDeck[] decks = new CardDeck[n];
        Player[] players = new Player[n];
        
        // initialise the players and decks
        for (int i = 0; i < n; i++) { 
            players[i] = new Player(i + 1); // Assign player number
            players[i].start(); // Start the player thread
            decks[i] = new CardDeck(i + 1); // Assign deck number
        }

        // Distribute the cards to the players
        for (int i=0; i < 4 * n; i++) {
            players[i % n].addCardToHand(cards[i]);
        }

        // Log the initial hand of each player
        for (Player player : players) {
            player.logInitialHand();
        }

        // Distribute the cards to the decks
        for (int i=4 * n; i < 8 * n; i++) {
            decks[i % n].addCardToDeck(cards[i]);
        }
        
        boolean isWinnerFound = false;

        // Check for a winner before any turns but after distribution 
        for (Player player : players) {
            if (player.hasWon()) {
                System.out.println("Game over. Player " + player.getPlayerNumber() + " has won!");
                notifyAllPlayers(players, player.getPlayerNumber());
                writeDeckContents(decks);
                return;
            }
        }

        // Counter for the number of turns
        int turns = 0;

        // Game loop
        while (!isWinnerFound) {

            //Index's
            int playersTurn = turns++ % n;
            int rightDeck = ((playersTurn + 1) % n);
            int leftDeck = ((playersTurn) % n);

            // Synchronise the players actions to avoid race conditions
            synchronized (players[playersTurn]) {
                decks[rightDeck].discardCard(players[playersTurn].takeTurn(decks[leftDeck].pickUpCard(), rightDeck, leftDeck));
            }

            // Check if the player has won
            if (players[playersTurn].hasWon()) {
                isWinnerFound = true;
                notifyAllPlayers(players, players[playersTurn].getPlayerNumber());
                writeDeckContents(decks);
            }
        }
    }

    /**
     * Writes the contents of the deck to a file.
     * @param decks
     */
    private static void writeDeckContents(CardDeck[] decks) {
        for (CardDeck deck : decks) {
            deck.writeDeckContentsToFile();
        }
    }

    /**
     * Notifies all players of the winner.
     * @param players The players
     * @param winner The winner
     */
    private static void notifyAllPlayers(Player[] players, int winner){
        for (Player player : players) {
            player.notifyGameEnd(winner);

        }
    }
}


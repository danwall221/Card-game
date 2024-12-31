import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CardGame {
    public static void main(String[] args) {
        try {
            // Request input from command line.
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter the number of players (positive integer): ");
            int n = Integer.parseInt(reader.readLine());
            System.out.println("Enter the filename: ");
            String filename = reader.readLine();
            intialiseGame(n, filename);

        } catch (IOException e) {
            System.out.println(e);
        } catch (NumberFormatException e) {
            System.out.println("Must enter a valid number of  players") ;
        }
    }

    public static void intialiseGame(int n, String filename) throws IOException {
        Pack pack = new Pack(filename, n);
        Card[] cards = pack.getCards();
        CardDeck[] decks = new CardDeck[n];
        Player[] players = new Player[n];
        
        for (int i = 0; i < n; i++) {
            players[i] = new Player(i + 1);
            players[i].start();
            decks[i] = new CardDeck(i + 1);
        }

        for (int i=0; i < 4 * n; i++) {
            players[i % n].addCardToHand(cards[i]);
        }

        for (Player player : players) {
            player.logInitialHand();
        }

        for (int i=4 * n; i < 8 * n; i++) {
            decks[i % n].addCardToDeck(cards[i]);
        }
        
        boolean isWinnerFound = false;

        for (Player player : players) {
            if (player.hasWon()) {
                System.out.println("Game over. Player " + player.getPlayerNumber() + " has won!");
                notifyAllPlayers(players, player.getPlayerNumber());
                writeDeckContents(decks);
                return;
            }
        }

        int turns = 0;

        while (!isWinnerFound) {
            int playersTurn = turns++ % n;
            int rightDeck = (playersTurn + 1) % n;
            int leftDeck = (playersTurn) % n;

            synchronized (players[playersTurn]) {
                decks[rightDeck].discardCard(players[playersTurn].takeTurn(decks[leftDeck].pickUpCard(), rightDeck, leftDeck));
            }

            if (players[playersTurn].hasWon()) {
                isWinnerFound = true;
                notifyAllPlayers(players, players[playersTurn].getPlayerNumber());
                writeDeckContents(decks);
            }
        }
    }

    private static void writeDeckContents(CardDeck[] decks) {
        for (CardDeck deck : decks) {
            deck.writeDeckContentsToFile();
        }
    }

    private static void notifyAllPlayers(Player[] players, int winner){
        for (Player player : players) {
            player.notifyGameEnd(winner);

        }
    }
}


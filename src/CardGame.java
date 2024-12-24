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

    public static void intialiseGame(int n, String filename) {
        Pack pack = new Pack(filename, n);
        Card[] cards = pack.getCards();
        CardDeck[] decks = new CardDeck[n];
        Player[] players = new Player[n];
        
        for (int i = 0; i < n; i++) {
            players[i-1] = new Player(i);
            players[i-1].start();
            decks[i] = new CardDeck(i);
        }

        for (int i=0; i < 4 * n; i++) {
            players[i % n].addCardToHand(cards[i]);
        }

        for (int i=4 * n; i < 8 * n; i++) {
            decks[i % n].addCardToDeck(cards[i]);
        }
        
        
        for (Player player : players) {
            if (player.hasWon()) {
                break;
            }
        }
    }
}

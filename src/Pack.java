import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Pack {
    
    private Card[] cards;
    
    /**
     * Initialises the pack with the filename and the number of players.
     * @param filename The filename of the pack
     * @param n The number of players
     * @throws IOException If the file is not found
     */
    public Pack(String filename, int n) throws IOException {
        this.cards = new Card[8 * n]; // 8 cards per player
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filename)))) {
            String newline;
            int counter = 0; // Number of cards read
            int expectedCards = 8 * n;

            // Read the file line by line to get values
            while ((newline = bufferedReader.readLine()) != null) {
                if (counter == 8 * n) {
                    throw new IOException("Too many cards in pack. Expected " + expectedCards + " cards for " + n + " players.");
                } else {
                    // convert the line to an integer and create a new card
                    this.cards[counter++] = new Card(Integer.parseInt(newline));// Read and parse the number of players
                }
            }
            int numberOfCards = counter;
            if (numberOfCards < expectedCards) {
                throw new IOException("Too few cards in pack. Cards needed: " + expectedCards + ". Cards received: " + numberOfCards);
            }
        } catch (NumberFormatException e) {
            throw new IOException("Pack file can only contain integers", e);
        }
    }

    /**
     * Gets the cards in the pack.
     * @return The cards in the pack
     */
    public Card[] getCards() {
        return cards;
    }
}


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Pack {
    
    private Card[] cards;
    
    
    public Pack(String filename, int n) throws IOException {
        this.cards = new Card[8 * n];
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filename)))) {
            String newline;
            int index = 0;
            int expectedCards = 8 * n;
            while ((newline = bufferedReader.readLine()) != null) {
                if (index == 8 * n) {
                    throw new IOException("Too many cards in pack. Expected " + expectedCards + " cards for " + n + " players.");
                } else {
                    this.cards[index++] = new Card(Integer.parseInt(newline));
                }
            }
            int numberOfCards = index;
            if (numberOfCards < expectedCards) {
                throw new IOException("Too few cards in pack. Cards needed: " + expectedCards + ". Cards received: " + numberOfCards);
            }
        } catch (NumberFormatException e) {
            throw new IOException("Pack file can only contain integers", e);
        }
    }

    public Card[] getCards() {
        return cards;
    }
}


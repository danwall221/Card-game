import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Pack {
    
    private Card[] cards;
    
    
         public Pack(String filename, int n) throws IOException {
        this.cards = new Card[8 * n];
        try {
            File packFile = new File(filename);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(packFile));
            String newline;
            int arrayIndex = 0;
            int expectedNumberOfCards = 8 * n;
            while ((newline = bufferedReader.readLine()) != null) {
                if (arrayIndex == 8 * n) {
                    System.out.println("Too many cards in pack. \n" +
                            "Expected " + expectedNumberOfCards + " cards for " + n + " players");
                    System.exit(1);
                } else {
                    this.cards[arrayIndex++] = new Card((short) Integer.parseInt(newline));
                }
            }
            int numberOfCards = arrayIndex;
            if (numberOfCards < expectedNumberOfCards) {
                // Error handling for pack size.
                System.out.println("Too few cards in pack\n" +
                        "Found " + numberOfCards + " cards for " + n + " players. Expected " + expectedNumberOfCards);
                System.exit(1);
            }
        } catch (NumberFormatException e) {
            // if Integer.parseInt fails
            System.out.println("The pack file of cards does not contain only integers");
        }
    }


        public Card[] getCards() {
            return cards;
        }
    
        public void setCards(Card[] cards) {
            this.cards = cards;
    }

}


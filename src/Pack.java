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
                    System.out.println("Too many cards in pack. \n" +
                            "Expected " + expectedCards + " cards for " + n + " players");
                    System.exit(1);
                } else {
                    this.cards[index++] = new Card(Integer.parseInt(newline));
                }
            }
            int numberOfCards = index;
            if (numberOfCards < expectedCards) {
                System.out.println("Too few cards in pack\n" +
                        "Cards needed: " + expectedCards + ". Cards received: " + numberOfCards);
                System.exit(1);
            }
        } catch (NumberFormatException e) {
            System.out.println("Pack file can only contain only integers");
        }
    }

        public Card[] getCards() {
            return cards;
        }
    
        public void setCards(Card[] cards) {
            this.cards = cards;
    }

}


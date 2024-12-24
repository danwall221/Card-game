import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Pack {
    
    private Card[] cards;
    
        public Pack(String filename, int n) {
            this.cards = new Card[8 * n];
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                int index = 0;
                while ((line = br.readLine()) != null && index < this.cards.length) {
                    short cardValue = Short.parseShort(line.trim());
                    this.cards[index++] = new Card(cardValue);
                    }
                }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        public Card[] getCards() {
            return cards;
        }
    
        public void setCards(Card[] cards) {
            this.cards = cards;
    }

}


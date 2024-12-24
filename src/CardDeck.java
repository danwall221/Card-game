import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CardDeck {
    private int deckNumber;
    private Card[] hand;
    private int cards;
        
                public CardDeck(int deckNumber){
                    this.deckNumber = deckNumber;
            }
        
            public int getDeckNumber() {
                return deckNumber;
            }
        
            public void setDeckNumber(short deckNumber) {
                this.deckNumber = deckNumber;
            }
        
            public void addCardToDeck(Card card) {
                this.hand[cards++] = card;
            }
            
            public void discardCard(Card card) {
                if (this.hand[3] == null) 
                    this.hand[3] = card;
                else this.hand[4] = card;
            }

            public Card pickUpCard() {
                Card topOfDeck = this.hand[0];
                Card[] newHand = new Card[5];
                System.arraycopy(this.hand, 1, newHand, 0, 4);
                this.hand = newHand;
                return topOfDeck;
            }

            public void writeContentsToFile(String playerName, String action) {
                String filename = playerName + "_output.txt";
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
                    writer.write(action);
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
             }
}


}

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Represents a Card deck in between players. Also contains 
 * methods the deck can perform.
 */
public class CardDeck {
    private int deckNumber;
    private Card[] hand;
    private int cards;
        
    /**
     * Initialises the CardDeck class. Starts as Empty.
     * @param deckNumber The ID of the deck.
     */
    public CardDeck(int deckNumber){
        this.deckNumber = deckNumber;
        this.hand = new Card[5];
        this.cards = 0;
    }
        
    /**
     * Gets the deck number.
     * @return The deck number.
     */
    public int getDeckNumber() {
        return deckNumber;
    }

    /**
     * Adds card to the deck
     * @return Updated hand with card added
     */
    public void addCardToDeck(Card card) {
        this.hand[cards++] = card;
    }
            
    /**
     * Discards a card from the deck
     * @param card The card to discard
     */
    public void discardCard(Card card) {
        if (this.hand[3] == null) 
            this.hand[3] = card;
        else this.hand[4] = card;
    }

    /**
     * Chooses a card at the top of the deck to give to the Player.
     * @return The card to be picked up
     */
    public Card pickUpCard() {
        Card topOfDeck = this.hand[0];
        Card[] newHand = new Card[5];
        System.arraycopy(this.hand, 1, newHand, 0, 4);
        this.hand = newHand;
        return topOfDeck;
    }

    /**
     * Logs the deck contents in a file.
     */
    public void writeDeckContentsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("deck" + deckNumber + "_output.txt"))) {
            writer.write("Deck " + deckNumber + " contents: " + deckToString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        
    /**
     * Converts the deck to a string.
     * @return The deck as a string
     */
    public String deckToString() {
        StringBuilder string = new StringBuilder();
        for (Card card : hand) {
            if (card != null) string.append(card.getValue()).append(" ");
        }
        return string.toString().trim();
    }
}




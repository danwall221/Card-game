/**
 * Card class represents a card in the game. Each card has a value and 
 * that is the only attribute of the card.
 */
public class Card {
    
    private final int value;

    public Card(int cardValue) {
        this.value = cardValue;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return "" + value;
    }
    
}
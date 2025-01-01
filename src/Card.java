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
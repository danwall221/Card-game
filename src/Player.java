import java.io.File;

public class Player extends Thread {
    private Card[] hand;
    private int cards;
    private int playerNumber;
                
    public Player(int playerNumber) {
        this.hand = new Card[5];
        this.cards = 0;
        this.playerNumber = playerNumber;
    }

    public Card[] getHand() {
        return hand;
    }
    
    public int getCards() {
        return cards;
    }
    
    public int getPlayerNumber() {
        return playerNumber;
    }

    public void addCardToHand(Card card) {
        hand[cards++] = card;
    }
    
    public boolean hasWon() {
        Card firstCard = this.hand[0];
        int counter = 0;
        for (Card card : this.hand) {
            if (counter++ == 4) break;
            if (card.getValue() != firstCard.getValue()) return false;
        }
        System.out.printf("Player %d has won%n", playerNumber);
        return true;
    }
}    

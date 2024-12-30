import java.util.Random;

import java.io.File;

public class Player extends Thread {
    private Card[] hand;
    private int cards;
    private int playerNumber;
    private Integer preferredCard;
                    
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
    
        public Card takeTurn(Card topOfDeck, int rightDeck, int leftDeck) {
            int discardIndex = findCardToDiscardIndex();
            Card cardToDiscard = this.hand[discardIndex];
            this.hand[discardIndex] = topOfDeck;
            return cardToDiscard;
        }
            

    
        @SuppressWarnings("null")
        private int findCardToDiscardIndex() {
            Random choose = new Random();
            int indexToCheck;
            boolean preferredCard = true;

            // Loop until a non-preferred card is found
            while (preferredCard) {
                indexToCheck = choose.nextInt(4); // Randomly pick an index from the hand
                if (this.hand[indexToCheck].getValue() != playerNumber) { // Check if it's not the preferred card
                    preferredCard = false;
                    return indexToCheck; // Return the index if it's not preferred
        }
    }
            return (Integer) null; // Return null if no card is found (should not happen with valid input)
        }

        public void discardCard(Card card) {
            for (int i = 0; i < hand.length; i++) {
                if (hand[i] != null && hand[i].equals(card)) {
                    hand[i] = null;
                    // Shift remaining cards to the left
                    for (int j = i; j < hand.length - 1; j++) {
                        hand[j] = hand[j + 1];
                    }
                    hand[hand.length - 1] = null; // Set the last card to null
                    cards--; // Decrease the card count
                    break;
                }
            }
        }

}    

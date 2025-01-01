import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Player extends Thread {
    private Card[] hand;
    private int cards;
    private int playerNumber;
    private BufferedWriter writer;

    public Player(int playerNumber) {
                this.hand = new Card[5];
                this.cards = 0;
                this.playerNumber = playerNumber;
                try {
                    // Create or overwrite the player's output file
                    writer = new BufferedWriter(new FileWriter("player" + playerNumber + "_output.txt"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
            for (Card card :
                    this.hand) {
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

            logAction("Player " + playerNumber + " draws " + topOfDeck + " from deck " + (leftDeck + 1));
            logAction("Player " + playerNumber + " discards " + cardToDiscard + " to deck " + (rightDeck + 1));
            logAction("Player " + playerNumber + " current hand: " + handToString());

            return cardToDiscard;
        }
            

    
        private int findCardToDiscardIndex() {
            Random choose = new Random();
            int indexToCheck;
            // Loop until a non-preferred card is found
            while (true) {
                indexToCheck = choose.nextInt(4); // Randomly pick an index from the hand
                if (this.hand[indexToCheck].getValue() != playerNumber) { // Check if it's not the preferred card
                    return indexToCheck; // Return the index if it's not preferred
                }
            } 
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

        private String handToString() {
            StringBuilder string = new StringBuilder();
            for (Card card : hand) {
                if (card != null) string.append(card.getValue()).append(" ");
            }
            return string.toString().trim();
        }

        private void logAction(String action) {
            try {
                writer.write(action);
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        public void closeOutputFile() {
            try {
                writer.write("Player " + playerNumber + " final hand: " + handToString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void logInitialHand() {
            logAction("player " + playerNumber + " initial hand: " + handToString());
        }

        public void notifyGameEnd(int winner) {
            logAction("player " + winner + " wins");
            logAction("player " + playerNumber + " exits.");
            closeOutputFile();
        }
}    

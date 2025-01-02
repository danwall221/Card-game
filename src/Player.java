import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Represents a player in the card game. Each player operates as an independent thread, 
 * allowing simultaneous actions like drawing and discarding cards.
 */
public class Player extends Thread {
    private Card[] hand;
    private int cards;
    private int playerNumber;
    private BufferedWriter writer;

    /**
     * Initialises the player with the player number. Creates a file to log the player's actions.
     * 
     * @param playerNumber The player number
     */
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
        
    /**
     * Gets the player's hand.
     * 
     * @return The player's hand
     */
    public Card[] getHand() {
            return hand;
    }
           
    /**
     * Gets the number of cards in the player's hand.
     * 
     * @return The number of cards in the player's hand
     */
    public int getCards() {
        return cards;
    }
      
    /**
     * Gets the player number.
     * 
     * @return The player number
     */
    public int getPlayerNumber() {
        return playerNumber;
    }
        
    /**
     * Adds a card to the player's hand.
     * 
     * @param card The card to add
     */
    public void addCardToHand(Card card) {
        hand[cards++] = card;
    }
            
    /**
     * Checks if the player wins, i.e., has all cards of the same value.
     * 
     * @return True if the player has won, false otherwise
     */
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

    /**
     * Takes a player's turn. The player draws a card, discards one, 
     * and logs their actions. The turn is synchronized to prevent race conditions.
     *
     * @param topOfDeck  The card drawn from the left deck.
     * @param rightDeck  The number of the deck to which the discarded card is added.
     * @param leftDeck   The number of the deck from which the drawn card came.
     * @return The card that the player discards to the right deck.
     */
    public Card takeTurn(Card topOfDeck, int rightDeck, int leftDeck) {
        int discardIndex = findCardToDiscardIndex();
        Card cardToDiscard = this.hand[discardIndex];
        this.hand[discardIndex] = topOfDeck;

        logAction("Player " + playerNumber + " draws " + topOfDeck + " from deck " + (leftDeck + 1));
        logAction("Player " + playerNumber + " discards " + cardToDiscard + " to deck " + (rightDeck + 1));
        logAction("Player " + playerNumber + " current hand: " + handToString());

        return cardToDiscard;
    }
            
    /**
     * Finds a card in the hand to discard, preferring non-preferred cards.
     * A non-preferred card is one that does not match the player's number.
     *
     * @return The index of the card to discard.
     */
    public int findCardToDiscardIndex() {
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

    /**
     * Converts the hand to a string.
     * 
     * @return The hand as a string seperated by spaces
     */
    private String handToString() {
        StringBuilder string = new StringBuilder();
        for (Card card : hand) {
            if (card != null) string.append(card.getValue()).append(" ");
        }
        return string.toString().trim();
    }

    /**
     * Logs an action to the player's output file.
     * 
     * @param action The string to log
     */
    private void logAction(String action) {
        try {
            writer.write(action);
            writer.newLine();
            writer.flush(); // Flush the buffer to write the action immediately, this is important for testing
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        
    /**
     * Writes the final hand to the player's output file and closes the file.
     */
    public void closeOutputFile() {
        try {
            writer.write("Player " + playerNumber + " final hand: " + handToString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * logs the players hand directly after distribution.
     */
    public void logInitialHand() {
        logAction("player " + playerNumber + " initial hand: " + handToString());
    }

    /**
     * Notifies the player that the game has ended and whp the winner is .
     * 
     * @param winner The player number of the winner
     */
    public void notifyGameEnd(int winner) {
        logAction("player " + winner + " wins");
        logAction("player " + playerNumber + " exits.");
        closeOutputFile();
    }
}    

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Player class.
 */
public class PlayerTest {

    /**
     * Test case for the player number
     */
    @Test
    public void testPlayerNumber() {
        var player = new Player(1);
        assertEquals(1, player.getPlayerNumber());
    }

    /**
     * Tests adding cards to the player's hand.
     * Verifies that cards are correctly added and the card count is updated.
     */
    @Test
    public void testAddCardToHand() {
        var player = new Player(1);
        player.addCardToHand(new Card(1));
        player.addCardToHand(new Card(2));

        assertEquals(2, player.getCards());
        assertEquals(1, player.getHand()[0].getValue());
        assertEquals(2, player.getHand()[1].getValue());
    }

    /**
     * Test to make sure that when a player has four cards of the same hand 
     * they definitely win
     */
    @Test
    public void testHasWon() {
        var player = new Player(1);

        player.addCardToHand(new Card(1));
        player.addCardToHand(new Card(1));
        player.addCardToHand(new Card(1));
        player.addCardToHand(new Card(1));
        assertTrue(player.hasWon());
    }

    /**
     * Test to make sure that hasWon() returns flase
     * when there is not 4 cards of the same denomination
     */
    @Test
    public void testHasNotWon() {
        var player = new Player(1);

        player.addCardToHand(new Card(1));
        player.addCardToHand(new Card(2));
        player.addCardToHand(new Card(1));
        player.addCardToHand(new Card(1));
        assertFalse(player.hasWon());
    }

    /**
     * Tests the takeTurn method, making sure that a card is drawn, a card is discarded,
     * and the player's hand is all updated correctly.
     */
    @Test
    public void testTakeTurn() {
        var player = new Player(1);
        
        player.addCardToHand(new Card(1));
        player.addCardToHand(new Card(2));
        player.addCardToHand(new Card(1));
        player.addCardToHand(new Card(1));

        Card newCard = new Card(5);
        player.takeTurn(newCard, 2, 1);

        assertEquals(4, player.getCards());
        assertTrue(containsCard(player.getHand(), 5));
        assertFalse(containsCard(player.getHand(), 2));
    }

    /**
     * Tests logging the initial hand to the output file.
     * Verifies that the log file contains the correct initial hand string.
     */
    @Test
    void testLogActions() throws IOException {
        var player = new Player(1);         

        player.addCardToHand(new Card(1));
        player.addCardToHand(new Card(2));
        player.logInitialHand();

        // Verify the contents of the log file
        File file = new File("player1_output.txt");
        assertTrue(file.exists(), "Log file does not exist.");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String initialHandLog = reader.readLine();
            assertEquals("player 1 initial hand: 1 2", initialHandLog);
        }
    }

    /**
     * Tests notifyGameEnd() to ensure the correct winner and exit messages
     * are logged in the file
     */
    @Test
    void testNotifyGameEnd() throws IOException {
        var player = new Player(1);

        player.addCardToHand(new Card(1));
        player.addCardToHand(new Card(2));
        player.notifyGameEnd(2);

        // Verify the contents of the log file
        File file = new File("player1_output.txt");
        assertTrue(file.exists(), "Log file does not exist.");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String winnerLog = reader.readLine();
            String exitLog = reader.readLine();
            assertEquals("player 2 wins", winnerLog);
            assertEquals("player 1 exits.", exitLog);
        }
    }

    /**
     * Helper method to help testing.
     * Checks if a specific card value exists in the player's hand.
     *
     * @param hand  The player's hand.
     * @param value The value to search for.
     * @return True if the card is found, false otherwise.
     */
    private boolean containsCard(Card[] hand, int value) {
        for (Card card : hand) {
            if (card != null && card.getValue() == value) {
                return true;
            }
        }
        return false;
    }
}
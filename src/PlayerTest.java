
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {

    @Test
    public void testPlayerNumber() {
        var player = new Player(1);
        assertEquals(1, player.getPlayerNumber());
    }

    @Test
    public void testAddCardToHand() {
        var player = new Player(1);
        player.addCardToHand(new Card(1));
        player.addCardToHand(new Card(2));

        assertEquals(2, player.getCards());
        assertEquals(1, player.getHand()[0].getValue());
        assertEquals(2, player.getHand()[1].getValue());
    }

    @Test
    public void testHasWon() {
        var player = new Player(1);

        player.addCardToHand(new Card(1));
        player.addCardToHand(new Card(1));
        player.addCardToHand(new Card(1));
        player.addCardToHand(new Card(1));
        assertTrue(player.hasWon());
    }

    @Test
    public void testHasNotWon() {
        var player = new Player(1);

        player.addCardToHand(new Card(1));
        player.addCardToHand(new Card(2));
        player.addCardToHand(new Card(1));
        player.addCardToHand(new Card(1));
        assertFalse(player.hasWon());
    }

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

    @Test
    public void testLogActions() throws IOException {
        var player = new Player(1);
    
        player.addCardToHand(new Card(1));
        player.addCardToHand(new Card(2));

        player.logInitialHand();
        
        File file = new File("player1_output.txt");
        assertTrue(file.exists(), "Log file does not exist.");

        

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String initialHandLog = reader.readLine();
            assertEquals("player 1 initial hand: 1 2 ", initialHandLog);
        }
    }

    private boolean containsCard(Card[] hand, int value) {
        for (Card card : hand) {
            if (card != null && card.getValue() == value) {
                return true;
            }
        }
        return false;
    }
}
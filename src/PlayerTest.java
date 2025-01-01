
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {

    private File logFile;

    @BeforeEach
    void setUp() {
        logFile = new File("player1_output.txt");
    }

    @AfterEach
    void tearDown() {
        if (logFile.exists()) {
            logFile.delete();
        }
    }


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



    private boolean containsCard(Card[] hand, int value) {
        for (Card card : hand) {
            if (card != null && card.getValue() == value) {
                return true;
            }
        }
        return false;
    }
}
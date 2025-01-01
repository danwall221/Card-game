import org.junit.Before;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CardDeckTest {

    private CardDeck tempDeck;
    
    @Before
    private CardDeck createTempDeck(int deckNumber) {
        var tempDeck = new CardDeck(deckNumber);
        tempDeck.addCardToDeck(new Card(1));
        tempDeck.addCardToDeck(new Card(2));
        tempDeck.addCardToDeck(new Card(3));
        tempDeck.addCardToDeck(new Card(4));
        return tempDeck;
    }
    
    @Test
    public void testAddCard() {
        // Create one CardDeck and 5 Card's
        var addCardDeck = new CardDeck( 1);
        addCardDeck.addCardToDeck(new Card( 5));
    }
    
    @Test
    public void testFirstDeck() {
    tempDeck = createTempDeck(1);

    assertEquals(1, tempDeck.pickUpCard().getValue());
    tempDeck.discardCard(new Card( 5));
    assertEquals(2, tempDeck.pickUpCard().getValue());
    assertEquals(3, tempDeck.pickUpCard().getValue());
    assertEquals(4, tempDeck.pickUpCard().getValue());
    assertEquals(5, tempDeck.pickUpCard().getValue());
    }

    @Test
    public void testOtherDecks() {
        tempDeck = createTempDeck(1);
        
        tempDeck.discardCard(new Card( 5));
        assertEquals(1, tempDeck.pickUpCard().getValue());
        assertEquals(2, tempDeck.pickUpCard().getValue());
        assertEquals(3, tempDeck.pickUpCard().getValue());
        assertEquals(4, tempDeck.pickUpCard().getValue());
        assertEquals(5, tempDeck.pickUpCard().getValue());
    }

    @Test
    public void testDeckToString() {
        tempDeck = createTempDeck(1);
        assertEquals("1 2 3 4", tempDeck.deckToString());
    }

    @Test
    public void testWriteDeckContentsToFile() {
        tempDeck = createTempDeck(1);

        tempDeck.writeDeckContentsToFile();

        // Define the expected file name and expected contents
        String expectedFileName = "deck" + tempDeck.getDeckNumber() + "_output.txt";
        String expectedContents = "Deck 1 contents: 1 2 3 4";

        // Verify the file exists
        var file = new File(expectedFileName);
        assertTrue(file.exists(), "The output file was not created.");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String actualContents = reader.readLine();
            assertEquals(expectedContents, actualContents, "The file contents do not match the expected output.");
        } catch (IOException e) {
            fail("Failed to read the output file: " + e.getMessage());
        } finally {
            if (!file.delete()) {
                System.err.println("Failed to delete test output file: " + expectedFileName);
            }
        }
    }
}

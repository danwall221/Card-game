import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Card class. 
 */
public class CardTest {

    @Test 
    public void testCardValue() {
        Card card = new Card(5);
        assertEquals(5, card.getValue());
    }

    @Test
    public void testCardToString() {
        Card card = new Card(5);
        assertEquals("5", card.toString());
    }
}

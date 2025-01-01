import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

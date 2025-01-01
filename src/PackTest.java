import org.junit.Before;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PackTest {

    @Before
    private String createTempFile(String content) throws IOException {
        String tempFileName = "temp_pack.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFileName))) {
            writer.write(content);
        }
        return tempFileName;
    }

    @Test
    void testValidPack() {
        final String content = "1\n2\n3\n4\n5\n6\n7\n8\n"; // 8 cards for 1 player
        assertDoesNotThrow(() -> new Pack(createTempFile(content), 1));
    }

    @Test
    void testTooFewCards() {
        final String content = "1\n2\n3\n4\n"; // 4 cards instead of 8 for 1 player
        IOException exception = assertThrows(IOException.class, () -> new Pack(createTempFile(content), 1));
        assertEquals("Too few cards in pack. Cards needed: 8. Cards received: 4", exception.getMessage());
    }

    @Test
    void testTooManyCards() {
        final String content = "1\n2\n3\n4\n5\n6\n7\n8\n9\n"; // 9 cards instead of 8 for 1 player
        IOException exception = assertThrows(IOException.class, () -> new Pack(createTempFile(content), 1));
        assertEquals("Too many cards in pack. Expected 8 cards for 1 players.", exception.getMessage());
    }

    @Test
    void testNonIntegerInput() {
        final String content = "1\n2\nthree\n4\n"; // "three" is not an integer
        IOException exception = assertThrows(IOException.class, () -> new Pack(createTempFile(content), 1));
        assertEquals("Pack file can only contain integers", exception.getMessage());
    }
}
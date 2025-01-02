import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CardGameTest {

    @Test
    public void testPlayCardGame() {
        try {
            int numberOfPlayers = (int) (Math.random() * 100) + 1;

            // Create a test pack file dynamically
            String testPackFilePath = "testCardGamePack.txt";
            File testPackFile = new File(testPackFilePath);
            if (!testPackFile.exists() && !testPackFile.createNewFile()) {
                throw new IOException("Failed to create a new test pack file.");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(testPackFile))) {
                for (int i = 1; i <= numberOfPlayers; i++) {
                    for (int j = 0; j < 8; j++) {
                        writer.write(i + "\n");
                    }
                }
            }
            try {
                CardGame.intialiseGame(numberOfPlayers, testPackFilePath);
            } catch (IOException e) {
                fail("The game failed to run with the generated test pack file: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("The test failed to create a valid test pack file: " + e.getMessage());
            }
    }
}
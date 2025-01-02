import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the CardGame class.
 */
public class CardGameTest {

    @Test
    public void testPlayCardGame() {
        String packFilePath = null;
    try {
        // Generate a random number of players (at least 1, up to 100)
        int playerCount = (int) (Math.random() * 100) + 1;

        // Create and prepare a test pack file
        packFilePath = "generatedPackFile.txt";
        File packFile = new File(packFilePath);
        if (!packFile.exists() && !packFile.createNewFile()) {
            throw new IOException("Unable to generate a new test pack file.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(packFile))) {
            // Write card values to the file for each player
            for (int player = 1; player <= playerCount; player++) {
                for (int cardIndex = 0; cardIndex < 8; cardIndex++) {
                    writer.write(player + "\n");
                }
            }
        }
        try {
            CardGame.intialiseGame(playerCount, packFilePath);
        } catch (IOException ioException) {
            fail("Game execution failed while using the generated pack file: " + ioException.getMessage());
        }

    } catch (IOException testSetupException) {
        // Handle issues with setting up the test, unrelated to game logic
        System.err.println("Test setup encountered an error while creating the pack file: " + testSetupException.getMessage());
    } finally {
        deleteOutputFiles();
    }
}

// Helper method to delete the output files generated during 
// the test to save clogging up the project directory as there could be
// 100 files generated
// NOT A TEST METHOD
private void deleteOutputFiles() {
    // Delete the pack file if it exists
    File packFile = new File("generatedPackFile.txt");
    if (packFile.exists()) {
        packFile.delete();
    }

    // Delete player and deck output files
    for (int i = 1; i <= 100; i++) {
        File playerFile = new File("player" + i + "_output.txt");
        if (playerFile.exists()) {
            playerFile.delete();
        }
        File deckFile = new File("deck" + i + "_output.txt");
        if (deckFile.exists()) {
            deckFile.delete();
        }
    }
}
}
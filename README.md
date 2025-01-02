# ECM2414 coursework Card-Game
Software development continuous assesment developed by Daniel Wall and Katie Purvis

# Game Functionality
This is a multi-threaded Java application to simulate a card game, developed as part of ECM2414 coursework. Each player plays the game simultaneously until there is winner. The game starts by the user entering a number of a players and a file containing a cardpack. Every turn a player picks up the top card of the deck to their left and discards one to their right. The aim of the game is to have four card of the same denomination.

# Card Pack File
A valid input file is a plain text file, where eachrow contains a single non-negative integer value. there must be 8 row for every player.

## Features
- Thread-safe gameplay
- Customizable number of players
- Input/output as specified

## How to Run

### Run the Game from JAR Release
1. Ensure you have the `cards.jar` file in your working directory.
2. Open your terminal/command prompt in the directory where `cards.jar` is saved.
3. Run the following command:
```bash
java -jar cards.jar
```
4. Type the number of players and a valid card pack file. (You can use the number of players as '4' and use 'four.txt')
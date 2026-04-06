package ui;

public class HangmanPrinter {

    private static final String[] HANGMAN_STAGES = {
            """
     +---+
         |
         |
         |
    ======
  """,
            """
     +---+
     |   |
     O   |
         |
         |
    ======
""",
            """
     +---+
     |   |
     O   |
     |   |
         |
    ======
""",
            """
     +---+
     |   |
     O   |
    /|   |
         |
    ======
""",
            """
     +---+
     |   |
     O   |
    /|\\  |
         |
    ======
""",
            """
     +---+
     |   |
     O   |
    /|\\  |
    /    |
    ======
""",
            """
     +---+
     |   |
     O   |
    /|\\  |
    / \\  |
    ======
"""
    };

    public void print(int errorCount) {
        System.out.println(HANGMAN_STAGES[errorCount]);
    }

    public int getMaxMisses() {
        return HANGMAN_STAGES.length - 1;
    }

}

package domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Game {
    private final String secretWord;
    private final char[] secretChars;
    private final char[] mask;
    private int missCount = 0;
    private final int maxMisses;
    private final Set<Character> guessedLetters = new HashSet<>();



    public Game(String secretWord, int maxMisses) {
        this.secretWord = secretWord;
        this.secretChars = secretWord.toLowerCase().toCharArray();
        mask = new char[secretChars.length];
        Arrays.fill(mask, '_');
        this.maxMisses = maxMisses;
    }

    public GuessResult guess(char letter) {
        if (!guessedLetters.add(letter)) {
            return GuessResult.ALREADY_GUESSED;
        }
        boolean found = false;
        for (int i = 0; i < secretChars.length; i++) {
            if (secretChars[i] == letter) {
                mask[i] = letter;
                found = true;
            }
        }

        if (found) {
            return GuessResult.CORRECT;
        } else {
            missCount++;
            return GuessResult.INCORRECT;
        }
    }

    public String getMaskedWord() {
        return new String(mask);
    }

    public boolean isWon() {
        for (char c : mask) {
            if (c == '_') {
                return false;
            }
        }
        return true;
    }

    public boolean isLost() {
        return missCount >= maxMisses;
    }

    public int getMissCount() {
        return missCount;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public int getMaxMisses() {
        return maxMisses;
    }
}

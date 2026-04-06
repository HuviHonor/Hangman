package service;

import domain.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameService {
    private final List<String> masterDictionary;
    private List<String> gameDictionary;
    private Game currentGame;
    private final  int maxMisses;

    public GameService(List<String> masterDictionary, int maxMisses) {
        this.masterDictionary = List.copyOf(masterDictionary);
        if (this.masterDictionary.isEmpty()) {
            throw new IllegalArgumentException("Master dictionary is empty");
        }
        this.gameDictionary = new ArrayList<>(this.masterDictionary);
        Collections.shuffle(this.gameDictionary);
        this.maxMisses = maxMisses;
    }

    public Game startNewGame() {
        refillGameDictionaryIfNeeded();
        String secretWord = pickAndRemoveRandomWord();
        currentGame = new Game(secretWord, maxMisses);
        return currentGame;
    }

    public Game getOrCreateGame() {
        if (currentGame == null || currentGame.isWon() || currentGame.isLost()) {
            return startNewGame();
        }
        return currentGame;
    }

    private void refillGameDictionaryIfNeeded() {
        if (gameDictionary.isEmpty()) {
            gameDictionary = new ArrayList<>(masterDictionary);
            Collections.shuffle(gameDictionary);
        }
    }

    private String pickAndRemoveRandomWord() {
        return gameDictionary.removeLast();
    }

}

import domain.Game;
import domain.GuessResult;
import repository.DictionaryRepository;
import service.GameService;
import ui.HangmanPrinter;


import java.util.List;
import java.util.Scanner;

import static domain.Game.isCyrillicLetter;

public class Main {
    private final GameService gameService;
    private final HangmanPrinter printer;
    private final Scanner scanner = new Scanner(System.in);
    private static final int START = 1;
    private static final int QUIT = 2;
    private static final String FAIL_MESSAGE = "Ошибка, попробуйте снова";

    public Main(GameService gameService, HangmanPrinter hangmanPrinter) {
        this.gameService = gameService;
        this.printer = hangmanPrinter;
    }

    public static void main(String[] args) {
        DictionaryRepository repository = new DictionaryRepository("/Dictionary.txt");
        List<String>  master = repository.loadMasterDictionary();
        GameService gameService = new GameService(master);
        HangmanPrinter printer = new HangmanPrinter();

        new Main(gameService, printer).run();

    }

   private void run() {
        while (true) {
            printMenu();

            String s = scanner.nextLine();
            if(!isInteger(s)) {
                System.out.println(FAIL_MESSAGE);
                continue;
            }

            int command = Integer.parseInt(s);

            if (command == START) {
                playGame();
            } else if (command == QUIT) {
                System.out.println("Выход...");
                break;
            } else {
                System.out.printf("Ошибка: нужно ввести число %d или %d.\n", START, QUIT);
            }
        }
    }

    private void printMenu() {
        System.out.println(START + ". Начать новую игру");
        System.out.println(QUIT + ". Выход");
        System.out.printf("Пожалуйста, выберите %d или %d\n", START, QUIT);
        System.out.print(">> ");
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
    } catch (NumberFormatException e) {
        return false;
        }
    }

    private void playGame() {
        Game game = gameService.getOrCreateGame();

        while (!game.isWon() && !game.isLost()) {
            System.out.println("Слово: " + game.getMaskedWord());
            System.out.print("Введите букву: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty() || input.codePointCount(0, input.length()) != 1) {
                System.out.println("Нужно ввести ровно одну букву!");
                continue;
            }
            int cp = input.codePointAt(0);

            if (!isCyrillicLetter(cp)) {
                System.out.println("Нужна кириллическая буква");
                continue;
            }

            int lowerCp = Character.toLowerCase(cp);
            char letter = (char) lowerCp;
            GuessResult result = game.guess(letter);
            switch (result) {
                case CORRECT -> System.out.println("Угадали");
                case INCORRECT -> {
                    System.out.println("Такой буквы нет");
                    printer.print(game.getMissCount());
                    System.out.println("Ваше количество ошибок: " + game.getMissCount());
                    System.out.println("Максимальное количество ошибок: " + game.getMaxMisses());
                }
                case ALREADY_GUESSED -> System.out.println("Эту букву уже вводили");
            }

            if (game.isWon()) {
                System.out.println("Поздравляю, вы выиграли! \n Загаданное слово: "+ game.getSecretWord());
            } else if (game.isLost()) {
                System.out.println("Вы проиграли. Загаданное слово: " + game.getSecretWord());
            }
            System.out.println();
        }
    }

}

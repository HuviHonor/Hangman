import repository.DictionaryRepository;
import service.GameService;
import ui.GameSession;
import ui.HangmanPrinter;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        DictionaryRepository repository = new DictionaryRepository("/Dictionary.txt");
        HangmanPrinter printer = new HangmanPrinter();
        int maxMisses = printer.getMaxMisses();
        List<String>  master = repository.loadMasterDictionary();
        GameService gameService = new GameService(master, maxMisses);

        new GameSession(gameService, printer).run();

    }
}

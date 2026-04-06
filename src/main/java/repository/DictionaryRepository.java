package repository;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class DictionaryRepository {
    private  final String masterDictionaryPath;
    private final List<String> masterDictionary = new ArrayList<>();

    public DictionaryRepository(String masterDictionaryPath) {
        Objects.requireNonNull(masterDictionaryPath);
        this.masterDictionaryPath = masterDictionaryPath;
    }

    public List<String> loadMasterDictionary() {
        masterDictionary.clear();
        try (var in = DictionaryRepository.class.getResourceAsStream(masterDictionaryPath)) {
            if (in == null) {
                throw new IllegalArgumentException("Master dictionary not found: " + masterDictionaryPath);
            }
            try (var reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                reader.lines()
                        .filter(word -> word.length() >= 5)
                        .forEach(masterDictionary::add);
                return List.copyOf(masterDictionary);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot read master dictionary: " + masterDictionaryPath, e);
        }
    }}

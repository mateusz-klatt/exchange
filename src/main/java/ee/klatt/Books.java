package ee.klatt;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Books {

    private final Map<String, Book> books;

    public Books() {
        this.books = new HashMap<>();
    }

    @Override
    public String toString() {
        return books.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .map(Object::toString)
                .filter(v -> !v.isEmpty())
                .collect(Collectors.joining("\n"));
    }

    public Book getInstrument(String instrument) {
        return books.computeIfAbsent(instrument, Book::new);
    }
}

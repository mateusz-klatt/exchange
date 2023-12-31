package ee.klatt;

public class Exchange {
    private final Books books;

    public Exchange() {
        this.books = new Books();
    }

    public void newOrder(int id, String instrument, double price, int quantity, Side side) {
        books.getInstrument(instrument).addOrder(id, price, quantity, side);
    }

    @Override
    public String toString() {
        return books.toString();
    }

    @Deprecated
    public Book getBook(String instrument) {
        return books.getInstrument(instrument);
    }

    @Deprecated
    public void cancelOrder(int id, int quantity) {
        books.getInstruments().stream().anyMatch(book -> book.cancelOrder(id, quantity));
    }

    @Deprecated
    public void executeOrder(int id, int quantity) {
        books.getInstruments().stream().anyMatch(book -> book.executeOrder(id, quantity));
    }
}

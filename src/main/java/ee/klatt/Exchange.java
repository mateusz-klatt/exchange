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

    public void cancelOrder(int orderId) {
        books.getInstruments().stream().anyMatch(book -> book.removeOrder(orderId));
    }
}

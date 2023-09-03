package ee.klatt;

public class Exchange {
    private final Books books;

    public Exchange() {
        this.books = new Books();
    }

    public void newOrder(String instrument, int amount, double price, Side side) {
        books.getInstrument(instrument).addOrder(amount, price, side);
    }

    @Override
    public String toString() {
        return books.toString();
    }
}

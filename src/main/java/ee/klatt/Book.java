package ee.klatt;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ee.klatt.Side.Buy;
import static ee.klatt.Side.Sell;

public class Book {
    private final String instrument;
    private final Prices bid;
    private final Prices ask;

    public Book(String instrument) {
        this.instrument = instrument;
        this.bid = new Prices(Buy);
        this.ask = new Prices(Sell);
    }

    @Override
    public String toString() {
        return Stream.of(this.instrument, this.ask, this.bid)
                .map(Object::toString)
                .filter(v -> !v.isEmpty())
                .collect(Collectors.joining("\n"));
    }

    public void addOrder(int amount, double price, Side side) {
        if (side == Buy) {
            this.bid.addOrder(amount, price);
        } else {
            this.ask.addOrder(amount, price);
        }
    }
}

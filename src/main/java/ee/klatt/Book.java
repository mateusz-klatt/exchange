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
        return Stream.of(this.instrument, this.bid, this.ask)
                .map(Object::toString)
                .filter(v -> !v.isEmpty())
                .collect(Collectors.joining("\n"));
    }

    public void addOrder(int id, double price, int quantity, Side side) {
        var order = new Order(id, price, quantity, side);
        this.marketTake(order);
        if (order.getRemaining() > 0) {
            this.marketMake(order);
        }
    }

    private void marketMake(Order order) {
        if (order.getSide() == Buy) {
            this.bid.addOrder(order);
        } else {
            this.ask.addOrder(order);
        }
    }

    private void marketTake(Order order) {
        if (order.getSide() == Buy) {
            this.ask.executeOrder(order);
        } else {
            this.bid.executeOrder(order);
        }
    }

    @Deprecated
    public boolean cancelOrder(int id, int quantity) {
        return Stream.of(this.bid, this.ask).anyMatch(prices -> prices.cancelOrder(id, quantity));
    }

    @Deprecated
    public boolean executeOrder(int id, int quantity) {
        return Stream.of(this.bid, this.ask).anyMatch(prices -> prices.executeOrder(id, quantity));
    }

    @Deprecated
    public Prices getSide(Side side) {
        if (side == Buy) {
            return this.bid;
        } else {
            return this.ask;
        }
    }
}

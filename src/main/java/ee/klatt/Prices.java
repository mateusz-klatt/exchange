package ee.klatt;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ee.klatt.Side.Sell;

public class Prices {
    private final Side side;
    private final SortedMap<Double, Price> prices;

    public Prices(Side side) {
        this.side = side;
        this.prices = new TreeMap<>(side == Sell ? Comparator.naturalOrder() : Collections.reverseOrder());
    }

    @Override
    public String toString() {
        return prices.values().stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }

    @Deprecated
    public Stream<Price> getPrices() {
        return prices.values().stream();
    }

    public void addOrder(Order order) {
        prices.computeIfAbsent(order.getPrice(), Price::new).addOrder(order);
    }

    public void executeOrder(Order order) {
        var orders = prices.values().stream()
                .flatMap(Price::getOrders)
                .takeWhile(o -> o.crossed(order) && o.execute(order).getRemaining() == 0)
                .collect(Collectors.toList());
        orders.forEach(o -> {
            if (prices.get(o.getPrice()).removeOrder(o).getSize() == 0) {
                prices.remove(o.getPrice());
            }
        });
    }

    @Deprecated
    public boolean executeOrder(int id, int quantity) {
        var orders = new ArrayList<Order>();
        var found = prices.values().stream()
                .flatMap(Price::getOrders)
                .anyMatch(o -> {
                    if (o.getId() == id) {
                        orders.add(o);
                        return true;
                    }
                    return false;
                });
        orders.forEach(o -> {
            if (o.execute(quantity).getRemaining() == 0 && prices.get(o.getPrice()).removeOrder(o).getSize() == 0) {
                prices.remove(o.getPrice());
            }
        });
        return found;
    }

    @Deprecated
    public boolean cancelOrder(int id, int quantity) {
        var orders = new ArrayList<Order>();
        var found = prices.values().stream()
                .flatMap(Price::getOrders)
                .anyMatch(o -> {
                    if (o.getId() == id) {
                        orders.add(o);
                        return true;
                    }
                    return false;
                });
        orders.forEach(o -> {
            if (o.cancel(quantity).getRemaining() == 0 && prices.get(o.getPrice()).removeOrder(o).getSize() == 0) {
                prices.remove(o.getPrice());
            }
        });
        return found;
    }
}

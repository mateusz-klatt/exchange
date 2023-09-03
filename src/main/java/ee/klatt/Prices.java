package ee.klatt;

import java.util.*;
import java.util.stream.Collectors;

import static ee.klatt.Side.Sell;

public class Prices {
    private final Side side;
    private final Map<Double, Price> prices;

    public Prices(Side side) {
        this.side = side;
        this.prices = new HashMap<>();
    }

    @Override
    public String toString() {
        return prices.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(side == Sell ? Comparator.naturalOrder() : Collections.reverseOrder()))
                .map(Map.Entry::getValue)
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }

    public void addOrder(Order order) {
        prices.computeIfAbsent(order.getPrice(), Price::new).addOrder(order);
    }

    public void executeOrder(Order order) {
        var orders = prices.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(side == Sell ? Comparator.naturalOrder() : Collections.reverseOrder()))
                .map(Map.Entry::getValue)
                .map(Price::getOrders)
                .flatMap(List::stream)
                .filter(side == Sell ? o -> o.getPrice() <= order.getPrice() : o -> o.getPrice() >= order.getPrice())
                .takeWhile(o -> (o.execute(order).getRemaining() == 0))
                .collect(Collectors.toList());
        orders.forEach(o -> {
            if (prices.get(o.getPrice()).removeOrder(o).getSize() == 0) {
                prices.remove(o.getPrice());
            }
        });
    }
}

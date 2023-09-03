package ee.klatt;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
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
                .filter(v -> !v.isEmpty())
                .collect(Collectors.joining("\n"));
    }

    public void addOrder(int amount, double price) {
        prices.computeIfAbsent(price, Price::new).addOrder(amount);
    }
}

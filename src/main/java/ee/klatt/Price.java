package ee.klatt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Price {
    private final double price;
    private final List<Order> orders;

    public Price(double price) {
        this.price = price;
        this.orders = new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.format("%.2f %d %s", this.price, this.getRemaining(),
                this.orders.stream()
                        .map(Order::getId)
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")));
    }

    public Price addOrder(Order order) {
        this.orders.add(order);
        return this;
    }

    public double getPrice() {
        return price;
    }

    public int getSize() {
        return orders.size();
    }

    public int getRemaining() {
        return orders.stream().mapToInt(Order::getRemaining).sum();
    }

    public Stream<Order> getOrders() {
        return orders.stream();
    }

    public Price removeOrder(Order order) {
        orders.remove(order);
        return this;
    }
}

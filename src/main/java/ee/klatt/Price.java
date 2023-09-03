package ee.klatt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Price {
    private final double price;
    private final List<Order> orders;

    public Price(double price) {
        this.price = price;
        this.orders = new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.format("%.2f %d %s", this.price, this.getSize(),
                this.orders.stream()
                        .map(Order::getId)
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")));
    }

    public Price addOrder(Order order) {
        this.orders.add(order);
        return this;
    }

    public int getSize() {
        return orders.stream().mapToInt(Order::getRemaining).sum();
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    public Price removeOrder(Order order) {
        orders.remove(order);
        return this;
    }
}

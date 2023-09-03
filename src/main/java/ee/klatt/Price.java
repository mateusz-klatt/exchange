package ee.klatt;

import java.util.ArrayList;
import java.util.List;

public class Price {
    private final double price;
    private final List<Order> orders;

    public Price(double price) {
        this.price = price;
        this.orders = new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.format("%.2f %d", this.price, this.getSize());
    }

    public void addOrder(int amount) {
        this.orders.add(new Order(amount));
    }

    public int getSize() {
        return orders.stream().mapToInt(Order::getAmount).sum();
    }
}

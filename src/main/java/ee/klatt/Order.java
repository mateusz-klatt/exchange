package ee.klatt;

public class Order {
    private final int id;
    private final double price;
    private final Side side;
    private int canceled;
    private int remaining;
    private int executed;

    public Order(int id, double price, int quantity, Side side) {
        this.id = id;
        this.price = price;
        this.remaining = quantity;
        this.executed = 0;
        this.canceled = 0;
        this.side = side;
    }

    public int getId() {
        return id;
    }

    public int getCanceled() {
        return canceled;
    }

    public int getExecuted() {
        return executed;
    }

    public int getRemaining() {
        return remaining;
    }

    public double getPrice() {
        return price;
    }

    public Side getSide() {
        return side;
    }

    public Order execute(Order order) {
        if (order.side == this.side) {
            throw new RuntimeException("Cannot execute against order on same side");
        }
        if (order.price < this.price && order.side == Side.Buy) {
            throw new RuntimeException("Bid price is lower than ask price");
        }
        if (order.price > this.price && order.side == Side.Sell) {
            throw new RuntimeException("Ask price is higher than bid price");
        }
        int executed = Math.min(this.remaining, order.remaining);
        this.executed += executed;
        order.executed += executed;
        this.remaining -= executed;
        order.remaining -= executed;
        return this;
    }
}

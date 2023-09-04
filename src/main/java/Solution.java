import ee.klatt.Exchange;
import ee.klatt.Order;
import ee.klatt.Side;

import java.util.*;
import java.math.*;
import java.util.stream.Collectors;

class PriceLevelResult {
    public final float price;
    public final int totalQuantity;
    public final List<Integer> orderIDs;

    public PriceLevelResult(float price, int totalQuantity, List<Integer> orderIDs) {
        this.price = price;
        this.totalQuantity = totalQuantity;
        this.orderIDs = orderIDs;
    }
}

class OrderBook {
    private final Exchange exchange;
    public OrderBook() {
         exchange = new Exchange();
    }

    // Implement these functions as specified in the description.
    // Add any additional functions/classes as you see fit.

    public void onNewOrder(int orderID, String stockTicker, float price, int quantity, int side) {
        exchange.newOrder(orderID, stockTicker, price, quantity, Side.fromValue(side));
    }
    public void onCancelledOrder(int orderID, int quantityToCancel) {
        exchange.cancelOrder(orderID, quantityToCancel);
    }
    public void onExecutedOrder(int orderID, int quantityExecuted) {
        exchange.executeOrder(orderID, quantityExecuted);
    }

    public List<PriceLevelResult> top(int n, String stockTicker, int side) {
        var prices = exchange.getBook(stockTicker).getSide(Side.fromValue(side));
        return prices.getPrices()
                .limit(n)
                .map(p -> new PriceLevelResult((float) p.getPrice(), p.getRemaining(), p.getOrders().map(Order::getId).collect(Collectors.toList()))).collect(Collectors.toList());
    }
}
// Translate between stdin/out and function calls
public class Solution {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        OrderBook orderBook = new OrderBook();


        int numEvents = Integer.parseInt(input.nextLine());
        for (int i = 0; i < numEvents; i++) {
            String[] items = input.nextLine().split(",");
            String eventType = items[0];

            if ("N".equals(eventType)) {
                int orderID = Integer.parseInt(items[1]);
                String stockTicker = items[2];
                float price = Float.parseFloat(items[3]);
                int quantity = Integer.parseInt(items[4]);
                int side = Integer.parseInt(items[5]);
                orderBook.onNewOrder(orderID, stockTicker, price, quantity, side);
            } else if ("T".equals(eventType)) {
                int n = Integer.parseInt(items[1]);
                String stockTicker = items[2];
                int side = Integer.parseInt(items[3]);
                List<PriceLevelResult> topLevels = orderBook.top(n, stockTicker, side);

                System.out.println(topLevels.size());
                for (PriceLevelResult level : topLevels) {
                    System.out.print(level.price + "," + level.totalQuantity);
                    for (int orderID : level.orderIDs) {
                        System.out.print(",");
                        System.out.print(orderID);
                    }
                    System.out.println();
                }
            } else {
                int orderID = Integer.parseInt(items[1]);
                int quantity = Integer.parseInt(items[2]);
                if ("C".equals(eventType)) {
                    orderBook.onCancelledOrder(orderID, quantity);
                } else {
                    orderBook.onExecutedOrder(orderID, quantity);
                }
            }
        }
    }
}
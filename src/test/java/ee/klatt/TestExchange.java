package ee.klatt;

import org.junit.Test;

import static ee.klatt.Side.Buy;
import static ee.klatt.Side.Sell;
import static org.junit.Assert.assertEquals;

public class TestExchange {
    @Test
    public void testNewOrder() {
        var exchange = new Exchange();
        exchange.newOrder(1, "DAXEX GY", 100.0, 100, Buy);
        assertEquals("DAXEX GY\n" +
                "100.00 100", exchange.toString());
    }

    @Test
    public void testSumOrders() {
        var exchange = new Exchange();
        exchange.newOrder(1, "DAXEX GY", 100.0, 100, Buy);
        exchange.newOrder(2, "DAXEX GY", 100.0, 200, Buy);
        assertEquals("DAXEX GY\n" +
                "100.00 300", exchange.toString());
    }

    @Test
    public void testOrderBid() {
        var exchange = new Exchange();
        exchange.newOrder(1, "DAXEX GY", 9319.60, 100, Buy);
        exchange.newOrder(2, "DAXEX GY", 9136.50, 100, Buy);
        assertEquals("DAXEX GY\n" +
                "9319.60 100\n" +
                "9136.50 100", exchange.toString());
    }

    @Test
    public void testOrderAsk() {
        var exchange = new Exchange();
        exchange.newOrder(1, "DAXEX GY", 9152.80, 100, Sell);
        exchange.newOrder(2, "DAXEX GY", 9156.40, 100, Sell);
        assertEquals("DAXEX GY\n" +
                "9152.80 100\n" +
                "9156.40 100", exchange.toString());
    }

    @Test
    public void testOrder() {
        var exchange = new Exchange();
        exchange.newOrder(1, "DAXEX GY", 220.0, 100, Sell);
        exchange.newOrder(2, "DAXEX GY", 210.0, 100, Sell);
        exchange.newOrder(3, "DAXEX GY", 190.0, 100, Buy);
        exchange.newOrder(4, "DAXEX GY", 180.0, 100, Buy);
        assertEquals("DAXEX GY\n" +
                "190.00 100\n" +
                "180.00 100\n" +
                "210.00 100\n" +
                "220.00 100", exchange.toString());
    }
}

package ee.klatt;

import org.junit.Test;

import static ee.klatt.Side.Buy;
import static ee.klatt.Side.Sell;
import static org.junit.Assert.assertEquals;

public class TestExchange {
    @Test
    public void testNewOrder() {
        var exchange = new Exchange();
        exchange.newOrder("DAXEX GY", 100, 100.0, Buy);
        assertEquals("DAXEX GY\n" +
                "100.00 100", exchange.toString());
    }

    @Test
    public void testSumOrders() {
        var exchange = new Exchange();
        exchange.newOrder("DAXEX GY", 100, 100.0, Buy);
        exchange.newOrder("DAXEX GY", 200, 100.0, Buy);
        assertEquals("DAXEX GY\n" +
                "100.00 300", exchange.toString());
    }

    @Test
    public void testOrderBid() {
        var exchange = new Exchange();
        exchange.newOrder("DAXEX GY", 100, 9319.60, Buy);
        exchange.newOrder("DAXEX GY", 100, 9136.50, Buy);
        assertEquals("DAXEX GY\n" +
                "9319.60 100\n" +
                "9136.50 100", exchange.toString());
    }

    @Test
    public void testOrderAsk() {
        var exchange = new Exchange();
        exchange.newOrder("DAXEX GY", 100, 9152.80, Sell);
        exchange.newOrder("DAXEX GY", 100, 9156.40, Sell);
        assertEquals("DAXEX GY\n" +
                "9152.80 100\n" +
                "9156.40 100", exchange.toString());
    }

    @Test
    public void testOrder() {
        var exchange = new Exchange();
        exchange.newOrder("DAXEX GY", 100, 220.0, Sell);
        exchange.newOrder("DAXEX GY", 100, 210.0, Sell);
        exchange.newOrder("DAXEX GY", 100, 190.0, Buy);
        exchange.newOrder("DAXEX GY", 100, 180.0, Buy);
        assertEquals("DAXEX GY\n" +
                "190.00 100\n" +
                "180.00 100\n" +
                "210.00 100\n" +
                "220.00 100", exchange.toString());
    }
}

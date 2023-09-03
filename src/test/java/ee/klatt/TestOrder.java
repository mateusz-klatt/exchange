package ee.klatt;

import org.junit.Test;

import static ee.klatt.Side.Buy;
import static ee.klatt.Side.Sell;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class TestOrder {
    @Test
    public void testBid() {
        var order = new Order(100, 100.0, Buy);
        assertEquals(100, order.getRemaining());
        assertEquals(100.0, order.getPrice(), 0);
        assertEquals(Buy, order.getSide());
    }

    @Test
    public void testAsk() {
        var order = new Order(10, 10.0, Sell);
        assertEquals(10, order.getRemaining());
        assertEquals(10.0, order.getPrice(), 0);
        assertEquals(Sell, order.getSide());
    }

    @Test
    public void testExecuteSameSide() {
        var one = new Order(10, 10.0, Sell);
        var two = new Order(10, 10.0, Sell);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            one.execute(two);
        });
        assertEquals("Cannot execute against order on same side", exception.getMessage());
    }

    @Test
    public void testExecuteAskAboveBid() {
        var bid = new Order(100, 11.0, Buy);
        var ask = new Order(100, 12.0, Sell);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            bid.execute(ask);
        });
        assertEquals("Ask price is higher than bid price", exception.getMessage());
    }

    @Test
    public void testExecuteBidUnderAsk() {
        var bid = new Order(100, 11.0, Buy);
        var ask = new Order(100, 12.0, Sell);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ask.execute(bid);
        });
        assertEquals("Bid price is lower than ask price", exception.getMessage());
    }

    @Test
    public void testExecute() {
        var bid = new Order(100, 12.0, Buy);
        var ask = new Order(100, 12.0, Sell);
        bid.execute(ask);
        assertEquals(0, bid.getRemaining());
        assertEquals(0, ask.getRemaining());
        assertEquals(0, bid.getCanceled());
        assertEquals(0, ask.getCanceled());
        assertEquals(100, bid.getExecuted());
        assertEquals(100, ask.getExecuted());
    }

    @Test
    public void testExecuteBid() {
        var bid = new Order(100, 13.0, Buy);
        var ask = new Order(10, 12.0, Sell);
        ask.execute(bid);
        assertEquals(90, bid.getRemaining());
        assertEquals(0, ask.getRemaining());
        assertEquals(0, bid.getCanceled());
        assertEquals(0, ask.getCanceled());
        assertEquals(10, bid.getExecuted());
        assertEquals(10, ask.getExecuted());
    }

    @Test
    public void testExecuteAsk() {
        var bid = new Order(100, 13.0, Buy);
        var ask = new Order(150, 12.0, Sell);
        bid.execute(ask);
        assertEquals(0, bid.getRemaining());
        assertEquals(50, ask.getRemaining());
        assertEquals(0, bid.getCanceled());
        assertEquals(0, ask.getCanceled());
        assertEquals(100, bid.getExecuted());
        assertEquals(100, ask.getExecuted());
    }
}

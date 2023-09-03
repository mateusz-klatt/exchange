package ee.klatt;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestBook {
    @Test
    public void testEmptyBook() {
        var book = new Book("DAXEX GY");
        assertEquals("DAXEX GY", book.toString());
    }

    @Test
    public void testAddOrder() {
        var book = new Book("DAXEX GY");
        book.addOrder(100, 100.0, Side.Buy);
        assertEquals("DAXEX GY\n" +
                "100.00 100", book.toString());
    }

    @Test
    public void testAddOrdersCross() {
        var book = new Book("DAXEX GY");
        book.addOrder(100, 100.0, Side.Buy);
        book.addOrder(10, 99, Side.Sell);
        assertEquals("DAXEX GY\n" +
                "100.00 90", book.toString());
    }

    @Test
    public void testAddOrdersCrossOver() {
        var book = new Book("DAXEX GY");
        book.addOrder(10, 100.0, Side.Buy);
        book.addOrder(100, 99.0, Side.Sell);
        assertEquals("DAXEX GY\n" +
                "99.00 90", book.toString());
    }
}

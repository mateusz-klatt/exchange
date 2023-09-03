package ee.klatt;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestBook {
    @Test
    public void testEmptyBook() {
        var book = new Book("DAXEX GY");
        assertEquals("DAXEX GY", book.toString());
    }
}

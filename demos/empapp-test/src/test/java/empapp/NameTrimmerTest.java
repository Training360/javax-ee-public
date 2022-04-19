package empapp;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NameTrimmerTest {

    @Test
    public void testTrimName() {
        assertEquals("John Doe", new NameTrimmer().trimName("   John Doe   "));
    }
}

package sge.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by shacking on 11/09/15.
 */
public class StringUtils_Test {

    @Test
    public void padLeft () throws Exception {
        String s = StringUtils.padLeft("Hello", 3);

        assertEquals(5, s.length());
        assertTrue(s.startsWith("Hello"));

        s = StringUtils.padLeft("Hello", 5);

        assertEquals(5, s.length());
        assertTrue(s.startsWith("Hello"));

        s = StringUtils.padLeft("Hello", 10);

        assertEquals(10, s.length());
        assertTrue(s.startsWith("Hello"));

        s = StringUtils.padLeft("Hello World", 21);

        assertEquals(21, s.length());
        assertTrue(s.startsWith("Hello World"));
    }

    @Test
    public void padLeftChar () throws Exception {
        String s = StringUtils.padLeft("Hello", 3, '-');

        assertEquals(5, s.length());
        assertTrue(s.startsWith("Hello"));

        s = StringUtils.padLeft("Hello", 5, '-');

        assertEquals(5, s.length());
        assertTrue(s.startsWith("Hello"));

        s = StringUtils.padLeft("Hello", 10, '-');

        assertEquals(10, s.length());
        assertTrue(s.startsWith("Hello"));
        assertTrue(s.indexOf(' ') < 0);

        s = StringUtils.padLeft("Hello World", 21, '-');

        assertEquals(21, s.length());
        assertTrue(s.startsWith("Hello World"));
    }

    @Test
    public void padRight () throws Exception {
        String s = StringUtils.padRight("Hello", 3);

        assertEquals(5, s.length());
        assertTrue(s.endsWith("Hello"));

        s = StringUtils.padRight("Hello", 5);

        assertEquals(5, s.length());
        assertTrue(s.endsWith("Hello"));

        s = StringUtils.padRight("Hello", 10);

        assertEquals(10, s.length());
        assertTrue(s.startsWith(" "));
        assertTrue(s.endsWith("Hello"));

        s = StringUtils.padRight("Hello World", 21);

        assertEquals(21, s.length());
        assertTrue(s.startsWith(" "));
        assertTrue(s.endsWith("Hello World"));
    }

    @Test
    public void padRightChar () throws Exception {
        String s = StringUtils.padRight("Hello", 3, '-');

        assertEquals(5, s.length());
        assertTrue(s.endsWith("Hello"));

        s = StringUtils.padRight("Hello", 5, '-');

        assertEquals(5, s.length());
        assertTrue(s.endsWith("Hello"));

        s = StringUtils.padRight("Hello", 10, '-');

        assertEquals(10, s.length());
        assertTrue(s.startsWith("-"));
        assertTrue(s.endsWith("Hello"));
        assertTrue(s.indexOf(' ') < 0);

        s = StringUtils.padRight("Hello World", 21, '-');

        assertEquals(21, s.length());
        assertTrue(s.startsWith("-"));
        assertTrue(s.endsWith("Hello World"));
    }

    @Test
    public void padCenter () throws Exception {
        String s = StringUtils.padCenter("Hello", 3);

        assertEquals(5, s.length());
        assertTrue(s.endsWith("Hello"));

        s = StringUtils.padCenter("Hello", 5);

        assertEquals(5, s.length());
        assertTrue(s.endsWith("Hello"));

        s = StringUtils.padCenter("Hello", 10);

        assertEquals(10, s.length());
        assertTrue(s.startsWith(" "));
        assertTrue(s.endsWith(" "));
        assertTrue(s.indexOf("Hello") > 0);

        s = StringUtils.padCenter("Hello", 11);

        assertEquals(11, s.length());
        assertTrue(s.startsWith(" "));
        assertTrue(s.endsWith(" "));
        assertTrue(s.indexOf("Hello") > 0);

        s = StringUtils.padCenter("Hello World", 21);

        assertEquals(21, s.length());
        assertTrue(s.startsWith(" "));
        assertTrue(s.endsWith(" "));
        assertTrue(s.indexOf("Hello World") > 0);
    }

    @Test
    public void padCenterChar () throws Exception {
        String s = StringUtils.padCenter("Hello", 3, '-');

        assertEquals(5, s.length());
        assertTrue(s.endsWith("Hello"));

        s = StringUtils.padCenter("Hello", 5, '-');

        assertEquals(5, s.length());
        assertTrue(s.endsWith("Hello"));

        s = StringUtils.padCenter("Hello", 10, '-');

        assertEquals(10, s.length());
        assertTrue(s.startsWith("-"));
        assertTrue(s.endsWith("-"));
        assertTrue(s.indexOf("Hello") > 0);

        s = StringUtils.padCenter("Hello", 11, '-');

        assertEquals(11, s.length());
        assertTrue(s.startsWith("-"));
        assertTrue(s.endsWith("-"));
        assertTrue(s.indexOf("Hello") > 0);

        s = StringUtils.padCenter("Hello World", 21, '-');

        assertEquals(21, s.length());
        assertTrue(s.startsWith("-"));
        assertTrue(s.endsWith("-"));
        assertTrue(s.indexOf("Hello World") > 0);
    }

    @Test
    public void truncate () throws Exception {
        assertEquals("Hello", StringUtils.truncate("Hello", 10));
        assertEquals("Hello", StringUtils.truncate("Hello", 5));
        assertEquals("Hel...", StringUtils.truncate("Hello, World!", 6));
    }

    @Test
    public void toWidth () throws Exception {
        assertEquals("Hello World", StringUtils.toWidth("Hello World", 11));
        assertEquals("Hello World   ", StringUtils.toWidth("Hello World", 14));
        assertEquals("Hello W...", StringUtils.toWidth("Hello World", 10));
    }

    @Test
    public void extension () throws Exception {
        assertEquals("", StringUtils.extension("Test"));
        assertEquals("", StringUtils.extension("Test."));
        assertEquals("png", StringUtils.extension(".png"));
        assertEquals("txt", StringUtils.extension("foo.txt"));
        assertEquals("gz", StringUtils.extension("foo.tar.gz"));
    }

    @Test
    public void filename () throws Exception {
        assertEquals("Test", StringUtils.filename("Test"));
        assertEquals("Test.txt", StringUtils.filename("Test.txt"));
        assertEquals("Text", StringUtils.filename("/res/data/Text"));
        assertEquals("img.png", StringUtils.filename("res/art/img.png"));
        assertEquals("", StringUtils.filename("res/art/"));
    }

    @Test
    public void basePath () throws Exception {
        assertEquals("", StringUtils.basePath("Test"));
        assertEquals("", StringUtils.basePath("Test.txt"));
        assertEquals("/res/data/", StringUtils.basePath("/res/data/Text"));
        assertEquals("res/art/", StringUtils.basePath("res/art/img.png"));
        assertEquals("res/art/", StringUtils.basePath("res/art/"));
    }
}

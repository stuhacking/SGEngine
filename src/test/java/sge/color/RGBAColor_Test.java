package sge.color;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RGBAColor_Test {

    @Test
    public void shouldConvertColorsTo32Bit () {
        RGBAColor32 blk32 = RGBAColor.BLACK.to32Bit();

        assertEquals(0, blk32.r);
        assertEquals(0, blk32.g);
        assertEquals(0, blk32.b);

        RGBAColor32 wht32 = RGBAColor.WHITE.to32Bit();

        assertEquals(255, wht32.r);
        assertEquals(255, wht32.g);
        assertEquals(255, wht32.b);

        RGBAColor purple = new RGBAColor(0.8f, 0.1f, 0.6f);
        RGBAColor32 purple32 = purple.to32Bit();

        assertEquals(204, purple32.r);
        assertEquals(25, purple32.g);
        assertEquals(153, purple32.b);
    }

    @Test
    public void shouldConvertColorsToFloatSpace () {
        RGBAColor32 blk32 = new RGBAColor32((short) 0, (short) 0, (short) 0);
        RGBAColor blk = blk32.toFloatingPoint();

        assertEquals(0.0f, blk.r, 0);
        assertEquals(0.0f, blk.g, 0);
        assertEquals(0.0f, blk.b, 0);

        RGBAColor32 wht32 = new RGBAColor32((short) 255, (short) 255, (short) 255);
        RGBAColor wht = wht32.toFloatingPoint();

        assertEquals(1.0f, wht.r, 0);
        assertEquals(1.0f, wht.g, 0);
        assertEquals(1.0f, wht.b, 0);

        RGBAColor32 purple32 = new RGBAColor32((short) 204, (short) 25, (short) 153);
        RGBAColor purple = purple32.toFloatingPoint();

        // Allow a little flexibility in precision for the reverse operation: +/-0.01f
        assertEquals(0.8f, purple.r, 0.01f);
        assertEquals(0.1f, purple.g, 0.01f);
        assertEquals(0.6f, purple.b, 0.01f);
    }

    @Test
    public void fromHexCode () throws Exception {
        assertEquals(RGBAColor.BLACK, RGBAColor.fromHexCode("000"));
        assertEquals(RGBAColor.BLACK, RGBAColor.fromHexCode("#000"));
        assertEquals(RGBAColor.BLACK, RGBAColor.fromHexCode("#000000"));

        assertEquals(RGBAColor.RED, RGBAColor.fromHexCode("#FF0000"));

        assertEquals(RGBAColor.BLUE, RGBAColor.fromHexCode("#00F"));
    }

}

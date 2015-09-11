package sge.util;

/**
 * Static string functions.
 */
public abstract class StringUtils {

    /**
     * Pad a String with trailing spaces if the input string is narrower than the desired width.
     *
     * @param value String to pad
     * @param width Total width of output string
     * @return
     */
    public static String padLeft (final String value, final int width) {
        return String.format("%-" + width + "s", value);
    }

    /**
     * Pad a String with trailing characters if the input string is narrower than the desired width.
     *
     * @param value String to pad
     * @param width Total width of output string
     * @param c Character to use for padding
     * @return
     */
    public static String padLeft (final String value, final int width, final char c) {
        int padding = width - value.length();

        if (padding <= 0) {
            return value;
        }

        return value + new String(new char[padding]).replace('\0', c);
    }

    /**
     * Pad a String with leading spaces if the input string is narrower than the desired width.
     *
     * @param value String to pad
     * @param width Total width of output string
     * @return
     */
    public static String padRight (final String value, final int width) {
        return String.format("%" + width + "s", value);
    }

    /**
     * Pad a String with leading spaces if the input string is narrower than the desired width.
     *
     * @param value String to pad
     * @param width Total width of output string
     * @param c Character to use for padding
     * @return
     */
    public static String padRight (final String value, final int width, final char c) {
        int padding = width - value.length();

        if (padding <= 0) {
            return value;
        }

        return new String(new char[padding]).replace('\0', c) + value;
    }

    /**
     * Pad a String with surrounding spaces such that value is center-aligned, if the
     * input string is narrower than the desired width.
     *
     * @param value String to pad
     * @param width Total width of output string
     * @return
     */
    public static String padCenter (final String value, final int width) {
        int padding = width - value.length();

        if (padding <= 0) {
            return value;
        }

        int padR = padding / 2;
        int padL = (padding % 2 == 0) ? padR : padR + 1;

        return String.format("%" + padL + "s%s%" + padR + "s", "", value, "");
    }

    /**
     * Pad a String with surrounding spaces such that value is center-aligned, if the
     * input string is narrower than the desired width.
     *
     * @param value String to pad
     * @param width Total width of output string
     * @return
     */
    public static String padCenter (final String value, final int width, final char c) {
        int padding = width - value.length();

        if (padding <= 0) {
            return value;
        }

        int padR = padding / 2;
        int padL = (padding % 2 == 0) ? padR : padR + 1;

        return new String(new char[padL]).replace('\0', c) + value + new String(new char[padR]).replace('\0', c);
    }

    /**
     * Truncate a string if the desired with is less than the length of the input string.
     *
     * @param value String to pad
     * @param width Total width of output string
     * @return
     */
    public static String truncate (final String value, final int width) {
        if (width >= value.length()) {
            return value;
        }

        return value.substring(0, width - 3) + "...";
    }

    /**
     * Pad or Truncate a string such that the resulting length is exactly the desired width.
     *
     * @param value String to pad
     * @param width Total width of output string
     * @return
     */
    public static String toWidth (final String value, final int width) {
        return (width >= value.length()) ? padLeft(value, width) : truncate(value, width);
    }

    /**
     * Return the extension of filename or path.
     *
     * @param value
     * @return
     */
    public static String extension (final String value) {
        int i = value.lastIndexOf('.');
        return (i < 0) ? "" : value.substring(i + 1);
    }

    /**
     * Return the filename part of a path.
     *
     * @param path
     * @return
     */
    public static String filename (final String path) {
        int i = path.lastIndexOf('/');
        return (i < 0) ? path : path.substring(i + 1);
    }

    /**
     * Return the filename part of a path.
     *
     * @param path
     * @return
     */
    public static String basePath (final String path) {
        int i = path.lastIndexOf('/');
        return (i < 0) ? "" : path.substring(0, i + 1);
    }
}

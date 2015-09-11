package sge.util;

import org.apache.log4j.Logger;

/**
 * Utilities for printing types in more readable formats.
 * Created by shacking on 28/08/15.
 */
public abstract class PrettyPrint {

    private static final Logger logger = Logger.getLogger(PrettyPrint.class);

    /**
     * Print an array of data as a Square Matrix of the given rank. If the input data does not
     * contain enough values then an empty string is returned.
     *
     * @param rank
     * @param data
     * @return
     */
    public static String matrix (final int rank, final float[] data) {
        final int size = rank * rank;

        if (data.length < size) {
            logger.error(String.format("Not enough data to format a %1$dx%1$d matrix.", rank));
            return "";
        }

        // Convert all values to strings and keep track of the widest.
        String[] values = new String[size];
        int maxValueWidth = 0;
        for (int k = 0; k < size; k++) {
            values[k] = String.format("%.6f", data[k]);
            maxValueWidth = Math.max(maxValueWidth, values[k].length());
        }

        // Build our matrix rows and keep track of the widest.
        String[] lines = new String[rank];
        int maxLineWidth = 0;
        for (int k = 0; k < rank; k++) {
            lines[k] = "";
            String sep = "";
            for (int l = 0; l < rank; l++) {
                lines[k] += sep + StringUtils.padRight(values[l * rank + k], maxValueWidth);
                sep = " ";
            }

            maxLineWidth = Math.max(maxLineWidth, lines[k].length());
        }

        // Format the rows of our matrix and add the surrounding braces.
        String pad = String.format("%" + maxLineWidth + "s", "");
        StringBuilder sb = new StringBuilder();
        sb.append("\u256D").append(pad).append("\u256E\n");
        for (String s : lines) {
            sb.append("\u2502").append(s).append("\u2502\n");
        }
        sb.append("\u2570").append(pad).append("\u256F");

        return sb.toString();
    }
}

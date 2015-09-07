package sge.math;

/**
 * Complex Number class
 */
public final class Complex {

    /** Real part of Complex. */
    public final float r;

    /** Imaginary part of Complex. */
    public final float i;

    /**
     * Value Constructor.
     */
    public Complex (final float real, final float imag) {
        r = real;
        i = imag;
    }

    /**
     * Copy Constructor.
     */
    public Complex (final Complex other) {
        r = other.r;
        i = other.i;
    }

    // COMPLEX OPERATIONS

    /**
     * Add another complex to this.
     *
     * @return Result of complex addition.
     */
    public Complex add (final Complex other) {
        return new Complex(r + other.r, i + other.i);
    }

    /**
     * Subtract another complex from this.
     *
     * @return Result of complex subtraction.
     */
    public Complex sub (final Complex other) {
        return new Complex(r - other.r, i - other.i);
    }

    // JAVA HOUSEKEEPING

    @Override
    public String toString () {
        return String.format("%s+%si", r, i);
    }

    @Override
    public int hashCode () {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(i);
        result = prime * result + Float.floatToIntBits(r);
        return result;
    }

    @Override
    public boolean equals (final Object other) {
        if (this == other)
            return true;

        if (other instanceof Complex)
            return compare((Complex) other);

        return false;
    }

    /**
     * Compare this Complex against another Complex exactly.
     *
     * @return true if this Complex exactly equals the other, false otherwise
     */
    public boolean compare (final Complex other) {
        return r == other.r && i == other.i;
    }

    /**
     * Compare this Complex against another Complex within a given tolerance.
     *
     * @param epsilon Tolerance within which Complex are considered equal
     * @return true if this Complex equals the other within given
     * tolerance, false otherwise
     */
    public boolean compare (final Complex other, final float epsilon) {
        if (Math.abs(r - other.r) > epsilon)
            return false;

        if (Math.abs(i - other.i) > epsilon)
            return false;

        return true;
    }

}

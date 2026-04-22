public class uc5 {

    // ✅ Enum (Base Unit = FEET)
    enum LengthUnit {

        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.393701 / 12.0);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }

        public double fromFeet(double feetValue) {
            return feetValue / toFeetFactor;
        }
    }

    // ✅ Quantity Class (Immutable)
    static class QuantityLength {

        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {

            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }

            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid numeric value");
            }

            this.value = value;
            this.unit = unit;
        }

        // ✅ Convert this object to another unit (Instance Method)
        public QuantityLength convertTo(LengthUnit targetUnit) {

            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double valueInFeet = unit.toFeet(value);
            double convertedValue = targetUnit.fromFeet(valueInFeet);

            return new QuantityLength(convertedValue, targetUnit);
        }

        // ✅ Equality Check (same as UC4)
        @Override
        public boolean equals(Object obj) {

            if (this == obj) return true;

            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            return Double.compare(
                    unit.toFeet(this.value),
                    other.unit.toFeet(other.value)
            ) == 0;
        }

        // ✅ toString()
        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // ✅ STATIC API METHOD (MAIN CONVERSION FUNCTION)
    public static double convert(double value, LengthUnit source, LengthUnit target) {

        if (source == null || target == null) {
            throw new IllegalArgumentException("Units cannot be null");
        }

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid numeric value");
        }

        double valueInFeet = source.toFeet(value);

        return target.fromFeet(valueInFeet);
    }

    // ✅ METHOD OVERLOADING (Demo)

    // Method 1: Raw values
    public static void demonstrateLengthConversion(double value, LengthUnit from, LengthUnit to) {
        double result = convert(value, from, to);
        System.out.println("convert(" + value + ", " + from + ", " + to + ") → " + result);
    }

    // Method 2: Object-based
    public static void demonstrateLengthConversion(QuantityLength q, LengthUnit to) {
        QuantityLength converted = q.convertTo(to);
        System.out.println(q + " → " + converted);
    }

    // ✅ Main Method (Demo)
    public static void main(String[] args) {

        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCH);
        demonstrateLengthConversion(3.0, LengthUnit.YARD, LengthUnit.FEET);
        demonstrateLengthConversion(36.0, LengthUnit.INCH, LengthUnit.YARD);
        demonstrateLengthConversion(1.0, LengthUnit.CENTIMETER, LengthUnit.INCH);

        QuantityLength q = new QuantityLength(2.0, LengthUnit.YARD);
        demonstrateLengthConversion(q, LengthUnit.FEET);
    }
}
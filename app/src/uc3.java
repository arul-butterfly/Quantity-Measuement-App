public class uc3 {

    // ✅ Step 1: Enum for Units (Conversion to base unit = FEET)
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0);   // 1 inch = 1/12 feet

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    // ✅ Step 2: Generic Quantity Class
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {

            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }

            this.value = value;
            this.unit = unit;
        }

        // ✅ Convert any unit to base unit (FEET)
        private double toFeet() {
            return unit.toFeet(value);
        }

        // ✅ Override equals()
        @Override
        public boolean equals(Object obj) {

            // 1. Same reference
            if (this == obj) return true;

            // 2. Null + Type check
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            // 3. Compare after conversion
            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }
    }

    // ✅ Main Method (Demo)
    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

        System.out.println("Input: Quantity(1.0, FEET) and Quantity(12.0, INCH)");
        System.out.println("Output: " + (q1.equals(q2) ? "Equal (true)" : "Not Equal (false)"));

        QuantityLength q3 = new QuantityLength(1.0, LengthUnit.INCH);
        QuantityLength q4 = new QuantityLength(1.0, LengthUnit.INCH);

        System.out.println("\nInput: Quantity(1.0, INCH) and Quantity(1.0, INCH)");
        System.out.println("Output: " + (q3.equals(q4) ? "Equal (true)" : "Not Equal (false)"));
    }
}
public class uc6 {

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

    // ✅ Quantity Class (Immutable Value Object)
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

        // ✅ Convert to another unit
        public QuantityLength convertTo(LengthUnit targetUnit) {

            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double valueInFeet = unit.toFeet(value);
            double convertedValue = targetUnit.fromFeet(valueInFeet);

            return new QuantityLength(convertedValue, targetUnit);
        }

        // ✅ ADD METHOD (CORE OF UC6)
        public QuantityLength add(QuantityLength other) {

            if (other == null) {
                throw new IllegalArgumentException("Other quantity cannot be null");
            }

            // Convert both to base unit (feet)
            double thisInFeet = this.unit.toFeet(this.value);
            double otherInFeet = other.unit.toFeet(other.value);

            // Add
            double sumInFeet = thisInFeet + otherInFeet;

            // Convert back to THIS unit
            double resultValue = this.unit.fromFeet(sumInFeet);

            return new QuantityLength(resultValue, this.unit);
        }

        // ✅ Static Add (Flexible API)
        public static QuantityLength add(QuantityLength q1, QuantityLength q2, LengthUnit targetUnit) {

            if (q1 == null || q2 == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double sumFeet =
                    q1.unit.toFeet(q1.value) +
                            q2.unit.toFeet(q2.value);

            double result = targetUnit.fromFeet(sumFeet);

            return new QuantityLength(result, targetUnit);
        }

        // ✅ equals()
        @Override
        public boolean equals(Object obj) {

            if (this == obj) return true;

            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            return Double.compare(
                    this.unit.toFeet(this.value),
                    other.unit.toFeet(other.value)
            ) == 0;
        }

        // ✅ toString()
        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // ✅ Main Method (Demo)
    public static void main(String[] args) {

        var f = new QuantityLength(1.0, LengthUnit.FEET);
        var i = new QuantityLength(12.0, LengthUnit.INCH);

        System.out.println("Input: " + f + " + " + i);
        System.out.println("Output: " + f.add(i));  // 2 FEET

        var inchFirst = new QuantityLength(12.0, LengthUnit.INCH);
        var feetSecond = new QuantityLength(1.0, LengthUnit.FEET);

        System.out.println("\nInput: " + inchFirst + " + " + feetSecond);
        System.out.println("Output: " + inchFirst.add(feetSecond)); // 24 INCH

        var yard = new QuantityLength(1.0, LengthUnit.YARD);
        var feet = new QuantityLength(3.0, LengthUnit.FEET);

        System.out.println("\nInput: " + yard + " + " + feet);
        System.out.println("Output: " + yard.add(feet)); // 2 YARD
    }
}
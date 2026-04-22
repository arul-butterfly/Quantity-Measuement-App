public class uc7 {

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

    // ✅ Quantity Class
    static class QuantityLength {

        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {

            if (unit == null)
                throw new IllegalArgumentException("Unit cannot be null");

            if (!Double.isFinite(value))
                throw new IllegalArgumentException("Invalid value");

            this.value = value;
            this.unit = unit;
        }

        // ✅ Private Helper (DRY)
        private static double addInFeet(QuantityLength q1, QuantityLength q2) {
            return q1.unit.toFeet(q1.value) + q2.unit.toFeet(q2.value);
        }

        // ✅ UC6 Method (default → first operand unit)
        public QuantityLength add(QuantityLength other) {

            if (other == null)
                throw new IllegalArgumentException("Other cannot be null");

            double sumFeet = addInFeet(this, other);
            double result = this.unit.fromFeet(sumFeet);

            return new QuantityLength(result, this.unit);
        }

        // ✅ UC7 Method (EXPLICIT TARGET UNIT)
        public static QuantityLength add(QuantityLength q1,
                                         QuantityLength q2,
                                         LengthUnit targetUnit) {

            if (q1 == null || q2 == null || targetUnit == null)
                throw new IllegalArgumentException("Invalid input");

            double sumFeet = addInFeet(q1, q2);
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

        System.out.println("Feet Target → " +
                QuantityLength.add(f, i, LengthUnit.FEET));

        System.out.println("Inch Target → " +
                QuantityLength.add(f, i, LengthUnit.INCH));

        System.out.println("Yard Target → " +
                QuantityLength.add(f, i, LengthUnit.YARD));

        var cm = new QuantityLength(2.54, LengthUnit.CENTIMETER);
        var inch = new QuantityLength(1.0, LengthUnit.INCH);

        System.out.println("CM Target → " +
                QuantityLength.add(cm, inch, LengthUnit.CENTIMETER));
    }
}
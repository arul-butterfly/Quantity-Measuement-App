public class uc1 {

    // ✅ Inner Class: Feet
    static class Feet {
        private final double value;   // Encapsulation + Immutability

        // ✅ Constructor
        public Feet(double value) {
            this.value = value;
        }

        // ✅ Override equals() method
        @Override
        public boolean equals(Object obj) {

            // 1. Same reference check (Reflexive)
            if (this == obj) {
                return true;
            }

            // 2. Null and type check
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            // 3. Type casting
            Feet other = (Feet) obj;

            // 4. Compare values using Double.compare()
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // ✅ Main Method
    public static void main(String[] args) {

        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);

        System.out.println("Input: 1.0 ft and 1.0 ft");

        if (f1.equals(f2)) {
            System.out.println("Output: Equal (true)");
        } else {
            System.out.println("Output: Not Equal (false)");
        }
    }
}
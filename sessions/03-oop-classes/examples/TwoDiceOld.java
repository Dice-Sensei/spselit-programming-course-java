public class TwoDiceOld {
    // main is static, so a class used from it must be static too.
    static class Die {
        int sides;
        int value;

        Die(int sides) {
            this.sides = sides;
        }

        void roll() {
            value = (int) (Math.random() * sides) + 1;
        }
    }

    public static void main(String[] args) {
        Die a = new Die(6);
        Die b = new Die(6);
        Die c = a;      // copies the reference: c and a are two names for one object

        a.roll();

        System.out.println("Same value: " + (a.value == c.value));
        System.out.println("Same object (a, c): " + (a == c));
        System.out.println("Same object (a, b): " + (a == b));
    }
}

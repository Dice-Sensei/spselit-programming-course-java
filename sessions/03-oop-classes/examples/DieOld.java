public class DieOld {
    static class Die {
        int sides = 6;
        int value;

        void roll() {
            value = (int) (Math.random() * sides) + 1;
        }
    }

    public static void main(String[] args) {
        Die d = new Die();
        d.roll();
        System.out.println("Rolled: " + d.value);
    }
}

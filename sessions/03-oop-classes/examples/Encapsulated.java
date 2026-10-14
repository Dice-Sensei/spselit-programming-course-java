class Die {
    private int sides;
    private int value;

    Die(int sides) {
        if (sides < 2) {
            System.out.println("A die needs at least 2 sides, using 6");
            sides = 6;
        }
        this.sides = sides;
    }

    int getSides() {
        return sides;
    }

    int getValue() {
        return value;
    }

    void roll() {
        value = (int) (Math.random() * sides) + 1;
    }
}

public class Encapsulated {
    public static void main(String[] args) {
        Die d = new Die(1);
        System.out.println("Sides: " + d.getSides());
        d.roll();
        System.out.println("Rolled: " + d.getValue());
    }
}

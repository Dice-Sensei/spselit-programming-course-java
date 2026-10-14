class Die {
    int sides;
    int value;

    Die(int sides) {
        this.sides = sides;
    }

    void roll() {
        value = (int) (Math.random() * sides) + 1;
    }
}

void main() {
    Die a = new Die(6);
    Die b = new Die(6);
    Die c = a;      // copies the reference: c and a are two names for one object

    a.roll();

    IO.println("Same value: " + (a.value == c.value));
    IO.println("Same object (a, c): " + (a == c));
    IO.println("Same object (a, b): " + (a == b));
}

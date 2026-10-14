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
    Die d = new Die(20);
    IO.println("Sides: " + d.sides);
    d.roll();
    IO.println("Rolled: " + d.value);

    // Once a constructor with parameters exists, this no longer compiles:
    // Die plain = new Die();
}

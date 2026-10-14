class Die {
    int sides = 6;
    int value;

    void roll() {
        value = (int) (Math.random() * sides) + 1;
    }
}

void main() {
    Die d = new Die();
    d.roll();
    IO.println("Rolled: " + d.value);
}

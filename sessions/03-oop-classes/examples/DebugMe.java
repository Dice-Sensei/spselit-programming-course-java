// Leader demo for the debugger block; not a student exercise.
class Die {
    int sides = 6;
    int value;

    void roll() {
        value = (int) (Math.random() * sides) + 1;
    }
}

void main() {
    Die[] dice = new Die[3];
    for (int i = 0; i < dice.length; i++) {
        dice[i] = new Die();
    }

    int total = 0;
    for (int i = 0; i < dice.length; i++) {
        dice[i].roll();
        total = total + dice[i].value;
    }
    IO.println("Total: " + total);
}

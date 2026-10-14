// This file does NOT compile, on purpose: read the error.
class Die {
    private int value = 6;
}

public class PrivateAccess {
    public static void main(String[] args) {
        Die d = new Die();
        d.value = 99;
        System.out.println("Showing " + d.value + " on a six-sided die");
    }
}

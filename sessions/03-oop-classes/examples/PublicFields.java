class Die {
    int value = 6;
}

public class PublicFields {
    public static void main(String[] args) {
        Die d = new Die();
        d.value = 99;     // nothing stops code outside Die from doing this
        System.out.println("Showing " + d.value + " on a six-sided die");
    }
}

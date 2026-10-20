import java.util.ArrayList;

void main() {
    // Before generics: a list of Object accepts anything.
    ArrayList<Object> things = new ArrayList<>();
    things.add("hello");

    IO.println("added: " + things.get(0));

    // The compiler is happy with this cast, so the mistake only shows up when it runs.
    Integer number = (Integer) things.get(0);
    IO.println(number);
}

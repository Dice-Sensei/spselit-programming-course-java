import java.util.ArrayList;

void main() {
    ArrayList<Integer> numbers = new ArrayList<>();

    numbers.add(5);              // autoboxing: the int 5 becomes an Integer
    int first = numbers.get(0);  // unboxing: the Integer becomes an int
    IO.println(first + 1);

    Integer a = 100;
    Integer b = 100;
    IO.println(a == b);
    IO.println(a.equals(b));

    Integer c = 1000;
    Integer d = 1000;
    IO.println(c == d);
    IO.println(c.equals(d));
}

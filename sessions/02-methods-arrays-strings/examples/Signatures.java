int square(int n) {
    return n * n;
}

boolean isEven(int n) {
    return n % 2 == 0;
}

void greet(String name) {
    IO.println("Hello, " + name + "!");
}

void main() {
    IO.println(square(5));
    IO.println(isEven(7));
    greet("Ada");
}

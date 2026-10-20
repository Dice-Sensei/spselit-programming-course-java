record Card(String suit, int rank) {
}

void main() {
    Card a = new Card("hearts", 12);
    Card b = new Card("hearts", 12);

    IO.println(a);
    IO.println(a.suit());
    IO.println(a == b);
    IO.println(a.equals(b));
}

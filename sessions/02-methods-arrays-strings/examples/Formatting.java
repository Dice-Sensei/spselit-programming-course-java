void main() {
    for (int c = 0; c <= 100; c += 50) {
        double f = c * 9.0 / 5 + 32;
        IO.println(String.format("%5.1f C = %6.1f F", (double) c, f));
    }
    IO.println(String.format("%s has %d points", "Ada", 42));
}

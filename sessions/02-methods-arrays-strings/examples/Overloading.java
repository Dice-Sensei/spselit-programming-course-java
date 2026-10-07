int max(int a, int b) {
    return a > b ? a : b;
}

double max(double a, double b) {
    return a > b ? a : b;
}

int max(int a, int b, int c) {
    return max(max(a, b), c);
}

void main() {
    IO.println(max(3, 8));
    IO.println(max(2.5, 1.5));
    IO.println(max(4, 9, 6));
}

void main() {
    int number = 7;
    if (number % 2 == 0) {
        IO.println(number + " is even");
    } else {
        IO.println(number + " is odd");
    }

    for (int i = 1; i <= 5; i++) {
        IO.println(i);
    }

    int countdown = 3;
    while (countdown > 0) {
        IO.println(countdown);
        countdown--;
    }
}

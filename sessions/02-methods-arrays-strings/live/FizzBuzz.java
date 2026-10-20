void main() {
    IO.println("FizzBuzz example:");

    int end = 100;

    for (int i = 1; i < end; i++) {
        String output = "";

        if (i % 3 == 0) {
            output += "Fizz";
        }

        if (i % 5 == 0) {
            output += "Buzz";
        }

        IO.println(output == "" ? i : output);
        // if (output == "") {
        // IO.println(i);
        // } else {
        // IO.println(output);
        // }
    }
}

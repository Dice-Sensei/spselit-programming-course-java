void main() {
    String toCompare = "I understand Java";

    String userInput = IO.readln("Enter a string '" + toCompare + "' to confirm understanding: ");

    if (userInput.equals(toCompare)) {
        IO.println("Congratulations! You entered '" + toCompare + "' correctly");
    } else if (userInput.equalsIgnoreCase(toCompare)) {
        IO.println("Good enough! You entered '" + toCompare + "' but with different case");
    } else {
        IO.println("Really? You did not enter '" + toCompare + "'");
    }
}
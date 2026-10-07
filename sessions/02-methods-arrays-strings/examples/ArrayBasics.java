void main() {
    int[] scores = {70, 85, 90};
    IO.println(Arrays.toString(scores));
    IO.println(scores.length);

    IO.println(Arrays.toString(new int[3]));

    int[] unsorted = {5, 2, 9, 1};
    Arrays.sort(unsorted);
    IO.println(Arrays.toString(unsorted));

    try {
        IO.println(scores[3]);
    } catch (ArrayIndexOutOfBoundsException e) {
        IO.println("caught: " + e.getMessage());
    }
}

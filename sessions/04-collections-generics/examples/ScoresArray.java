import java.util.Arrays;

void main() {
    int[] scores = {70, 85, 90, 60};

    // An array cannot shrink: to remove index 1 we shift everything left by hand
    // and count how many slots are still in use.
    int used = scores.length;
    for (int i = 1; i < used - 1; i++) {
        scores[i] = scores[i + 1];
    }
    used--;

    int[] result = Arrays.copyOf(scores, used);
    IO.println(Arrays.toString(result));
}

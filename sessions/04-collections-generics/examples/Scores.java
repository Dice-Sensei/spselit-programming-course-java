import java.util.ArrayList;

void main() {
    ArrayList<Integer> scores = new ArrayList<>();
    scores.add(70);
    scores.add(85);
    scores.add(90);
    scores.add(60);

    scores.remove(1);

    IO.println(scores);
}

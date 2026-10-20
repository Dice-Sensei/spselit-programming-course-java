import java.util.HashMap;

void main() {
    String text = "to be or not to be";
    HashMap<String, Integer> counts = new HashMap<>();

    for (String word : text.split(" ")) {
        if (counts.containsKey(word)) {
            counts.put(word, counts.get(word) + 1);
        } else {
            counts.put(word, 1);
        }
    }

    // Look up by key: a HashMap makes no promise about the order of its entries.
    IO.println("to=" + counts.get("to"));
    IO.println("be=" + counts.get("be"));
    IO.println("or=" + counts.get("or"));
    IO.println("not=" + counts.get("not"));
    IO.println(counts.containsKey("xyz"));
}

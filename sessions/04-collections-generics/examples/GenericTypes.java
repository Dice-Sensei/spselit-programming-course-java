import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Card {
    String suit;
    int rank;

    Card(String suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }
}

void main() {
    // Read it from the outside in: a Map from String to a List of Cards.
    Map<String, List<Card>> hands = new HashMap<>();

    hands.put("Ada", new ArrayList<>());
    hands.get("Ada").add(new Card("hearts", 12));
    hands.get("Ada").add(new Card("spades", 3));

    List<Card> adasHand = hands.get("Ada");
    IO.println(adasHand.size());
    IO.println(adasHand.get(0).suit);
}

import java.util.ArrayList;

class Card {
    String suit;
    int rank;

    Card(String suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    String describe() {
        return rank + " of " + suit;
    }
}

void main() {
    ArrayList<Card> hand = new ArrayList<>();
    hand.add(new Card("hearts", 12));
    hand.add(new Card("spades", 3));
    hand.add(new Card("clubs", 7));

    IO.println(hand.size());

    for (Card card : hand) {
        IO.println(card.describe());
    }

    hand.remove(0);
    IO.println(hand.size());
}

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
    Card card = new Card("hearts", 12);
    IO.println(card.describe());
}

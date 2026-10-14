class PlainCard {
    String suit;
    int rank;

    PlainCard(String suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }
}

class Card {
    String suit;
    int rank;

    Card(String suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String toString() {
        return rank + " of " + suit;
    }
}

void main() {
    IO.println(new PlainCard("hearts", 12));
    IO.println(new Card("hearts", 12));
}

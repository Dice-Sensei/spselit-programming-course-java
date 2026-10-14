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

    public boolean equals(Object o) {
        if (!(o instanceof Card other)) {
            return false;
        }
        return suit.equals(other.suit) && rank == other.rank;
    }
}

void main() {
    PlainCard p1 = new PlainCard("hearts", 12);
    PlainCard p2 = new PlainCard("hearts", 12);
    Card c1 = new Card("hearts", 12);
    Card c2 = new Card("hearts", 12);

    IO.println("PlainCard ==: " + (p1 == p2));
    IO.println("PlainCard equals: " + p1.equals(p2));
    IO.println("Card ==: " + (c1 == c2));
    IO.println("Card equals: " + c1.equals(c2));
}

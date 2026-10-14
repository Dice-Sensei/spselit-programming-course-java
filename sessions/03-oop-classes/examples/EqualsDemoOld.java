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
        if (!(o instanceof Card)) {
            return false;
        }
        Card other = (Card) o;
        return suit.equals(other.suit) && rank == other.rank;
    }
}

public class EqualsDemoOld {
    public static void main(String[] args) {
        PlainCard p1 = new PlainCard("hearts", 12);
        PlainCard p2 = new PlainCard("hearts", 12);
        Card c1 = new Card("hearts", 12);
        Card c2 = new Card("hearts", 12);

        System.out.println("PlainCard ==: " + (p1 == p2));
        System.out.println("PlainCard equals: " + p1.equals(p2));
        System.out.println("Card ==: " + (c1 == c2));
        System.out.println("Card equals: " + c1.equals(c2));
    }
}

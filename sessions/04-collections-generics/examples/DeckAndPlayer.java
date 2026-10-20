import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

// A Deck HAS cards. It knows how to build, shuffle and deal them, and nothing else.
class Deck {
    List<Card> cards = new ArrayList<>();

    Deck() {
        String[] suits = {"hearts", "diamonds", "clubs", "spades"};
        for (String suit : suits) {
            for (int rank = 1; rank <= 13; rank++) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    void shuffle() {
        Collections.shuffle(cards);
    }

    Card deal() {
        return cards.remove(cards.size() - 1);
    }

    int size() {
        return cards.size();
    }
}

// A Player HAS a hand. It knows what it holds, and nothing about the whole deck.
class Player {
    String name;
    List<Card> hand = new ArrayList<>();

    Player(String name) {
        this.name = name;
    }

    void take(Card card) {
        hand.add(card);
    }
}

void main() {
    Deck deck = new Deck();
    IO.println("cards in deck: " + deck.size());

    deck.shuffle();
    Player ada = new Player("Ada");
    for (int i = 0; i < 5; i++) {
        ada.take(deck.deal());
    }

    IO.println("cards left: " + deck.size());
    IO.println(ada.name + " holds " + ada.hand.size() + " cards:");
    for (Card card : ada.hand) {
        IO.println("  " + card.describe());
    }
}

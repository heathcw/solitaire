import java.util.ArrayList;
import java.util.Collections;

public class deck {
    public ArrayList<card> deck = new ArrayList<>();

    public deck() {
        ArrayList<suit> suits = new ArrayList<>();
        suits.add(suit.HEARTS);
        suits.add(suit.DIAMONDS);
        suits.add(suit.CLUBS);
        suits.add(suit.SPADES);
        for (int i = 1; i <= 13; i++) {
            for (suit suitToAdd : suits) {
                card newCard = new card(i, suitToAdd);
                deck.add(newCard);
            }
        }
        Collections.shuffle(deck);
    }

    public card drawCard() {
        if (deck.isEmpty()) {
            return null;
        }
        return deck.removeLast();
    }

    public void addCard(card cardToAdd) {
        deck.add(cardToAdd);
    }
}

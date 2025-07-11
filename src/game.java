import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class game {
    public deck gameDeck = new deck();
    public Stack<card> heartStack = new Stack<>();
    public Stack<card> clubStack = new Stack<>();
    public Stack<card> diamondStack = new Stack<>();
    public Stack<card> spadeStack = new Stack<>();
    public ArrayList<ArrayList<card>> playingField = new ArrayList<>();
    public Stack<card> drawPile = new Stack<>();

    public game() {
        deal();
    }

    public void deal() {
        ArrayList<card> stack1 = new ArrayList<>();
        ArrayList<card> stack2 = new ArrayList<>();
        ArrayList<card> stack3 = new ArrayList<>();
        ArrayList<card> stack4 = new ArrayList<>();
        ArrayList<card> stack5 = new ArrayList<>();
        ArrayList<card> stack6 = new ArrayList<>();
        ArrayList<card> stack7 = new ArrayList<>();
        card topCard = gameDeck.drawCard();
        topCard.flip();

        stack1.add(topCard);

        stack2.add(gameDeck.drawCard());
        topCard = gameDeck.drawCard();
        topCard.flip();
        stack2.add(topCard);

        for (int i = 0; i < 2; i++) { stack3.add(gameDeck.drawCard()); }
        topCard = gameDeck.drawCard();
        topCard.flip();
        stack3.add(topCard);

        for (int i = 0; i < 3; i++) { stack4.add(gameDeck.drawCard()); }
        topCard = gameDeck.drawCard();
        topCard.flip();
        stack4.add(topCard);

        for (int i = 0; i < 4; i++) { stack5.add(gameDeck.drawCard()); }
        topCard = gameDeck.drawCard();
        topCard.flip();
        stack5.add(topCard);

        for (int i = 0; i < 5; i++) { stack6.add(gameDeck.drawCard()); }
        topCard = gameDeck.drawCard();
        topCard.flip();
        stack6.add(topCard);

        for (int i = 0; i < 6; i++) { stack7.add(gameDeck.drawCard()); }
        topCard = gameDeck.drawCard();
        topCard.flip();
        stack7.add(topCard);

        playingField.add(stack1);
        playingField.add(stack2);
        playingField.add(stack3);
        playingField.add(stack4);
        playingField.add(stack5);
        playingField.add(stack6);
        playingField.add(stack7);

    }

    public ArrayList<ArrayList<card>> getPlayingField() { return this.playingField; }
    public Stack<card> getDrawPile() { return this.drawPile; }
    public deck getGameDeck() { return this.gameDeck; }

    public void draw() {
        card drawn = gameDeck.drawCard();
        if (drawn == null) {
            while (!drawPile.isEmpty()) {
                card backToDeck = drawPile.pop();
                gameDeck.addCard(backToDeck);
            }
            drawn = gameDeck.drawCard();
        }
        drawn.flip();
        drawPile.push(drawn);
    }

    public boolean moveCardDrawPile(int destination) {
        card cardToMove = drawPile.pop();
        if (playingField.get(destination).isEmpty()) {
            if (cardToMove.cardValue == 13) {
                playingField.get(destination).add(cardToMove);
                return true;
            }
            return false;
        }
        card cardToCompare = playingField.get(destination).removeLast();
        switch (cardToMove.cardSuit) {
            case CLUBS, SPADES:
                if (cardToMove.cardValue == cardToCompare.cardValue - 1) {
                    if (cardToCompare.cardSuit == suit.HEARTS || cardToCompare.cardSuit == suit.DIAMONDS) {
                        playingField.get(destination).add(cardToCompare);
                        playingField.get(destination).add(cardToMove);
                        return true;
                    }
                }
                break;
            case HEARTS, DIAMONDS:
                if (cardToMove.cardValue == cardToCompare.cardValue - 1) {
                    if (cardToCompare.cardSuit == suit.CLUBS || cardToCompare.cardSuit == suit.SPADES) {
                        playingField.get(destination).add(cardToCompare);
                        playingField.get(destination).add(cardToMove);
                        return true;
                    }
                }
                break;
        }
        playingField.get(destination).add(cardToCompare);
        drawPile.push(cardToMove);
        return false;
    }

    public boolean moveCardStack(int origin, int index, int destination) {
        ArrayList<card> originStack = playingField.get(origin);
        card topOfStack = originStack.get(index);
        List<card> stackToMove = originStack.subList(index, originStack.size());

        if (playingField.get(destination).isEmpty()) {
            if (topOfStack.cardValue == 13) {
                playingField.get(destination).addAll(stackToMove);
                stackToMove.clear();
                if (originStack.getLast().faceDown) { originStack.getLast().flip(); }
                return true;
            }
            return false;
        }

        card cardToCompare = playingField.get(destination).removeLast();
        switch (topOfStack.cardSuit) {
            case CLUBS, SPADES:
                if (topOfStack.cardValue == cardToCompare.cardValue - 1) {
                    if (cardToCompare.cardSuit == suit.HEARTS || cardToCompare.cardSuit == suit.DIAMONDS) {
                        playingField.get(destination).add(cardToCompare);
                        playingField.get(destination).addAll(stackToMove);
                        stackToMove.clear();
                        if (!originStack.isEmpty() && originStack.getLast().faceDown) { originStack.getLast().flip(); }
                        return true;
                    }
                }
                break;
            case HEARTS, DIAMONDS:
                if (topOfStack.cardValue == cardToCompare.cardValue - 1) {
                    if (cardToCompare.cardSuit == suit.CLUBS || cardToCompare.cardSuit == suit.SPADES) {
                        playingField.get(destination).add(cardToCompare);
                        playingField.get(destination).addAll(stackToMove);
                        stackToMove.clear();
                        if (!originStack.isEmpty() && originStack.getLast().faceDown) { originStack.getLast().flip(); }
                        return true;
                    }
                }
                break;
        }
        playingField.get(destination).add(cardToCompare);
        return false;
    }

    public boolean moveCardSuit(int origin, suit destination) {
        card cardToMove = playingField.get(origin).removeLast();
        card cardToCompare;
        switch (destination) {
            case CLUBS:
                if (clubStack.isEmpty() && cardToMove.cardSuit == suit.CLUBS) {
                    clubStack.push(cardToMove);
                    playingField.get(origin).getLast().flip();
                    return true;
                }
                cardToCompare = clubStack.pop();
                if ((cardToCompare.cardValue == cardToMove.cardValue - 1) && cardToMove.cardSuit == suit.CLUBS) {
                    clubStack.push(cardToCompare);
                    clubStack.push(cardToMove);
                    playingField.get(origin).getLast().flip();
                    return true;
                } else {
                    clubStack.push(cardToCompare);
                    break;
                }
            case HEARTS:
                if (heartStack.isEmpty() && cardToMove.cardSuit == suit.HEARTS) {
                    heartStack.push(cardToMove);
                    playingField.get(origin).getLast().flip();
                    return true;
                }
                cardToCompare = heartStack.pop();
                if ((cardToCompare.cardValue == cardToMove.cardValue - 1) && cardToMove.cardSuit == suit.HEARTS) {
                    heartStack.push(cardToCompare);
                    heartStack.push(cardToMove);
                    playingField.get(origin).getLast().flip();
                    return true;
                } else {
                    heartStack.push(cardToCompare);
                    break;
                }
            case SPADES:
                if (spadeStack.isEmpty() && cardToMove.cardSuit == suit.SPADES) {
                    spadeStack.push(cardToMove);
                    playingField.get(origin).getLast().flip();
                    return true;
                }
                cardToCompare = spadeStack.pop();
                if ((cardToCompare.cardValue == cardToMove.cardValue - 1) && cardToMove.cardSuit == suit.SPADES) {
                    spadeStack.push(cardToCompare);
                    spadeStack.push(cardToMove);
                    playingField.get(origin).getLast().flip();
                    return true;
                } else {
                    spadeStack.push(cardToCompare);
                    break;
                }
            case DIAMONDS:
                if (diamondStack.isEmpty() && cardToMove.cardSuit == suit.DIAMONDS) {
                    diamondStack.push(cardToMove);
                    playingField.get(origin).getLast().flip();
                    return true;
                }
                cardToCompare = diamondStack.pop();
                if ((cardToCompare.cardValue == cardToMove.cardValue - 1) && cardToMove.cardSuit == suit.DIAMONDS) {
                    diamondStack.push(cardToCompare);
                    diamondStack.push(cardToMove);
                    playingField.get(origin).getLast().flip();
                    return true;
                } else {
                    diamondStack.push(cardToCompare);
                    break;
                }
            default:
                playingField.get(origin).add(cardToMove);
                return false;
        }
        playingField.get(origin).add(cardToMove);
        return false;
    }
}

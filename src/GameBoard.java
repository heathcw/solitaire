import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class GameBoard extends JPanel {

    private final game solitaire;
    private CardComponent selectedCard = null;

    public GameBoard() {
        solitaire = new game();
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Background
        g.setColor(new Color(0, 100, 0)); // dark green
        g.fillRect(0, 0, getWidth(), getHeight());

        drawPlayingField();
    }


    private void drawPlayingField() {
        this.removeAll();
        ArrayList<ArrayList<card>> playingField = solitaire.getPlayingField();
        Stack<card> drawPile = solitaire.getDrawPile();
        deck gameDeck = solitaire.getGameDeck();
        Stack<card> heartStack = solitaire.heartStack;
        Stack<card> clubStack = solitaire.clubStack;
        Stack<card> diamondStack = solitaire.diamondStack;
        Stack<card> spadeStack = solitaire.spadeStack;
        ArrayList<Stack<card>> suitStacks = new ArrayList<>();
        suitStacks.add(heartStack);
        suitStacks.add(clubStack);
        suitStacks.add(diamondStack);
        suitStacks.add(spadeStack);

        //suitStacks
        for (int i = 4; i < 8; i++) {
            Stack<card> stackToAdd = suitStacks.get(i - 4);
            int x = 50 + i * 100;
            int y = 25;
            CardComponent suitTop;
            if (stackToAdd.isEmpty()) {
                suitTop = new CardComponent(null, -3, -3, this);
            } else {
                suitTop = new CardComponent(stackToAdd.getLast(), -3, -3, this);
            }
            suitTop.setBounds(x, y, 70, 100);
            this.add(suitTop);
        }

        //drawPile
        CardComponent deckTop;
        if (!gameDeck.deck.isEmpty()) {
            deckTop = new CardComponent(gameDeck.deck.getLast(), -1, -1, this);
        } else {
            deckTop = new CardComponent(null, -1, -1, this);
        }
        deckTop.setBounds(25, 100, 70, 100);
        this.add(deckTop);
        if (!drawPile.isEmpty()) {
            CardComponent drawTop = new CardComponent(drawPile.getLast(), -2, -2, this);
            drawTop.setBounds(25, 225, 70, 100);
            this.add(drawTop);
        }

        //tableau
        int x = 50;
        int y;
        int stackNumber = 0;
        for (ArrayList<card> stack : playingField) {
            x = x + 100;
            y = 150;
            int index = 0;
            if (stack.isEmpty()) {
                CardComponent cardComp = new CardComponent(null, stackNumber, 0, this);
                cardComp.setBounds(x, y, 70, 100);
                this.add(cardComp);
                continue;
            }
            for (card cardToDraw : stack) {
                CardComponent cardComp = new CardComponent(cardToDraw, stackNumber, index, this);
                cardComp.setBounds(x, y, 70, 100);
                this.add(cardComp);
                this.setComponentZOrder(cardComp, 0);
                y = y + 25;
                index += 1;
            }
            stackNumber +=1;
        }

    }

    public void handleCardClick(CardComponent clickedCard) {
        if (selectedCard != null) {
            selectedCard.setBorder(null); // Deselect previous
            if (selectedCard.index == -2) {
                solitaire.moveCardDrawPile(clickedCard.stackNumber);
            } else if (clickedCard.index != -2) {
                solitaire.moveCardStack(selectedCard.stackNumber, selectedCard.index, clickedCard.stackNumber);
            }
            selectedCard = null;
        } else {
            if (clickedCard.nullCard()) {
                solitaire.draw();
            } else if (!solitaire.moveCardSuit(clickedCard.stackNumber, clickedCard.cardSuit(), clickedCard.cardNumber())) {
                selectedCard = clickedCard;
                selectedCard.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
            }
        }
        drawPlayingField();
        this.revalidate();
        this.repaint();
    }

    public void handleCardClickFaceDown(CardComponent clickedCard) {
        if (clickedCard.index == -1) {
            solitaire.draw();
            drawPlayingField();
            this.revalidate();
            this.repaint();
        }
    }
}

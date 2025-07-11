import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class CardComponent extends JComponent {
    private final card cardComponent;
    public int stackNumber;
    public int index;

    public CardComponent(card newCard, int stackNumber, int index, GameBoard board) {
        cardComponent = newCard;
        this.stackNumber = stackNumber;
        this.index = index;
        setOpaque(true); // allow background painting
        setBackground(Color.WHITE); // make it visible

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (cardComponent == null || !cardComponent.faceDown) {
                    board.handleCardClick(CardComponent.this);
                } else {
                    System.out.println("Card clicked: facedown");
                    board.handleCardClickFaceDown(CardComponent.this);
                }
            }
        });
    }

    public int cardNumber() { return this.cardComponent.cardValue; }
    public suit cardSuit() { return this.cardComponent.cardSuit; }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (cardComponent == null) {
            drawEmptyCard(g, 0, 0);
        } else if (!cardComponent.faceDown) {
            drawCardFront(g, 0, 0, cardComponent.cardSuit, cardComponent.cardValue);
        } else {
            drawCardBack(g, 0, 0); // If you support face-down cards
        }
    }

    public void drawCardFront(Graphics g, int x, int y, suit cardSuit, int value) {
        Font newFont = new Font("SansSerif", Font.PLAIN, 20);
        String valueString = switch (value) {
            case 1 -> "A";
            case 11 -> "J";
            case 12 -> "Q";
            case 13 -> "K";
            default -> Integer.toString(value);
        };

        String suitString = switch (cardSuit) {
            case CLUBS -> "♣";
            case HEARTS -> "♥";
            case SPADES -> "♠";
            case DIAMONDS -> "♦";
        };

        Color cardColor = switch (cardSuit) {
            case CLUBS, SPADES -> Color.BLACK;
            case HEARTS, DIAMONDS -> Color.RED;
        };

        g.setColor(Color.WHITE);
        g.fillRoundRect(x, y, 70, 100, 10, 10);
        g.setColor(Color.BLACK);
        g.drawRoundRect(x, y, 70, 100, 10, 10);
        g.setColor(cardColor);
        g.drawString(valueString + suitString, x + 5, y+ 15);
        g.drawString(valueString + suitString, x + 47, y + 95);
        g.setFont(newFont);
        g.drawString(suitString, x + 30, y + 55);
    }

    private void drawCardBack(Graphics g, int x, int y) {
        int width = 70;
        int height = 100;

        // Draw background fill
        g.setColor(new Color(30, 60, 180)); // Blue shade
        g.fillRoundRect(x + 1, y + 1, width - 1, height - 1, 10, 10);

        // Draw inner pattern (checkerboard)
        g.setColor(Color.WHITE);
        int boxSize = 10;
        for (int i = 0; i < width / boxSize; i++) {
            for (int j = 0; j < height / boxSize; j++) {
                if ((i + j) % 2 == 0) {
                    g.fillRect(x + i * boxSize, y + j * boxSize, boxSize, boxSize);
                }
            }
        }

        // Draw border
        g.setColor(Color.BLACK);
        g.drawRoundRect(x, y, width, height, 10, 10);
    }

    private void drawEmptyCard(Graphics g, int x, int y) {
        int width = 70;
        int height = 100;
        g.setColor(Color.BLACK);
        g.drawRoundRect(x, y, width, height, 10, 10);
    }
}

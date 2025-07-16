import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CardComponent extends JComponent {
    private final card cardComponent;
    public int stackNumber;
    public int index;
    public boolean highlighted = false;

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
                    board.handleCardClickFaceDown(CardComponent.this);
                }
            }
        });
    }

    public int cardNumber() { return this.cardComponent.cardValue; }
    public suit cardSuit() { return this.cardComponent.cardSuit; }
    public boolean nullCard() { return cardComponent == null; }
    public void highlight() { this.highlighted = true; }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (cardComponent == null) {
            drawEmptyCard(g);
        } else if (!cardComponent.faceDown) {
            drawCardFront(g, 0, 0, cardComponent.cardSuit, cardComponent.cardValue);
            //if (highlighted) { setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2)); }
        } else {
            drawCardBack(g); // If you support face-down cards
        }
    }

    public void drawCardFront(Graphics g, int x, int y, suit cardSuit, int value) {
        Graphics2D g2 = (Graphics2D) g;
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

        g2.setColor(Color.WHITE);
        g2.fillRoundRect(x, y, 70, 100, 10, 10);
        if (highlighted) {
            g2.setColor(Color.yellow);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(x, y, 70, 100, 10, 10);
        }
        else {
            g2.setColor(Color.BLACK);
            g2.drawRoundRect(x, y, 70, 100, 10, 10);
        }
        g2.setStroke(new BasicStroke(1));
        g2.setColor(cardColor);
        g2.drawString(valueString + suitString, x + 5, y+ 15);
        g2.drawString(valueString + suitString, x + 47, y + 95);
        g2.setFont(newFont);
        g2.drawString(suitString, x + 30, y + 55);
    }

    private void drawCardBack(Graphics g) {
        int width = 70;
        int height = 100;

        // Draw background fill
        g.setColor(new Color(30, 60, 180)); // Blue shade
        g.fillRoundRect(1, 1, width - 1, height - 1, 10, 10);

        // Draw inner pattern (checkerboard)
        g.setColor(Color.WHITE);
        int boxSize = 10;
        for (int i = 0; i < width / boxSize; i++) {
            for (int j = 0; j < height / boxSize; j++) {
                if ((i + j) % 2 == 0) {
                    g.fillRect(i * boxSize, j * boxSize, boxSize, boxSize);
                }
            }
        }

        // Draw border
        g.setColor(Color.BLACK);
        g.drawRoundRect(0, 0, width, height, 10, 10);
    }

    private void drawEmptyCard(Graphics g) {
        int width = 70;
        int height = 100;
        g.setColor(Color.BLACK);
        g.drawRoundRect(0, 0, width, height, 10, 10);
    }
}

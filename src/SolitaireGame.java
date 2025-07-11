import javax.swing.*;

public class SolitaireGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Solitaire");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);

        GameBoard board = new GameBoard();
        frame.add(board);

        frame.setVisible(true);
    }
}
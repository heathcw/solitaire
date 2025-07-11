public class card {
    public int cardValue;
    public suit cardSuit;
    public boolean faceDown;

    public card(int cardValue, suit cardSuit) {
        this.cardValue = cardValue;
        this.cardSuit = cardSuit;
        this.faceDown = true;
    }

    public void flip() {
        faceDown = !faceDown;
    }
}

public class Player {
    private static int score = 0;
    public Card[] holeCards = new Card[2];

    // ctor
    public Player() {
    }

    public Player(Card card1, Card card2) {
        holeCards[0] = card1;
        holeCards[1] = card2;
    }

    public void addscore(int temp) {
        score = score + temp;
    }

    public int getscore() {
        return score;
    }

    // methods
    protected void setCard(Card card, int cardNum) {
        holeCards[cardNum] = card;
    }

    protected Card getCard(int cardNum) {
        return holeCards[cardNum];
    }

    protected int cardSize() {
        return holeCards.length;
    }

    protected void printCards(int playerNumber) {
        System.out.println("Player " + (playerNumber + 1) + " hole cards:");

        for (int i = 0; i < 2; i++) {
            System.out.println(holeCards[i].printCard());
        }
        System.out.println("\n");
    }
}
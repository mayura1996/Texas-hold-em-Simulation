public class Board {
    private Card[] board = new Card[5];
    private Card[] burnCards = new Card[3];

    // constructor
    public Board() {
    }

    // methods
    protected void setBoardCard(Card card, int cardNum) {
        this.board[cardNum] = card;
    }

    protected Card getCard(int cardNum) {
        return this.board[cardNum];
    }

    protected void setBurnCard(Card card, int cardNum) {
        this.burnCards[cardNum] = card;
    }

    protected Card getBurnCard(int cardNum) {
        return this.burnCards[cardNum];
    }

    protected int boardSize() {
        return board.length;
    }

    protected void printBoard(int value) {
        System.out.println("The board contains the following cards:");
        for (int i = 0; i < value; i++) {
            System.out.println(i + 1 + ": " + getCard(i).printCard());
        }
        System.out.println("\n");
    }

    protected void printBurnCards() {
        System.out.println("The burn cards are:");
        for (int i = 0; i < burnCards.length; i++) {
            System.out.println(i + 1 + ": " + getBurnCard(i).printCard());
        }
        System.out.println("\n");
    }

}
import java.util.*;

public class Card {
    private short rank, suit;

    private static String[] ranks = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" };
    private static String[] suits = { "Diamonds", "Clubs", "Hearts", "Spades" };

    // Constructor
    public Card(short rank, short suit) {
        this.rank = rank;
        this.suit = suit;
    }

    // Getter and Setters
    public short getSuit() {
        return suit;
    }

    public short getRank() {
        return rank;
    }

    protected void setSuit(short suit) {
        this.suit = suit;
    }

    protected void setRank(short rank) {
        this.rank = rank;
    }

    public static String rankAsString(int __rank) {
        return ranks[__rank];
    }

    public static String suitAsString(int __suit) {
        return suits[__suit];
    }

    public @Override String toString() {
        return rank + " of " + suit;
    }

    // Print card to string
    protected String printCard() {
        return ranks[rank] + " of " + suits[suit];
    }

    // Determine if two cards are the same
    public static boolean sameCard(Card card1, Card card2) {
        return (card1.rank == card2.rank && card1.suit == card2.suit);
    }

}

class rankComparator implements Comparator<Object> {
    public int compare(Object card1, Object card2) throws ClassCastException {

        if (!((card1 instanceof Card) && (card2 instanceof Card))) {
            throw new ClassCastException("A Card object was expeected.  Parameter 1 class: " + card1.getClass()
                    + " Parameter 2 class: " + card2.getClass());
        }

        short rank1 = ((Card) card1).getRank();
        short rank2 = ((Card) card2).getRank();

        return rank1 - rank2;
    }
}

class suitComparator implements Comparator<Object> {
    public int compare(Object card1, Object card2) throws ClassCastException {

        if (!((card1 instanceof Card) && (card2 instanceof Card))) {
            throw new ClassCastException("A Card object was expeected.  Parameter 1 class: " + card1.getClass()
                    + " Parameter 2 class: " + card2.getClass());
        }

        short suit1 = ((Card) card1).getSuit();
        short suit2 = ((Card) card2).getSuit();

        return suit1 - suit2;
    }
}
import java.util.Arrays;

public class Hand {
    private Card[] cardsAvail = new Card[7];
    private int handvalue;
    private final static short ONE = 1;
    private final static short TWO = 2;
    private final static short THREE = 3;
    private final static short FOUR = 4;

    // Constructor
    public Hand() {
    }

    public int gethandval() {
        return handvalue;
    }

    // methods
    protected void insertCard(Card card, int i) {
        cardsAvail[i] = card;
    }

    protected Card readCard(int i) {
        return cardsAvail[i];
    }

    protected int cardAmounts() {
        return cardsAvail.length;
    }

    protected void sortByRank() {
        Arrays.sort(cardsAvail, new rankComparator());
    }

    protected void sortBySuit() {
        Arrays.sort(cardsAvail, new suitComparator());
    }

    protected void sortBySuitThenRank() {
        Arrays.sort(cardsAvail, new suitComparator());
        Arrays.sort(cardsAvail, new rankComparator());
    }

    protected void sortByRankThenSuit() {
        Arrays.sort(cardsAvail, new rankComparator());
        Arrays.sort(cardsAvail, new suitComparator());
    }

    protected String evaluateHand() {
        String handResult = new String();
        short[] rankCounter = new short[13];
        short[] suitCounter = new short[4];

        // initializations
        for (int i = 0; i < rankCounter.length; i++) {
            rankCounter[i] = 0;
        }

        for (int i = 4; i < suitCounter.length; i++) {
            suitCounter[i] = 0;
        }

        // Loop through sorted cards and total ranks
        for (int i = 0; i < cardsAvail.length; i++) {
            rankCounter[cardsAvail[i].getRank()]++;
            suitCounter[cardsAvail[i].getSuit()]++;
        }

        // sort cards for evaluation
        this.sortByRankThenSuit();

        // hands are already sorted by rank and suit for royal and straight flush
        // checks.
        // check for royal flush
        handResult = evaluateRoyal(rankCounter, suitCounter);

        // check for straight flush
        if (handResult == null || handResult.length() == 0) {
            handResult = evaluateStraightFlush(rankCounter, suitCounter);
        }

        // check for four of a kind
        if (handResult == null || handResult.length() == 0) {
            handResult = evaluateFourOfAKind(rankCounter);
        }

        // check for full house
        if (handResult == null || handResult.length() == 0) {
            handResult = evaluateFullHouse(rankCounter);
        }

        // check for flush
        if (handResult == null || handResult.length() == 0) {
            handResult = evaluateFlush(rankCounter, suitCounter);
        }

        // check for straight
        if (handResult == null || handResult.length() == 0) {
            // re-sort by rank, up to this point we had sorted by rank and suit
            // but a straight is suit independent.
            this.sortByRank();
            handResult = evaluateStraight(rankCounter);
        }

        // check for three of a kind
        if (handResult == null || handResult.length() == 0) {
            handResult = evaluateThreeOfAKind(rankCounter);
        }

        // check for two pair
        if (handResult == null || handResult.length() == 0) {
            handResult = evaluateTwoPair(rankCounter);
        }

        // check for one pair
        if (handResult == null || handResult.length() == 0) {
            handResult = evaluateOnePair(rankCounter);
        }

        // check for highCard
        if (handResult == null || handResult.length() == 0) {
            handResult = evaluateHighCard(rankCounter);
        }

        return handResult;
    }

    private String evaluateRoyal(short[] rankCounter, short[] suitCounter) {
        String result = "";

        // Check for Royal Flush (10 - Ace of the same suit).
        // check if there are 5 of one suit, if not royal is impossible
        if ((rankCounter[9] >= 1 && /* 10 */
                rankCounter[10] >= 1 && /* Jack */
                rankCounter[11] >= 1 && /* Queen */
                rankCounter[12] >= 1 && /* King */
                rankCounter[0] >= 1) /* Ace */
                && (suitCounter[0] > 4 || suitCounter[1] > 4 || suitCounter[2] > 4 || suitCounter[3] > 4)) {

            // min. requirements for a royal flush have been met,
            // now loop through records for an ace and check subsequent cards.
            // Loop through the aces first since they are the first card to
            // appear in the sorted array of 7 cards.
            royalSearch: for (int i = 0; i < 3; i++) {
                // Check if first card is the ace.
                // Ace must be in position 0, 1 or 2
                if (cardsAvail[i].getRank() == 0) {
                    // because the ace could be the first card in the array
                    // but the remaining 4 cards could start at position 1,
                    // 2 or 3 loop through checking each possibility.
                    for (int j = 1; j < 4 - i; j++) {
                        if ((cardsAvail[i + j].getRank() == 9 && cardsAvail[i + j + 1].getRank() == 10
                                && cardsAvail[i + j + 2].getRank() == 11 && cardsAvail[i + j + 3].getRank() == 12)
                                && (cardsAvail[i].getSuit() == cardsAvail[i + j].getSuit()
                                        && cardsAvail[i].getSuit() == cardsAvail[i + j + 1].getSuit()
                                        && cardsAvail[i].getSuit() == cardsAvail[i + j + 2].getSuit()
                                        && cardsAvail[i].getSuit() == cardsAvail[i + j + 3].getSuit())) {
                            // Found royal flush, break and return.
                            result = "Royal Flush!! Suit: " + Card.suitAsString(cardsAvail[i].getSuit());
                            handvalue = 10;
                            break royalSearch;
                        }
                    }
                }
            }
        }
        return result;
    }

    // Straight flush is 5 consecutive cards of the same suit.
    private String evaluateStraightFlush(short[] rankCounter, short[] suitCounter) {
        String result = "";

        if (suitCounter[0] > 4 || suitCounter[1] > 4 || suitCounter[2] > 4 || suitCounter[3] > 4) {

            // min. requirements for a straight flush have been met.
            // Loop through available cards looking for 5 consecutive cards of the same
            // suit,
            // start in reverse to get the highest value straight flush
            for (int i = cardsAvail.length - 1; i > 3; i--) {
                if ((cardsAvail[i].getRank() - ONE == cardsAvail[i - ONE].getRank()
                        && cardsAvail[i].getRank() - TWO == cardsAvail[i - TWO].getRank()
                        && cardsAvail[i].getRank() - THREE == cardsAvail[i - THREE].getRank()
                        && cardsAvail[i].getRank() - FOUR == cardsAvail[i - FOUR].getRank())
                        && (cardsAvail[i].getSuit() == cardsAvail[i - ONE].getSuit()
                                && cardsAvail[i].getSuit() == cardsAvail[i - TWO].getSuit()
                                && cardsAvail[i].getSuit() == cardsAvail[i - THREE].getSuit()
                                && cardsAvail[i].getSuit() == cardsAvail[i - FOUR].getSuit())) {
                    // Found royal flush, break and return.
                    result = "Straight Flush!! " + Card.rankAsString(cardsAvail[i].getRank()) + " high of "
                            + Card.suitAsString(cardsAvail[i].getSuit());
                    handvalue = 9;
                    break;
                }
            }
        }
        return result;
    }

    // Four of a kind is 4 cards with the same rank: 2-2-2-2, 3-3-3-3, etc...
    private String evaluateFourOfAKind(short[] rankCounter) {
        String result = "";

        for (int i = 0; i < rankCounter.length; i++) {
            if (rankCounter[i] == FOUR) {
                result = "Four of a Kind, " + Card.rankAsString(i) + "'s";
                handvalue = 8;
                break;
            }
        }
        return result;
    }

    // Full house is having 3 of a kind of one rank, and two of a kind of
    // a second rank. EX: J-J-J-3-3
    private String evaluateFullHouse(short[] rankCounter) {
        String result = "";
        short threeOfKindRank = -1;
        short twoOfKindRank = -1;

        for (int i = rankCounter.length; i > 0; i--) {
            if ((threeOfKindRank < (short) 0) || (twoOfKindRank < (short) 0)) {
                if ((rankCounter[i - ONE]) > 2) {
                    threeOfKindRank = (short) (i - ONE);
                } else if ((rankCounter[i - ONE]) > 1) {
                    twoOfKindRank = (short) (i - ONE);
                }
            } else {
                break;
            }
        }

        if ((threeOfKindRank >= (short) 0) && (twoOfKindRank >= (short) 0)) {
            result = "Full House: " + Card.rankAsString(threeOfKindRank) + "'s full of "
                    + Card.rankAsString(twoOfKindRank) + "'s";
            handvalue = 7;
        }

        return result;
    }

    // Flush is 5 cards of the same suit.
    private String evaluateFlush(short[] rankCounter, short[] suitCounter) {
        String result = "";

        // verify at least 1 suit has 5 cards or more.
        if (suitCounter[0] > 4 || suitCounter[1] > 4 || suitCounter[2] > 4 || suitCounter[3] > 4) {

            for (int i = cardsAvail.length - 1; i > 3; i--) {
                if (cardsAvail[i].getSuit() == cardsAvail[i - ONE].getSuit()
                        && cardsAvail[i].getSuit() == cardsAvail[i - TWO].getSuit()
                        && cardsAvail[i].getSuit() == cardsAvail[i - THREE].getSuit()
                        && cardsAvail[i].getSuit() == cardsAvail[i - FOUR].getSuit()) {
                    // Found royal flush, break and return.
                    result = "Flush!! " + Card.rankAsString(cardsAvail[i].getRank()) + " high of "
                            + Card.suitAsString(cardsAvail[i].getSuit());
                    handvalue = 6;
                    break;
                }
            }
        }

        return result;
    }

    // Straight is 5 consecutive cards, regardless of suit.
    private String evaluateStraight(short[] rankCounter) {
        String result = "";

        // loop through rank array to check for 5 consecutive
        // index with a value greater than zero
        for (int i = rankCounter.length; i > 4; i--) {
            if ((rankCounter[i - 1] > 0) && (rankCounter[i - 2] > 0) && (rankCounter[i - 3] > 0)
                    && (rankCounter[i - 4] > 0) && (rankCounter[i - 5] > 0)) {
                result = "Straight " + Card.rankAsString(i - 1) + " high";
                handvalue = 5;
                break;
            }
        }
        return result;
    }

    // Three of a kind is 3 cards of the same rank.
    private String evaluateThreeOfAKind(short[] rankCounter) {
        String result = "";

        // loop through rank array to check for 5 consecutive
        // index with a value greater than zero
        for (int i = rankCounter.length; i > 0; i--) {
            if (rankCounter[i - 1] > 2) {
                result = "Three of a Kind " + Card.rankAsString(i - 1) + "'s";
                handvalue = 4;
                break;
            }
        }
        return result;
    }

    // Two pair is having 2 cards of the same rank, and two
    // different cards of the same rank. EX: 3-3-7-7-A
    private String evaluateTwoPair(short[] rankCounter) {
        String result = "";
        short firstPairRank = -1;
        short secondPairRank = -1;

        for (int i = rankCounter.length; i > 0; i--) {
            if ((firstPairRank < (short) 0) || (secondPairRank < (short) 0)) {
                if (((rankCounter[i - ONE]) > 1) && (firstPairRank < (short) 0)) {
                    firstPairRank = (short) (i - ONE);
                } else if ((rankCounter[i - ONE]) > 1) {
                    secondPairRank = (short) (i - ONE);
                }
            } else {
                // two pair found, break loop.
                break;
            }
        }

        // populate output
        if ((firstPairRank >= (short) 0) && (secondPairRank >= (short) 0)) {
            if (secondPairRank == (short) 0) {
                // Aces serve as top rank but are at the bottom of the rank array
                // swap places so aces show first as highest pair
                result = "Two Pair: " + Card.rankAsString(secondPairRank) + "'s and " + Card.rankAsString(firstPairRank)
                        + "'s";
            } else {
                result = "Two Pair: " + Card.rankAsString(firstPairRank) + "'s and " + Card.rankAsString(secondPairRank)
                        + "'s";
            }
        }
        handvalue = 3;
        return result;
    }

    // One is is two cards of the same rank.
    private String evaluateOnePair(short[] rankCounter) {
        String result = "";

        for (int i = rankCounter.length; i > 0; i--) {
            if ((rankCounter[i - ONE]) > 1) {
                result = "One Pair: " + Card.rankAsString(i - ONE) + "'s";
                break;
            }
        }
        handvalue = 2;
        return result;
    }

    // high card is the highest card out of the 7 possible cards to be used.
    private String evaluateHighCard(short[] rankCounter) {
        String result = "";

        for (int i = rankCounter.length; i > 0; i--) {
            if ((rankCounter[i - ONE]) > 0) {
                result = "High Card: " + Card.rankAsString(i - ONE);
                break;
            }
        }
        handvalue = 1;
        return result;
    }

}
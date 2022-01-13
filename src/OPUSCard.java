//Opus card class containing information for an opus card

import enums.OPUSCardType;

/**
 * OPUS card class, contains all the data for an OPUS card
 */
public class OPUSCard {

    //Type of the OPUS card, which equals one of the values of the enum OPUSCardType
    private OPUSCardType cardType;

    //Full name of the cardholder
    private String name;

    //Expiration month of the card; number between 1 and 12 inclusively
    private int expMonth;

    //Expiration year of the card; 4-digit number
    private int expYear;

    /**
     * Default constructor
     */
    public OPUSCard() {
    }

    /**
     * Constructor with all 4 parameters
     *
     * @param cardType Type of the OPUS card
     * @param name     Full name of the cardholder
     * @param expMonth Expiration month; between 1 and 12 inclusively, otherwise set to 0
     * @param expYear  Expiration year; 4-digit year
     */
    public OPUSCard(OPUSCardType cardType, String name, int expMonth, int expYear) {
        this.cardType = cardType;
        this.name = name;
        setExpMonth(expMonth);
        this.expYear = expYear;
    }

    /**
     * Copy constructor
     *
     * @param opusCard OPUS card to copy information from
     */
    public OPUSCard(OPUSCard opusCard) {
        this.cardType = opusCard.getCardType();
        this.name = opusCard.getName();
        this.expMonth = opusCard.getExpMonth();
        this.expYear = opusCard.getExpYear();
    }

    //region Setters & Getters
    public OPUSCardType getCardType() {
        return cardType;
    }

    public String getName() {
        return name;
    }

    public int getExpMonth() {
        return expMonth;
    }

    public int getExpYear() {
        return expYear;
    }

    /**
     * Verify new expiration month and set new value
     *
     * @param newExpMonth Number between 1 and 12 inclusively, if not, month set to 0
     */
    public void setExpMonth(int newExpMonth) {
        if (newExpMonth > 1 && newExpMonth <= 12) //Check if month is between 1 and 12 inclusively
            this.expMonth = newExpMonth;
        else //Passed value isn't valid, so expMonth is set to 0
            this.expMonth = 0;
    }

    public void setExpYear(int newExpYear) {
        this.expYear = newExpYear;
    }
    //endregion

    /**
     * Information of the OPUS card in the format: cardType, name, expMonth/expYear
     *
     * @return The information of the card
     */
    @Override
    public String toString() {
        return cardType + ", " + name + ", " + (expMonth < 10 ? "0" + expMonth : String.valueOf(expMonth)) + "/" + expYear;
    }

    /**
     * Test if another OPUS card's information equals this OPUS card's (if so, this indicates that one card is an illegal copy)
     *
     * @param card Other OPUS card
     * @return True if both cards have matching information, false if at least one information if different
     */
    public boolean equals(OPUSCard card) {
        boolean isSameCardType = this.cardType.equals(card.getCardType());
        boolean isSameName = this.name.equals(card.getName());
        boolean isSameExpMonth = this.expMonth == card.getExpMonth();
        boolean isSameExpYear = this.expYear == card.getExpYear();
        return isSameCardType && isSameName && isSameExpMonth && isSameExpYear;
    }
}
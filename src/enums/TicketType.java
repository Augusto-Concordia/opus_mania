package enums;

/**
 * Enum containing the different ticket types
 */
public enum TicketType {
    REGULAR(3.5f),
    JUNIOR(2.5f),
    SENIOR(1),
    DAILY(10),
    WEEKLY(40);

    //Fare of each type
    private final float FARE;

    /**
     * Constructor with the fare of the ticket type in parameter
     *
     * @param fare Fare of the ticket
     */
    TicketType(float fare) {
        this.FARE = fare;
    }

    public float getFare() {
        return FARE;
    }

}

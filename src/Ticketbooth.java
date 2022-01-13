/**
 * Ticketbooth class, containing all the information for a ticketbooth
 */
public class Ticketbooth {

    //TIckets in the ticketbooth
    private Tickets tickets;

    //Array of all the OPUS cards in the ticketbooth
    private OPUSCard[] OPUSCards;

    /**
     * Default constructor
     */
    public Ticketbooth() {
    }

    /**
     * Constructor with all 2 parameters
     *
     * @param tickets   Tickets in the ticketbooth
     * @param opusCards Array of the OPUS cards in the ticketbooth
     */
    public Ticketbooth(Tickets tickets, OPUSCard[] opusCards) {
        this.tickets = tickets;
        this.OPUSCards = opusCards;
    }

    //region Setters & Getters
    public Tickets getTickets() {
        return tickets;
    }

    public void setTickets(Tickets tickets) {
        this.tickets = tickets;
    }

    public OPUSCard[] getOpusCards() {
        return OPUSCards;
    }

    public void setOpusCard(OPUSCard[] opusCard) {
        this.OPUSCards = opusCard;
    }
    //endregion

    /**
     * Content of the ticketbooth; ticket distribution first, followed by the OPUS cards
     *
     * @return The content of the ticketbooth
     */
    @Override
    public String toString() {
        String output = ticketsBreakdown();
        output += OPUSCardsBreakdown();
        return output;
    }

    /**
     * Breakdown of the information for each of the OPUS cards
     *
     * @return "No OPUS cards" if ticketbooth doesn't have OPUS cards. If not, returns the breakdown of OPUS cards with each card in one line
     */
    public String OPUSCardsBreakdown() {
        String output = ""; //String that will be returned
        if (OPUSCards.length == 0) output += "\n\tNo OPUS cards"; //No OPUS card
        else
            for (int i = 0; i < OPUSCards.length; i++) { //Loop through the array of OPUS cards
                OPUSCard card = OPUSCards[i];
                output += "\n\t" + (i + 1) + ". " + card.toString(); //i + 1 to show the indexes starting at 1
            }
        return output;
    }

    /**
     * Test if another ticketbooth has the same total value of tickets as this ticketbooth
     *
     * @param booth Other ticketbooth to check
     * @return True if same value, false if not
     */
    public boolean ticketsHaveSameValue(Ticketbooth booth) {
        return this.tickets.ticketsTotal() == booth.tickets.ticketsTotal();
    }

    /**
     * Test if another ticketbooth has the same ticket distribution as this ticketbooth
     *
     * @param booth Other ticketbooth to check
     * @return True if same ticket distribution, false if not
     */
    public boolean ticketsHaveSameComposition(Ticketbooth booth) {
        return this.tickets.equals(booth.tickets);
    }

    /**
     * Total value of the tickets in this ticketbooth
     *
     * @return Total value of tickets
     */
    public double totalValueOTickets() {
        return tickets.ticketsTotal();
    }

    /**
     * Number of OPUS cards in this ticketbooth
     *
     * @return Number of OPUS cards
     */
    public int getNumbOfOPUSCards() {
        return OPUSCards.length;
    }

    /**
     * Add an OPUS card to this ticketbooth
     *
     * @param cardToAdd OPUS card to add
     * @return The new number of OPUS cards in the ticketbooth
     */
    public int addOPUSCard(OPUSCard cardToAdd) {
        OPUSCard[] newCards = new OPUSCard[OPUSCards.length + 1]; //Array with one extra spot at the end for the new card

        for (int i = 0; i < OPUSCards.length; i++) { //Copy all OPUS cards to the new array
            newCards[i] = OPUSCards[i];
        }

        newCards[OPUSCards.length] = cardToAdd; //Add new OPUS card to the end

        OPUSCards = newCards; //Assign the new array to the array of OPUS cards

        return OPUSCards.length; //Return the new length
    }

    /**
     * Remove an OPUS card from the array of OPUS cards
     *
     * @param indexToRemove Index of the OPUS card to remove, where 0 is card #1, 1 is card #2, etc.
     * @return True if the removal was successful, false if not
     */
    public boolean removeOPUSCard(int indexToRemove) {
        if (OPUSCards.length == 0 || indexToRemove > OPUSCards.length - 1) return false; //Check if there are OPUS cards and if the index is too big

        OPUSCard[] newCards = new OPUSCard[OPUSCards.length - 1]; //Array with one less spot since a card will be removed

        int numbOSkipped = 0; //Number of OPUS cards skipped
        for (int i = 0; i < OPUSCards.length; i++) { //Loop through the OPUS cards of the ticketbooth
            if (indexToRemove == i) { //Check if reached the card to remove
                numbOSkipped++; //Increment
                continue; //Continue to the next interation to not add this card to the newCards array
            }
            newCards[i - numbOSkipped] = OPUSCards[i]; //If reached this line, then the card isn't the one to be removed, so add it to the array
        }

        OPUSCards = newCards; //Assign the new array to the array of OPUS cards

        return true; //If reached this line, then the array of OPUS cards has been updated and the OPUS card has been removed
    }

    /**
     * Update the expiration date of an OPUS card
     *
     * @param indexToUpdate Index of the OPUS card to update
     * @param newExpMonth   New expiration month; int between 1 and 12 inclusively
     * @param newExpYear    New expiration year; 4-digit number
     */
    public void updateOPUS(int indexToUpdate, int newExpMonth, int newExpYear) {
        OPUSCards[indexToUpdate].setExpMonth(newExpMonth);
        OPUSCards[indexToUpdate].setExpYear(newExpYear);
    }

    /**
     * Add tickets to the ticketbooth
     *
     * @param nbReg Amount of regular tickets to add
     * @param nbJun Amount of junior tickets to add
     * @param nbSen Amount of senior tickets to add
     * @param nbDai Amount of daily tickets to add
     * @param nbWee Amount of weekly tickets to add
     * @return Updated total value of the tickets
     */
    public double addTickets(int nbReg, int nbJun, int nbSen, int nbDai, int nbWee) {
        tickets.addTickets(nbReg, nbJun, nbSen, nbDai, nbWee);
        return totalValueOTickets();
    }

    /**
     * Check if 2 ticketbooths have the same total value of tickets and the same number of OPUS cards
     * @param booth Other ticketbooth to compare this one to
     * @return True if they have the same total value of tickets and same number of OPUS cards, false if not
     */
    public boolean equals(Ticketbooth booth) {
        return totalValueOTickets() == booth.totalValueOTickets() && getNumbOfOPUSCards() == booth.getNumbOfOPUSCards();
    }

    /**
     * Distribution of the tickets of the ticketbooth
     * @return Distribution of tickets
     */
    public String ticketsBreakdown() {
        return tickets.toString();
    }

}

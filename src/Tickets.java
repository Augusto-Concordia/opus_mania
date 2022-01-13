import enums.TicketType;

/**
 * Tickets class, containing the amount of each type of tickets
 */
public class Tickets {

    //Amount of regular tickets
    private int nbRegulars;

    //Amount of junior tickets
    private int nbJuniors;

    //Amount of senior tickets
    private int nbSeniors;

    //Amount of daily tickets
    private int nbDailies;

    //Amount of weekly tickets
    private int nbWeeklies;


    /**
     * Default constructor
     */
    public Tickets() {
    }

    /**
     * Constructor with all 5 types of tickets
     *
     * @param nbRegulars Amount of regular tickets
     * @param nbJuniors  Amount of junior tickets
     * @param nbSeniors  Amount of senior tickets
     * @param nbDailies  Amount of daily tickets
     * @param nbWeeklies Amount of weekly tickets
     */
    public Tickets(int nbRegulars, int nbJuniors, int nbSeniors, int nbDailies, int nbWeeklies) {
        this.nbRegulars = nbRegulars;
        this.nbJuniors = nbJuniors;
        this.nbSeniors = nbSeniors;
        this.nbDailies = nbDailies;
        this.nbWeeklies = nbWeeklies;
    }

    /**
     * Copy constructor to copy the number of tickets from another Tickets object
     *
     * @param _tickets Other tickets object to copy data from
     */
    public Tickets(Tickets _tickets) {
        this.nbRegulars = _tickets.getNbRegulars();
        this.nbJuniors = _tickets.getNbJuniors();
        this.nbSeniors = _tickets.getNbSeniors();
        this.nbDailies = _tickets.getNbDailies();
        this.nbWeeklies = _tickets.getNbWeeklies();
    }

    //region Setters & Getters
    public int getNbRegulars() {
        return nbRegulars;
    }

    public void setNbRegulars(int nbRegulars) {
        this.nbRegulars = nbRegulars;
    }

    public int getNbJuniors() {
        return nbJuniors;
    }

    public void setNbJuniors(int nbJuniors) {
        this.nbJuniors = nbJuniors;
    }

    public int getNbSeniors() {
        return nbSeniors;
    }

    public void setNbSeniors(int nbSeniors) {
        this.nbSeniors = nbSeniors;
    }

    public int getNbDailies() {
        return nbDailies;
    }

    public void setNbDailies(int nbDailies) {
        this.nbDailies = nbDailies;
    }

    public int getNbWeeklies() {
        return nbWeeklies;
    }

    public void setNbWeeklies(int nbWeeklies) {
        this.nbWeeklies = nbWeeklies;
    }
    //endregion

    /**
     * Amount of tickets for each ticket type, in the same format for each type, where each type is on a new line : 'nbRegulars'x Regulars ('fare'$)
     *
     * @return The ticket distribution
     */
    @Override
    public String toString() {
        return "\t" + nbRegulars + "x Regulars  (" + String.format("%.2f", TicketType.REGULAR.getFare()) + "$)\n" +
                "\t" + nbSeniors + "x Seniors   (" + String.format("%.2f", TicketType.SENIOR.getFare()) + "$)\n" +
                "\t" + nbJuniors + "x Juniors   (" + String.format("%.2f", TicketType.JUNIOR.getFare()) + "$)\n" +
                "\t" + nbDailies + "x Dailies   (" + String.format("%.2f", TicketType.DAILY.getFare()) + "$)\n" +
                "\t" + nbWeeklies + "x Weeklies  (" + String.format("%.2f", TicketType.WEEKLY.getFare()) + "$)\n";
    }

    /**
     * Check if another object equals this one, meaning that they must have the same ticket distribution to equal
     *
     * @param o Other object
     * @return True if they have the same ticket distribution, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; //If o is this object, then it's obviously true

        if (o == null || getClass() != o.getClass())
            return false; //If o is null or the class doesn't match Tickets, it's false

        //Check if each ticket quantity is the same between both objects
        Tickets tickets = (Tickets) o;
        return nbRegulars == tickets.nbRegulars && nbJuniors == tickets.nbJuniors && nbSeniors == tickets.nbSeniors && nbDailies == tickets.nbDailies && nbWeeklies == tickets.nbWeeklies;
    }

    /**
     * Add tickets to each ticket type
     *
     * @param nbReg Amount of regular tickets to add
     * @param nbJun Amount of junior tickets to add
     * @param nbSen Amount of senior tickets to add
     * @param nbDai Amount of daily tickets to add
     * @param nbWee Amount of weekly tickets to add
     */
    public void addTickets(int nbReg, int nbJun, int nbSen, int nbDai, int nbWee) {
        nbRegulars += nbReg;
        nbJuniors += nbJun;
        nbSeniors += nbSen;
        nbDailies += nbDai;
        nbWeeklies += nbWee;
    }

    /**
     * Calculate the total value of the tickets based on the fare of each ticket type
     * @return Total value of tickets
     */
    public double ticketsTotal() {
        return nbRegulars * TicketType.REGULAR.getFare()
                + nbJuniors * TicketType.JUNIOR.getFare()
                + nbSeniors * TicketType.SENIOR.getFare()
                + nbDailies * TicketType.DAILY.getFare()
                + nbWeeklies * TicketType.WEEKLY.getFare();
    }
}

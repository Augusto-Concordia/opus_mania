// -------------------------------------------------------
// Assignment 4
// Written by: Michaël Gugliandolo   40213419
//             Augusto Mota Pinheiro 40208020
// For COMP 248 Section EC – Fall 2021
// -------------------------------------------------------

//Ticketbooth simulator that handles tickets and OPUS cards
//Main menu of 10 options, where the user can decide to view the content of ticketbooths, compare ticketbooths, manage OPUS cards or add tickets
//Verify user's answers and prompt again until user enters valid answers

import enums.OPUSCardType;

import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Driver class
 */
public class Main {

    public static void main(String[] args) {

        //Scanner for user's input through the console
        Scanner keyInput = new Scanner(System.in);

        //User's choice for the main menu; number between 0 and 9 inclusively
        int choice;

        //To know if user wants to continue or quit; true if user is continuing, false is user wants to quit
        boolean isContinuing = true;

        //Ticketbooth array containing all 5 ticketbooths; ticketbooth content is hard coded is method fillTicketbooth
        //Ticketbooth #1 is at index 0, #2 is at index 1, etc.
        Ticketbooth[] ticketbooths = new Ticketbooth[5];
        fillTicketbooth(ticketbooths);

        //Welcome message
        System.out.println("""
                =================================================================================
                        
                          Welcome to Concordia 2021 Fall Geek's Ticketbooth Application
                        
                =================================================================================""");

        //Loop the menu until the user decides to quit
        do {
            //When needed, index of the ticketbooth selected by the user; equals 0 when ticketbooth #1 is selected, 1 when #2 is selected, etc.
            int selectedIndex;

            //Print out the main menu
            printMenu();

            //Read user's choice
            choice = keyInput.nextInt();

            //Perform different actions based on user's choice
            switch (choice) {
                case 1: //See content of all ticketbooths
                    printAllTicketbooths(ticketbooths);
                    break;
                case 2: //See content of one ticketbooth
                    selectedIndex = selectTicketbooth(ticketbooths, keyInput, "Which Ticketbooth do you want to see the content of?");
                    printSingleTicketBooth(ticketbooths, selectedIndex);
                    break;
                case 3: //List ticketbooths with the same tickets value
                    findValuePairs(ticketbooths);
                    break;
                case 4: //List ticketbooths with same tickets distribution
                    findDistributionPairs(ticketbooths);
                    break;
                case 5: //List ticketbooths with the same tickets value and same number of OPUS cards
                    findValueOPUSPairs(ticketbooths);
                    break;
                case 6: //Add OPUS card
                    selectedIndex = selectTicketbooth(ticketbooths, keyInput, "To which Ticketbooth do you want to add a card?");
                    addOPUSToTickebooth(ticketbooths, selectedIndex, keyInput);
                    break;
                case 7: //Remove OPUS card
                    selectedIndex = selectTicketbooth(ticketbooths, keyInput, "From which Ticketbooth do you want to remove a card?");
                    removeOPUSFromTicketbooth(ticketbooths, selectedIndex, keyInput);
                    break;
                case 8: //Update expiry date of OPUS card
                    selectedIndex = selectTicketbooth(ticketbooths, keyInput, "From which Ticketbooth do you want to update a card?");
                    updateOPUSOnTicketbooth(ticketbooths, selectedIndex, keyInput);
                    break;
                case 9: //Add tickets to a ticketbooth
                    selectedIndex = selectTicketbooth(ticketbooths, keyInput, "To which Ticketbooth do you want to add tickets?");
                    addTickets(ticketbooths, selectedIndex, keyInput);
                    break;
                case 0: //Quit
                    isContinuing = false;
                default: //Not a valid answer
                    System.out.println("Sorry, that is not a valid choice. Please try again!");
            }
        }
        while (isContinuing);

        //Close scanner
        keyInput.close();
        //Closing message
        System.out.println("Thank you for using Concordia Fall Geek's Ticketbooth application!");
    }

    /**
     * Print out the main menu
     */
    private static void printMenu() {
        System.out.print("""
                                    
                What would you like to do?
                  1. See the content of all Ticketboohs
                  2. See the content of one Ticketbooth
                  3. List Ticketbooths with same amount of tickets' values
                  4. List Ticketbooths with same Tickets amount
                  5. List Tiketbooths with same amount of tickets values and same number of OPUS cards
                  6. Add a OPUS card to an existing Ticketbooth
                  7. Remove an existing OPUS card from a Ticketbooth
                  8. Update the expiry date of an existing OPUS card
                  9. Add Tickets to a Ticketbooth
                  0. To quit
                  
                Please enter your choice and press <Enter>:\040""");
    }

    /**
     * Fill the ticketbooth array with hard coded values
     *
     * @param boothToFill Ticketbooth array of length 5 to fill
     */
    private static void fillTicketbooth(Ticketbooth[] boothToFill) {
        Tickets tickets; //Contains the tickets for the ticketbooths
        OPUSCard[] opusCards; //Contains the OPUS cards for the ticketbooths

        //For ticketbooth 0 and 1
        tickets = new Tickets(3, 1, 4, 1, 5);
        opusCards = new OPUSCard[2];
        opusCards[0] = new OPUSCard(OPUSCardType.REM, "Dr. Motasol", 9, 2026);
        opusCards[1] = new OPUSCard(OPUSCardType.RTL, "M. Levitator", 5, 2035);

        boothToFill[0] = new Ticketbooth(tickets, opusCards);
        boothToFill[1] = new Ticketbooth(tickets, opusCards);

        //For ticketbooth 2
        tickets = new Tickets(8, 16, 9, 7, 2);
        opusCards = new OPUSCard[3];
        opusCards[0] = new OPUSCard(OPUSCardType.STL, "Impératrice", 9, 2032);
        opusCards[1] = new OPUSCard(OPUSCardType.STM, "LE Chesseur", 3, 2028);
        opusCards[2] = new OPUSCard(OPUSCardType.TRAMREM, "E. Crusher", 4, 2026);

        boothToFill[2] = new Ticketbooth(tickets, opusCards);

        //For ticketbooth 3 and 4
        tickets = new Tickets(2, 6, 4, 3, 3);
        opusCards = new OPUSCard[0];

        boothToFill[3] = new Ticketbooth(tickets, opusCards);
        boothToFill[4] = new Ticketbooth(tickets, opusCards);
    }

    /**
     * Prompt the user to choose a specific ticketbooth
     *
     * @param ticketbooths Array of ticketbooths
     * @param keyInput     Scanner for user's input
     * @param question     Question to ask the user (method will add "Number between 1 and {length}: " after the question)
     * @return Index of the ticketbooth, where 0 is ticketbooth #1, 1 is ticketbooth #2, etc.
     */
    private static int selectTicketbooth(Ticketbooth[] ticketbooths, Scanner keyInput, String question) {
        int answer; //User's answer
        System.out.print(question + " (Number between 1 and " + ticketbooths.length + "): "); //Prompt user
        answer = keyInput.nextInt(); //Read user's answer

        while (answer < 1 || answer >= ticketbooths.length + 1) { //While the answer isn't between 1 and ticketbooths' length, prompt again
            System.out.println("Sorry, there isn't any Ticketbooth number " + answer + ".");
            System.out.print("Please try again! (Number between 1 and " + ticketbooths.length + "): ");
            answer = keyInput.nextInt();
        }

        return answer - 1; //Subtract one to get the valid index
    }

    /**
     * Request new expiration month and year
     * @param keyInput Scanner for user's input
     * @return Int array where the first index is the month and the 2nd index is the year
     */
    private static int[] requestMonthAndYear(Scanner keyInput) {
        int monthNumber; //Month entered by user
        int year; //Year entered by user

        do { //Loop until valid answers
            System.out.print("\t--> Expiry month number and year (separated by a space): ");
            monthNumber = keyInput.nextInt();
            year = keyInput.nextInt();
        //Verify that the month is between 1 and 12 inclusively, that the year has 4 digits, and that the date entered is in the future
        } while (monthNumber > 12 || monthNumber < 1 || String.valueOf(year).length() != 4 || LocalDateTime.now().isAfter(LocalDateTime.of(year, monthNumber, LocalDateTime.now().getDayOfMonth(), 0, 0)));

        return new int[]{monthNumber, year};
    }

    /**
     * Print out the ticket distribution and OPUS cards of all the ticketbooths
     * @param ticketbooths Array of ticketbooths
     */
    private static void printAllTicketbooths(Ticketbooth[] ticketbooths) {
        System.out.println("Content of each Ticketbooth:");
        System.out.print("---------------------");
        for (int i = 0; i < ticketbooths.length; i++) { //Loop through the array
            System.out.println();
            System.out.println("Ticketbooth #" + (i + 1) + ":");
            System.out.println(ticketbooths[i].toString());
        }
    }

    /**
     * Print out ticket distribution of one ticketbooth
     * @param ticketbooths Array of ticketbooths
     * @param indexOfBooth Index of desired ticketbooth in the array
     */
    private static void printSingleTicketBooth(Ticketbooth[] ticketbooths, int indexOfBooth) {
        System.out.println("\nTicketbooth #" + indexOfBooth + ":");
        System.out.println(ticketbooths[indexOfBooth].toString());
    }

    /**
     * Find and print out the pairs of ticketbooths that have the same total value of tickets
     * @param ticketbooths Array of ticketbooths
     */
    private static void findValuePairs(Ticketbooth[] ticketbooths) {
        System.out.println("List of Ticketbooths with the same tickets value:\n");
        for (int first = 0; first < ticketbooths.length - 1; first++) { //Loop through the ticketbooths until the second to last one, since there's no point in checking if the last ticketbooth has the same value with itself
            for (int second = first + 1; second < ticketbooths.length; second++) { //Compare the ticketbooth selected in the first loop with the following ticketbooths in the array, so 'second' starts at 'first' + 1 and stops at the last ticketbooth
                if (ticketbooths[first].totalValueOTickets() == ticketbooths[second].totalValueOTickets()) {
                    System.out.println("\tTicketbooths " + (first + 1) + " and " + (second + 1) + " both have " + String.format("%.2f", ticketbooths[first].totalValueOTickets()) + "$");
                }
            }
        }
    }

    /**
     * Find and print out the pairs of ticketbooths that have the same ticket distribution
     * @param ticketbooths Array of ticketbooths
     */
    private static void findDistributionPairs(Ticketbooth[] ticketbooths) {
        System.out.println("List of Ticketbooths with the same ticket distribution:\n");
        for (int first = 0; first < ticketbooths.length - 1; first++) { //See 'findValuePairs' for comments on loops
            for (int second = first + 1; second < ticketbooths.length; second++) {
                if (ticketbooths[first].getTickets().equals(ticketbooths[second].getTickets())) {
                    System.out.println("\tTicketbooths " + (first + 1) + " and " + (second + 1) + " both have\n" + ticketbooths[first].ticketsBreakdown());
                }
            }
        }
    }

    /**
     * Find and print out the pairs of ticketbooths that have the same total value of tickets and the same ticket distribution
     * @param ticketbooths Array of ticketbooths
     */
    private static void findValueOPUSPairs(Ticketbooth[] ticketbooths) {
        System.out.println("List of Ticketbooths with the same tickets value and same number of OPUS cards:\n");
        for (int first = 0; first < ticketbooths.length - 1; first++) { //See 'findValuePairs' for comments on loops
            for (int second = first + 1; second < ticketbooths.length; second++) {
                if (ticketbooths[first].equals(ticketbooths[second])) {
                    System.out.println("\tTicketbooths " + (first + 1) + " and " + (second + 1));
                }
            }
        }
    }

    /**
     * Prompt user for the necessary information for an OPUS card and add a new OPUS card to a ticketbooth
     * @param ticketbooths Array of ticketbooth
     * @param boothIndex   Index of the selected ticketbooth, where 0 is for ticketbooth #1, 1 is for tiketbooth #2, etc.
     * @param keyInput     Scanner for user's input
     */
    private static void addOPUSToTickebooth(Ticketbooth[] ticketbooths, int boothIndex, Scanner keyInput) {

        System.out.println("\nPlease enter the following information so that we may complete the transaction:");

        String cardType = ""; //OPUS card type chosen by the user
        String fullName; //Full name of the user

        keyInput.nextLine();

        do { //Loops until the user enters a valid OPUS card type
            if (!cardType.equals("")) System.out.println("That is an invalid card type..."); //If cardType isn't null, then this isn't the first time in the loop
            System.out.print("\t--> Type of OPUS card (STM, STL, RTL, REM, TRAMREM): "); //Prompt user
            cardType = keyInput.nextLine();
        } while (!OPUSCardType.isValidString(cardType)); //Test if answer is valid

        System.out.print("\t--> Full name on OPUS card: "); //Prompt user for full name
        fullName = keyInput.nextLine();

        int[] monthYearCombo = requestMonthAndYear(keyInput); //Prompt user for expiration date

        int numbOCards = ticketbooths[boothIndex].addOPUSCard(new OPUSCard(OPUSCardType.valueOf(cardType), fullName, monthYearCombo[0], monthYearCombo[1])); //Add new OPUS card to selected ticketbooth

        System.out.println("You now have " + numbOCards + " OPUS card" + (numbOCards > 1 ? "s" : "") + "!"); //Print out new number of OPUS cards in selected ticketbooth
    }

    /**
     * Remove an OPUS card from a ticketbooth
     * @param ticketbooths Array of ticketbooths
     * @param boothIndex   Index of the selected ticketbooth, where 0 is for ticketbooth #1, 1 is for tiketbooth #2, etc.
     * @param keyInput     Scanner for user's input
     */
    private static void removeOPUSFromTicketbooth(Ticketbooth[] ticketbooths, int boothIndex, Scanner keyInput) {

        if (ticketbooths[boothIndex].getNumbOfOPUSCards() == 0) { //Check if ticketbooth doesn't have any OPUS cards
            System.out.println("Ticketbooth has no cards!");
            return;
        }

        System.out.println(ticketbooths[boothIndex].OPUSCardsBreakdown()); //Print out the OPUS cards of the selected ticketbooth

        int cardToRemoveIndex = -1; //Index of OPUS card to remove

        do { //Loop until user enters valid choice
            if (cardToRemoveIndex != -1) System.out.println("That is an invalid card number..."); //If equals -1, then it's the first time through the loop
            System.out.print("\nWhich card would you like to remove? (Number between " + 1 + " and " + ticketbooths[boothIndex].getNumbOfOPUSCards() + "): ");

            cardToRemoveIndex = keyInput.nextInt();
        } while (cardToRemoveIndex > ticketbooths[boothIndex].getOpusCards().length + 1 || cardToRemoveIndex < 1); //Check if the answer is greater than the number of OPUS cards or less than 1

        if (ticketbooths[boothIndex].removeOPUSCard(cardToRemoveIndex - 1)) { //Index - 1 to get the correct index in the array
            System.out.println("Card was removed successfully! Ticketbooth now contains: ");
            System.out.println(ticketbooths[boothIndex].OPUSCardsBreakdown());
        } else { //Method 'removeOPUSCard' returned false, meaning that the OPUS card hasn't been removed
            System.out.println("Sorry, the card could not be removed...");
        }
    }

    /**
     * Update the expiration date of an OPUS card
     * @param ticketbooths Array of ticketbooths
     * @param boothIndex   Index of the selected ticketbooth, where 0 is for ticketbooth #1, 1 is for tiketbooth #2, etc.
     * @param keyInput     Scanner for user's input
     */
    private static void updateOPUSOnTicketbooth(Ticketbooth[] ticketbooths, int boothIndex, Scanner keyInput) {

        if (ticketbooths[boothIndex].getNumbOfOPUSCards() == 0) { //Check if ticketbooth doesn't have any OPUS cards
            System.out.println("Ticketbooth has no cards!");
            return;
        }

        System.out.println(ticketbooths[boothIndex].OPUSCardsBreakdown()); //Print out the OPUS cards of the selected ticketbooth

        int cardToUpdateIndex = -1; //Index of OPUS card to update

        do { //Loop until user enters valid choice
            if (cardToUpdateIndex != -1) System.out.println("Sorry, that is an invalid card number..."); //If equals -1, then it's the first time through the loop
            System.out.print("\nWhich card would you like to update? (Number between 1 and " + ticketbooths[boothIndex].getNumbOfOPUSCards() + "): ");

            cardToUpdateIndex = keyInput.nextInt();
        } while (cardToUpdateIndex > ticketbooths[boothIndex].getOpusCards().length + 1 || cardToUpdateIndex < 1); //Check if the answer is greater than the number of OPUS cards or less than 1

        int[] monthYearCombo = requestMonthAndYear(keyInput); //Request a new expiration month and year from the user

        ticketbooths[boothIndex].updateOPUS(cardToUpdateIndex - 1, monthYearCombo[0], monthYearCombo[1]); //Update the OPUS card

        System.out.println("\nCard was updated successfully!");
        System.out.println("\t" + ticketbooths[boothIndex].getOpusCards()[cardToUpdateIndex - 1].toString()); //Print out the information of the updated OPUS card
    }

    /**
     * Add tickets to a ticketbooth
     * @param ticketbooths Array of ticketbooths
     * @param boothIndex   Index of the selected ticketbooth, where 0 is for ticketbooth #1, 1 is for tiketbooth #2, etc.
     * @param keyInput     Scanner for user's input
     */
    private static void addTickets(Ticketbooth[] ticketbooths, int boothIndex, Scanner keyInput) {

        System.out.println("How many regular, junior, senior, daily and weekly to you want to add?"); //Prompt user
        System.out.print("(Enter 5 numbers, each separated by a space): ");

        int[] add = new int[5]; //Array where each index is how many tickets to add for each type; index 0 = regular / 1 = junior / 2 = senior / 3 = daily / 4 = weekly
        for (int i = 0; i < add.length; i++)
            add[i] = keyInput.nextInt();

        while (add[0] < 0 || add[1] < 0 || add[2] < 0 || add[3] < 0 || add[4] < 0) { //Loop until all the answers are positive
            System.out.println("Please enter positive numbers!");
            System.out.print("(Enter 5 numbers, each separated by a space): ");
            for (int i = 0; i < add.length; i++)
                add[i] = keyInput.nextInt();
        }
        double newTicketsValue = ticketbooths[boothIndex].addTickets(add[0], add[1], add[2], add[3], add[4]); //Add tickets
        System.out.println("This ticketbooth now has $" + newTicketsValue); //Print out new total value of tickets
    }
}


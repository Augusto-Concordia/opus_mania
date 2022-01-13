package enums;

/**
 * Enum containing the different OPUS card types
 */
public enum OPUSCardType {
    STM("STM"),
    RTL("RTL"),
    STL("STL"),
    REM("REM"),
    TRAMREM("TRAMREM");

    //Name of each type
    private final String NAME;

    /**
     * Constructor with the name of the type in parameter
     *
     * @param name Name of the type of OPUS card
     */
    OPUSCardType(String name) {
        this.NAME = name;
    }

    /**
     * Return the name of the enum's value
     *
     * @return The name
     */
    @Override
    public String toString() {
        return NAME;
    }

    /**
     * Test if a string corresponds to a value in the enum
     *
     * @param testString String to test
     * @return True if the string corresponds to a value, false if not
     */
    public static boolean isValidString(String testString) {
        return testString.equals(OPUSCardType.STM.NAME) || testString.equals(OPUSCardType.STL.NAME) || testString.equals(OPUSCardType.RTL.NAME) || testString.equals(OPUSCardType.REM.NAME) || testString.equals(OPUSCardType.TRAMREM.NAME);
    }
}

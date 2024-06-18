package action;

public enum PossibleCommands {
    KYS("kys"),
    KILL("kill"),
    ENSLAVE("enslave"),
    RANDOMDEATH("randomdeath");
    private final String command;
    PossibleCommands(String command){this.command = command;}
    public String getValue(){return command;}
    public static PossibleCommands getName(String value) {
        for (PossibleCommands code : PossibleCommands.values()) {
            if (code.getValue().equals(value)) {
                return code;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }
}

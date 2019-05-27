package menu.util.setting;

public enum RestCommand {
    ADD,EDIT,DELETE;

    public static RestCommand getRestCommand(String test) {
        try {
            return RestCommand.valueOf(test);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static boolean contains(String test) {
        for (RestCommand restCommand : RestCommand.values()) {
            if (restCommand.name().equals(test)) {
                return true;
            }
        }
        return false;
    }
}

package member.util.setting;

public enum MemberCommand {
    OFFLINE,CUSTOMER,DELIVER_OFF,DELIVER_ON,DELIVER_BUSY,USER_BAN,ADMINISTRATOR,PUSHING,DELETE;

    public static MemberCommand getMemberCommand(String test) {
        try {
            return MemberCommand.valueOf(test);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static boolean contains(String test) {
        for (MemberCommand memberCommand : MemberCommand.values()) {
            if (memberCommand.name().equals(test)) {
                return true;
            }
        }
        return false;
    }
}

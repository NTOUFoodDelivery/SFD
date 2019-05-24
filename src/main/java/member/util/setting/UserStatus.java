package member.util.setting;

public enum UserStatus {
    OFFLINE,CUSTOMER,DELIVER_OFF,DELIVER_ON,DELIVER_BUSY,USER_BAN,ADMINISTRATOR,PUSHING;

    public static UserStatus getUserStatus(String test) {
        try {
            return UserStatus.valueOf(test);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static boolean contains(String test) {
        for (UserStatus userStatus : UserStatus.values()) {
            if (userStatus.name().equals(test)) {
                return true;
            }
        }
        return false;
    }
}

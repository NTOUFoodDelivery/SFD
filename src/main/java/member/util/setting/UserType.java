package member.util.setting;

public enum UserType {
    Customer,Customer_and_Deliver,Administrator;

    public static UserType getUserType(String test) {
        try {
            return UserType.valueOf(test);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static boolean contains(String test) {
        for (UserType userType : UserType.values()) {
            if (userType.name().equals(test)) {
                return true;
            }
        }
        return false;
    }
}

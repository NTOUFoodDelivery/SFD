package member.util.setting;

public enum UserType {
  Customer, Customer_and_Deliver, Administrator, Deliver;

  /**
   * <p>if this command is exist , get feedback command.</p>
   *
   * @param test user type
   */
  public static UserType getUserType(String test) {
    try {
      return UserType.valueOf(test);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  /**
   * <p>if this command is exist , return true.</p>
   *
   * @param test user type
   */
  public static boolean contains(String test) {
    for (UserType userType : UserType.values()) {
      if (userType.name().equals(test)) {
        return true;
      }
    }
    return false;
  }
}

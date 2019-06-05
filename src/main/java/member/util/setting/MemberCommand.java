package member.util.setting;

public enum MemberCommand {
  OFFLINE, CUSTOMER, DELIVER_OFF, DELIVER_ON,
  DELIVER_BUSY, USER_BAN, ADMINISTRATOR, PUSHING, DELETE;

  /**
   * <p>if this command is exist , get feedback command.</p>
   *
   * @param test member command
   */
  public static MemberCommand getMemberCommand(String test) {
    try {
      return MemberCommand.valueOf(test);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  /**
   * <p>if this command is exist , return true.</p>
   *
   * @param test member command
   */
  public static boolean contains(String test) {
    for (MemberCommand memberCommand : MemberCommand.values()) {
      if (memberCommand.name().equals(test)) {
        return true;
      }
    }
    return false;
  }
}

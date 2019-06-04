package menu.util.setting;

public enum RestCommand {
  ADD, EDIT, DELETE;

  /**
   * <p>if this command is exist , get feedback command.</p>
   *
   * @param test rest command
   */
  public static RestCommand getRestCommand(String test) {
    try {
      return RestCommand.valueOf(test);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  /**
   * <p>if this command is exist , return true.</p>
   *
   * @param test rest command
   */
  public static boolean contains(String test) {
    for (RestCommand restCommand : RestCommand.values()) {
      if (restCommand.name().equals(test)) {
        return true;
      }
    }
    return false;
  }
}

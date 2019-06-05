package member.util.setting;

public enum FeedbackCommand {
  CREATE, REPLY, SHOW;

  /**
   * <p>
   * if this command is exist , get feedback command.
   * </p>
   *
   * @param test feedback command
   */
  public static FeedbackCommand getFeedbackCommand(String test) {
    try {
      return FeedbackCommand.valueOf(test);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  /**
   * <p>
   * if this command is exist , return true.
   * </p>
   *
   * @param test feedback command
   */
  public static boolean contains(String test) {
    for (FeedbackCommand feedbackCommand : FeedbackCommand.values()) {
      if (feedbackCommand.name().equals(test)) {
        return true;
      }
    }
    return false;
  }
}

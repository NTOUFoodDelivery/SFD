package member.util.setting;

public enum FeedbackCommand {
    CREATE,REPLY,SHOW;

    public static FeedbackCommand getFeedbackCommand(String test) {
        try {
            return FeedbackCommand.valueOf(test);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static boolean contains(String test) {
        for (FeedbackCommand feedbackCommand : FeedbackCommand.values()) {
            if (feedbackCommand.name().equals(test)) {
                return true;
            }
        }
        return false;
    }
}

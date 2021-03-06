package member.model.javabean;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Feedback {

  /**
   * Feedback_Id : 1234 User_Id : 4567 Content : hi Back_Content : hi~.
   */

  @SerializedName("Feedback_Id")
  private Long feedbackId;
  @SerializedName("User_Id")
  private Long userID;
  @SerializedName("Content")
  private String content;
  @SerializedName("Back_Content")
  private String backContent;

  public Long getFeedbackId() {
    return feedbackId;
  }

  public void setFeedbackId(Long feedbackId) {
    this.feedbackId = feedbackId;
  }

  public Long getUserID() {
    return userID;
  }

  public void setUserID(Long userID) {
    this.userID = userID;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getBackContent() {
    return backContent;
  }

  public void setBackContent(String backContent) {
    this.backContent = backContent;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("feedbackId", feedbackId)
        .append("userID", userID)
        .append("content", content)
        .append("backContent", backContent)
        .toString();
  }
}

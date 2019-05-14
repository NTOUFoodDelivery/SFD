package member.model.javabean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedBack {

	@SerializedName("Feedback_Id")
	@Expose
	private long feedBackID;
	@SerializedName("User_Id")
	@Expose
	private long userID;
	@SerializedName("Content")
	@Expose
	private String content;
	@SerializedName("Back_Content")
	@Expose
	private String backContent;


	public long getFeedBackID() {
		return feedBackID;
	}
	
	public void setFeedBackID(long feedBackID) {
		this.feedBackID = feedBackID;
	}
	
	public long getUserID() {
		return userID;
	}
	
	public void setUserID(long userID) {
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
}

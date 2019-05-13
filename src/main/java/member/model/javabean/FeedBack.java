package member.model.javabean;

public class FeedBack {

	private long feedBackID;
	private long userID;
	private String content;
	
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
	
	

}

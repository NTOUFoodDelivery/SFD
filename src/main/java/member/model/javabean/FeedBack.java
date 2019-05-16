package member.model.javabean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedBack {


	/**
	 * query : {"command":"add","what":"feedBack"}
	 * result : [{"Feedback_Id":1234,"User_Id":4567,"Content":"hi","Back_Content":"hi~"}]
	 */

	private QueryBean query;
	private List<ResultBean> result;

	public QueryBean getQuery() {
		return query;
	}

	public void setQuery(QueryBean query) {
		this.query = query;
	}

	public List<ResultBean> getResult() {
		return result;
	}

	public void setResult(List<ResultBean> result) {
		this.result = result;
	}

	public static class QueryBean {
		/**
		 * command : add
		 * what : feedBack
		 */

		private String command;
		private String what;

		public String getCommand() {
			return command;
		}

		public void setCommand(String command) {
			this.command = command;
		}

		public String getWhat() {
			return what;
		}

		public void setWhat(String what) {
			this.what = what;
		}
	}

	public static class ResultBean {
		/**
		 * Feedback_Id : 1234
		 * User_Id : 4567
		 * Content : hi
		 * Back_Content : hi~
		 */

		@SerializedName("Feedback_Id")
		private Long facebackID;
		@SerializedName("User_Id")
		private Long userID;
		@SerializedName("Content")
		private String content;
		@SerializedName("Back_Content")
		private String backContent;

		public Long getFacebackID() {
			return facebackID;
		}

		public void setFacebackID(Long facebackID) {
			this.facebackID = facebackID;
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
	}
}


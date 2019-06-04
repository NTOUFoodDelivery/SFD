package util.javabean;

public class StatusCodeResponse {

  /**
   * statusCode : 200 message : hi time : 23d4rswty.
   */

  private int statusCode;
  private String time;
  private String message;

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}

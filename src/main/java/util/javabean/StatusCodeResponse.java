package util.javabean;

public class StatusCodeResponse {

  /**
   * result : sdf
   * statusCode : 200 message : hi time : 23d4rswty.
   */

  private String result;
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

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }
}

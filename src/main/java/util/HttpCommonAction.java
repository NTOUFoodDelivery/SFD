package util;

import com.sun.xml.bind.v2.TODO;
import java.io.BufferedReader;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;

import util.javabean.StatusCodeResponse;

public class HttpCommonAction {

  /**
   * <p>用來取得doPost請求的body.</p>
   *
   * @param httpServletRequestGetReaderBuffer BufferedReader
   */
  public static String getRequestBody(BufferedReader httpServletRequestGetReaderBuffer) {
    StringBuffer jb = new StringBuffer();
    String line = null;
    try {
      while ((line = httpServletRequestGetReaderBuffer.readLine()) != null) {
        jb.append(line);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return jb.toString();
  }

  /**
   * <p>製造 response 的訊息.</p>
   *
   * @param success 成功與否
   * @param msg 回傳訊息
   */
  public static StatusCodeResponse generateStatusResponse(boolean success, String msg) {
    StatusCodeResponse statusCodeResponse = new StatusCodeResponse();
    if (success) {
      statusCodeResponse.setStatusCode(HttpServletResponse.SC_OK);
    } else {
      statusCodeResponse.setStatusCode(HttpServletResponse.SC_NOT_FOUND);
    }
    statusCodeResponse.setMessage(msg);
    statusCodeResponse.setTime(new Date().toString());
    return statusCodeResponse;
  }
}

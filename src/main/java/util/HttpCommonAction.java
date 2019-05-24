package util;

import util.javabean.StatusCodeResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.Date;

public class HttpCommonAction {
    // 用來取得doPost請求的body
    public static String getRequestBody(BufferedReader httpServletRequestGetReaderBuffer) {
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            while ((line = httpServletRequestGetReaderBuffer.readLine()) != null)
                jb.append(line);
        } catch (Exception e) {

        }
        return jb.toString();
    }

    public static StatusCodeResponse getStatusCodeResponse(boolean success){
        StatusCodeResponse statusCodeResponse = new StatusCodeResponse();
        if(success){
            statusCodeResponse.setStatusCode(HttpServletResponse.SC_OK);
        }else{
            statusCodeResponse.setStatusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        statusCodeResponse.setTime(new Date().toString());
        return statusCodeResponse;
    }
}

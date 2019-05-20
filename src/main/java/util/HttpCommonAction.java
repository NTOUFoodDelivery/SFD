package util;

import java.io.BufferedReader;

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

}

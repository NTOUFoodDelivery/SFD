package menu.controller.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class ShowMenuServletTest {
//    private HttpServletRequest request;
//
//    @Before
//    public void setUp() throws Exception {
////        request = mock(HttpServletRequest.class);
////        request.setCharacterEncoding("UTF-8");
////        String json = "{  \n" +
////                "   \"restName\":\"Apple203\",\n" +
////                "   \"restAddress\":\"中正路111號\"\n" +
////                "}";
////        when(request.getReader()).thenReturn(
////                new BufferedReader(new StringReader(json)));
//    }
//
//    @After
//    public void tearDown() throws Exception {
//    }
//
//    @Test
//    public void doPost() {
////        Gson gson = new Gson();
////        RestMenuReq restMenuReq = null;
////        try {
////            restMenuReq = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()), RestMenuReq.class);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////
////        String restName = restMenuReq.getRestName();
////        assertEquals(restName,"Apple203");
////        String restAddress = restMenuReq.getRestAddress();
////        assertEquals(restAddress,"中正路111號");
////
////        JsonObject jsonObject = RestDAO.searchRestMenu(restName,restAddress);
////
////        String expect = "{\n" +
////                "    \"result\": [\n" +
////                "        {\n" +
////                "            \"Food_Id\": \"test1\",\n" +
////                "            \"Food_Name\": \"test1\",\n" +
////                "            \"Rest_Id\": \"test1\",\n" +
////                "            \"Cost\": \"test1\",\n" +
////                "            \"Description\": \"test1\",\n" +
////                "            \"Image\": \"test1\"\n" +
////                "        }\n" +
////                "    ]\n" +
////                "}";
////        ArrayValueMatcher<Object> arrValMatch = new ArrayValueMatcher<>(new CustomComparator(
////                JSONCompareMode.NON_EXTENSIBLE,
////                new Customization("result[*].Food_Id",(o1, o2) -> true),
////                new Customization("result[*].Food_Name",(o1, o2) -> true),
////                new Customization("result[*].Rest_Id",(o1, o2) -> true),
////                new Customization("result[*].Cost",(o1, o2) -> true),
////                new Customization("result[*].Description",(o1, o2) -> true),
////                new Customization("result[*].Image",(o1, o2) -> true)));
////
////        Customization arrayValueMatchCustomization = new Customization("result", arrValMatch);
////        CustomComparator customArrayValueComparator = new CustomComparator(
////                JSONCompareMode.NON_EXTENSIBLE,
////                arrayValueMatchCustomization);
////        try {
////            JSONAssert.assertEquals(expect, jsonObject.toString(),customArrayValueComparator);
////
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
//
//    }
//
//    @Test
//    public void doGet() {
//    }
}
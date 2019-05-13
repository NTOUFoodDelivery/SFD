package menu.controller.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import db.demo.dao.RestDAO;
import menu.model.request.javabean.RestMenuReq;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.ArrayValueMatcher;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import tool.HttpCommonAction;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShowMenuServletTest {
    private HttpServletRequest request;

    @Before
    public void setUp() throws Exception {
//        request = mock(HttpServletRequest.class);
//        request.setCharacterEncoding("UTF-8");
//        String json = "{  \n" +
//                "   \"restName\":\"Apple203\",\n" +
//                "   \"restAddress\":\"中正路111號\"\n" +
//                "}";
//        when(request.getReader()).thenReturn(
//                new BufferedReader(new StringReader(json)));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void doPost() {
//        Gson gson = new Gson();
//        RestMenuReq restMenuReq = null;
//        try {
//            restMenuReq = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()), RestMenuReq.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String restName = restMenuReq.getRestName();
//        assertEquals(restName,"Apple203");
//        String restAddress = restMenuReq.getRestAddress();
//        assertEquals(restAddress,"中正路111號");
//
//        JsonObject jsonObject = RestDAO.searchRestMenu(restName,restAddress);
//
//        String expect = "{\n" +
//                "    \"result\": [\n" +
//                "        {\n" +
//                "            \"Food_Id\": \"test1\",\n" +
//                "            \"Food_Name\": \"test1\",\n" +
//                "            \"Rest_Id\": \"test1\",\n" +
//                "            \"Cost\": \"test1\",\n" +
//                "            \"Description\": \"test1\",\n" +
//                "            \"Image\": \"test1\"\n" +
//                "        }\n" +
//                "    ]\n" +
//                "}";
//        ArrayValueMatcher<Object> arrValMatch = new ArrayValueMatcher<>(new CustomComparator(
//                JSONCompareMode.NON_EXTENSIBLE,
//                new Customization("result[*].Food_Id",(o1, o2) -> true),
//                new Customization("result[*].Food_Name",(o1, o2) -> true),
//                new Customization("result[*].Rest_Id",(o1, o2) -> true),
//                new Customization("result[*].Cost",(o1, o2) -> true),
//                new Customization("result[*].Description",(o1, o2) -> true),
//                new Customization("result[*].Image",(o1, o2) -> true)));
//
//        Customization arrayValueMatchCustomization = new Customization("result", arrValMatch);
//        CustomComparator customArrayValueComparator = new CustomComparator(
//                JSONCompareMode.NON_EXTENSIBLE,
//                arrayValueMatchCustomization);
//        try {
//            JSONAssert.assertEquals(expect, jsonObject.toString(),customArrayValueComparator);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }

    @Test
    public void doGet() {
    }
}
package menu.controller.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ShowRestInfoServletTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void doPost() {
    }

    @Test
    public void doGet() {
//        JsonObject jsonObject = RestDAO.searchRestInfo();
//        String expect = "{\n" +
//                "    \"result\": [\n" +
//                "        {\n" +
//                "            \"Rest_Id\": \"test1\",\n" +
//                "            \"Rest_Name\": \"test1\",\n" +
//                "            \"Rest_Address\": \"test1\",\n" +
//                "            \"Description\": \"test1\"\n" +
//                "        }\n" +
//                "    ]\n" +
//                "}";
//        ArrayValueMatcher<Object> arrValMatch = new ArrayValueMatcher<>(new CustomComparator(
//                JSONCompareMode.NON_EXTENSIBLE,
//                new Customization("result[*].Rest_Id",(o1, o2) -> true),
//                new Customization("result[*].Rest_Name",(o1, o2) -> true),
//                new Customization("result[*].Rest_Address",(o1, o2) -> true),
//                new Customization("result[*].Description",(o1, o2) -> true)));
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
}
package menu.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import menu.controller.service.RestInfoService;
import menu.model.javabean.Menu;
import menu.model.javabean.Rest;
import util.HttpCommonAction;

@WebServlet("/ShowMenuServlet")
public class ShowMenuServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    Gson gson = new GsonBuilder().disableHtmlEscaping()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
    Rest rest = gson
        .fromJson(HttpCommonAction.getRequestBody(request.getReader()), Rest.class); // rest

    RestInfoService restInfoService = new RestInfoService();
    String json = gson.toJson(restInfoService.getRestMenu(rest)); // 拿到 一家餐廳 的菜單
    restInfoService = null;
    gson = null;
    PrintWriter out = response.getWriter();
    out.print(json);
    out.flush();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    Gson gson = new GsonBuilder().disableHtmlEscaping()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
    Long restID = Long.parseLong(request.getParameter("restID"));

    ServletContext servletContext = request.getServletContext();
    Map<Long, List<Menu>> restMenuHashMap = (ConcurrentHashMap<Long, List<Menu>>) servletContext
        .getAttribute("restMenuHashMap"); // 拿到 servlet context 紀錄 各家餐廳 的菜單
    List<Menu> menuList = restMenuHashMap.get(restID);
    if (menuList == null) { // servlet context 還沒有 紀錄 任何餐廳 的 hash map 還沒紀錄該餐廳的 菜單
      RestInfoService restInfoService = new RestInfoService();
      menuList = restInfoService.getRestMenu(restID);
      restMenuHashMap.put(restID, menuList);
      restInfoService = null;
    } else {
      System.out.println("Get restMenuHashMap from servlet context!!");
    }

    String json = gson.toJson(menuList);
    gson = null;
    PrintWriter out = response.getWriter();
    out.print(json);
    out.flush();
  }
}

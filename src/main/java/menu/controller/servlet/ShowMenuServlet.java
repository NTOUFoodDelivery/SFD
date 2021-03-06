package menu.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import menu.controller.service.RestInfoService;
import menu.model.javabean.Rest;
import util.HttpCommonAction;

@WebServlet("/ShowMenuServlet")
public class ShowMenuServlet extends HttpServlet {


  // 用 名稱 地址 拿餐廳菜單
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

    PrintWriter out = response.getWriter();
    out.print(json);
    out.flush();
  }

  // 用 id 拿餐廳菜單
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    Gson gson = new GsonBuilder().disableHtmlEscaping()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
    Long restID = Long.parseLong(request.getParameter("restID"));

    RestInfoService restInfoService = new RestInfoService();

    String json = gson.toJson(restInfoService.getRestMenu(restID));
    restInfoService = null;

    PrintWriter out = response.getWriter();
    out.print(json);
    out.flush();
  }
}

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
import menu.util.setting.RestCommand;
import util.HttpCommonAction;

@WebServlet("/ModifyRestInfoServlet")
public class ModifyRestInfoServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    Gson gson = new GsonBuilder().disableHtmlEscaping()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

    RestCommand restCommand = RestCommand.getRestCommand(request.getParameter("cmd")); // cmd
    Rest rest = gson
        .fromJson(HttpCommonAction.getRequestBody(request.getReader()), Rest.class); // rest

    RestInfoService restInfoService = new RestInfoService();
    boolean result = restInfoService.modifyRestInfo(restCommand, rest);
    String json = gson.toJson(HttpCommonAction.generateStatusResponse(result,""));
    request.getServletContext().setAttribute("restInfoList", restInfoService.getRestInfo()); // 重新設定 新的餐廳物件
    restInfoService = null;

    PrintWriter out = response.getWriter();
    out.print(json);
    out.flush();

  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  }
}

package menu.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import menu.model.javabean.Rest;

@WebServlet("/ShowRestInfoServlet")
public class ShowRestInfoServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    Gson gson = new GsonBuilder().disableHtmlEscaping()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
    @SuppressWarnings("unchecked")
    List<Rest> restInfoList = (List<Rest>) request.getServletContext().getAttribute("restInfoList"); // 拿到 servlet context 的 所有餐廳
    String json = gson.toJson(restInfoList);

    PrintWriter out = response.getWriter();
    out.print(json);
    out.flush();
  }
}

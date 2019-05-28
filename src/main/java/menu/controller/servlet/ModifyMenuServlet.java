package menu.controller.servlet;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import menu.controller.service.RestInfoService;
import menu.model.javabean.Menu;
import menu.util.setting.RestCommand;
import util.HttpCommonAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/ModifyMenuServlet")
public class ModifyMenuServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

        RestCommand restCommand = RestCommand.getRestCommand(request.getParameter("cmd")); // cmd
        Menu menu = gson.fromJson(HttpCommonAction.getRequestBody(request.getReader()),Menu.class); // menu

        RestInfoService restInfoService = new RestInfoService();
        String json = gson.toJson(restInfoService.modifyRestMenu(restCommand,menu));
        restInfoService = null;

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}

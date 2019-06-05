package member.controller.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import menu.model.javabean.Rest;
import order.controller.service.OrderService;
import order.model.daoImpl.OrderDaoImpl;


@WebServlet("/test")
public class test extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setContentType("application/json;charset=UTF-8");
    Gson gson = new GsonBuilder().disableHtmlEscaping()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).serializeNulls().create();
    OrderDaoImpl orderDao = new OrderDaoImpl();
    String json = gson
        .toJson(orderDao.searchIdleOrder());
    orderDao = null;
    PrintWriter out = resp.getWriter();
    out.print(json);
    out.flush();
  //  Context ctx;
  //  try {
  //    ctx = new InitialContext();
  //    ComboPooledDataSource ds = (ComboPooledDataSource) ctx.lookup("java:comp/env/jndi/tmpSFD");
  //    Connection connection = ds.getConnection();
  //    PreparedStatement preparedStatement;
  //    ResultSet resultSet;
  //    List<Rest> restList = new ArrayList<>();
  //    String sql = "SELECT * FROM restaurant_info";
  //
  //    preparedStatement = connection.prepareStatement(sql);
  //    resultSet = preparedStatement.executeQuery();
  //    resultSet.getMetaData();
  //    while (resultSet.next()) {
  //      Rest rest = new Rest();
  //      rest.setRestID(resultSet.getLong("Rest_Id"));
  //      rest.setRestName(resultSet.getString("Rest_Name"));
  //      rest.setRestAddress(resultSet.getString("Rest_Address"));
  //      rest.setDescription(resultSet.getString("Description"));
  //      rest.setRestPhoto(resultSet.getString("Rest_Photo"));
  //      restList.add(rest);
  //    }
  //    System.out.println(restList);
  //  } catch (SQLException e) {
  //    e.printStackTrace();
  //  } catch (NamingException e) {
  //    e.printStackTrace();
  //  }
  }
}

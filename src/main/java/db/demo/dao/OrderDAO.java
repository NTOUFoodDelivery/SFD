package db.demo.dao;

import db.demo.connect.JdbcUtils;
import db.demo.javabean.User;
import order.model.javabean.Order;
//import testsfd_db.connect;
import tool.ResultSetToJson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.JsonObject;

public class OrderDAO {

    // 把訂單加入資料庫
    public static void addOrder(Order order){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JdbcUtils.getconn();

            // set order table ----- BEGIN<33333333
            String order_sql = "INSERT INTO `order`(Order_Id, Start_Time, Type_Count, Total, Order_Status) VALUES(?, ?, ?, ?, ?);";
            preparedStatement = (PreparedStatement)connection.prepareStatement(order_sql);
            preparedStatement.setInt(1,order.getOrder_Id());
            preparedStatement.setString(2,order.getStart_Time());
            preparedStatement.setInt(3,order.getType_Count());
            preparedStatement.setInt(4,order.getTotal());
            preparedStatement.setString(5,order.getOrder_Status());
            preparedStatement.executeUpdate();
            // set order table ----- END
            // set order_food table ----- BEGIN
            String order_food_sql = "INSERT INTO `order_food`(Order_SERIAL, Order_Id, Food_Id, `Count`) VALUES(?, (SELECT Order_Id FROM `order` WHERE `order`.Order_Id = ?), (SELECT Food_Id FROM meal WHERE meal.Food_Id = ?), ?);";
            preparedStatement = (PreparedStatement)connection.prepareStatement(order_food_sql);
            for(Order.MealsBean meal : order.getMeals()) {
                String str = order.getOrder_Id()+""+meal.getFood_Id();
                int orderSerial = (int)Long.parseLong(str.trim(),10);
                preparedStatement.setInt(1, orderSerial);
                preparedStatement.setInt(2, order.getOrder_Id());
                preparedStatement.setInt(3, meal.getFood_Id());
                preparedStatement.setInt(4, meal.getCount());
            }
            preparedStatement.executeUpdate();
            // set order_food table ----- END
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.close(preparedStatement,connection);
        }
    }

    // 利用 orderID 刪除 訂單
    // 需要把order_Fodd也都刪光光嗎                   @渣炸眨詐
    public static void delOrder(int orderID)
    {
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
        	connection = JdbcUtils.getconn();
        	String sql = "DELETE FROM `order` WHERE Order_Id = ?";
        	preparedStatement = connection.prepareStatement(sql);
        	preparedStatement.setInt(1, orderID);
        	preparedStatement.executeUpdate();
        }
        catch(SQLException e) 
	    { 
        	e.printStackTrace();
	    } 
	    finally 
	    { 
	    	JdbcUtils.close(preparedStatement,connection);
	    }
    }
    /*delete order_food 所屬食物項目*/
    public static void delOrder_food(int orderID)
    {
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
        	connection = JdbcUtils.getconn();
        	String sql = "DELETE FROM `order_food` WHERE Order_Id = ?";
        	preparedStatement = connection.prepareStatement(sql);
        	preparedStatement.setInt(1, orderID);
        	preparedStatement.executeUpdate();
        }
        catch(SQLException e) 
	    { 
        	e.printStackTrace();
	    } 
	    finally 
	    { 
	    	JdbcUtils.close(preparedStatement,connection);
	    }
    }
    

    // 找出 未推播 訂單
    // 暴風製造              請渣炸眨詐過目檢查OK
    public static ArrayList<Integer> searchIdelOrder()
    {
    	ArrayList<Integer> a = new ArrayList<Integer>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getconn();
            String sql = "SELECT Order_Id FROM order WHERE Order_Status = 'wait'";
            preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData();
            while(resultSet.next())
            {
                a.add(resultSet.getInt("Order_Id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.close(preparedStatement,connection);
        }
        return a;
    }

    // 利用 userID 查詢 食客 當前訂單
    // 暴風製造              請渣炸眨詐過目檢查OK
    public static JsonObject searchEaterOrder(int userID)
    {
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        JsonObject jsonString = null;
        try {
        	connection = JdbcUtils.getconn();

            String search_history_sql = "SELECT * FROM `order` WHERE ( Order_Status LIKE 'WAIT' OR Order_Status LIKE 'DEALING' ) AND Order_Id LIKE (SELECT Order_Id FROM customer_deliver_info WHERE Customer_Id = ?)";
            preparedStatement = connection.prepareStatement(search_history_sql);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData(); //取得Query資料
            jsonString = ResultSetToJson.ResultSetToJsonObject(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.close(preparedStatement,connection);
        }
    	return jsonString;
    }

  //Order_id 連結對應當前食物查詢顧客
    public static JsonObject searchCustomerNowOrder_Food(int orderID){
    	Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        JsonObject jsonString = null;
        try {
            con = JdbcUtils.getconn();

            String search_history_sql = "SELECT Food_Id, Count FROM History WHERE Order_Id = ?";
            pst = (PreparedStatement)con.prepareStatement(search_history_sql);
            pst.setInt(1, orderID);
            rs = pst.executeQuery();
            rs.getMetaData(); //取得Query資料
            jsonString = ResultSetToJson.ResultSetToJsonObject(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.close(pst,con);
        }
    	return jsonString;
    }
    
    // 利用 userID 查詢 食客 歷史訂單
    // 暴風製造              請渣炸眨詐過目檢查
    public static JsonObject searchEaterHistoryOrder(int userID)
    {
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        JsonObject jsonString = null;
        try {
        	connection = JdbcUtils.getconn();

            String search_history_sql = "SELECT History_Id, Start_Time, Total, Final_Status FROM History WHERE History_Id LIKE (SELECT Order_Id FROM customer_deliver_info WHERE Customer_Id = ?)";
            preparedStatement = connection.prepareStatement(search_history_sql);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData(); //取得Query資料
            jsonString = ResultSetToJson.ResultSetToJsonObject(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.close(preparedStatement,connection);
        }
    	return jsonString;
    }

    // 利用 userID 查詢 外送員 當前訂單
    // 暴風製造              請渣炸眨詐過目檢查
    public static JsonObject searchDeliverOrder(int userID)
    {
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        JsonObject jsonString = null;
        try {
        	connection = JdbcUtils.getconn();

            String search_history_sql = "SELECT * FROM `order` WHERE Order_Id = (SELECT Order_Id FROM customer_deliver_info WHERE Deliver_Id = ?)";
            preparedStatement = connection.prepareStatement(search_history_sql);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData(); //取得Query資料
            jsonString = ResultSetToJson.ResultSetToJsonObject(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.close(preparedStatement,connection);
        }
    	return jsonString;
    }

    // 利用 userID 查詢 外送員 歷史訂單
    public static JsonObject searchDeliverHistoryOrder(int deliverId){
    	Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        JsonObject jsonString = null;
        try {
            con = JdbcUtils.getconn();

            String search_history_sql = "SELECT History_Id, Start_Time, Total, Final_Status FROM History WHERE History_Id LIKE (SELECT Order_Id FROM customer_deliver_info WHERE Deliver_Id = ?)";
            pst = (PreparedStatement)con.prepareStatement(search_history_sql);
            pst.setInt(1, deliverId);
            rs = pst.executeQuery();
            rs.getMetaData(); //取得Query資料
            jsonString = ResultSetToJson.ResultSetToJsonObject(rs);
         /*while(rs.next()) {
				int na = rs.getInt("History_Id");

				System.out.println(na );
			}*/
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.close(pst,con);
        }
    	return jsonString;
    }
    //Order_id 連結對應食物查詢   還沒完成 !!!!!
    public static JsonObject searchDeliverHistoryOrder_Food(int orderID){
    	Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        JsonObject jsonString = null;
        try {
            con = JdbcUtils.getconn();

            String search_history_sql = "SELECT history_food.Food_Name, meal.Cost, history_food.Count, restaurant_info.Rest_Name "
            											 + "FROM meal "
            											 + "INNER JOIN history_food ON history_food.Food_Id = meal.Food_Id"
            											 + "INNER JOIN restaurant_info ON restaurant_info.Rest_Id = meal.Rest_Id "
            											 + "WHERE history_food.History_Id = ?";
            pst = (PreparedStatement)con.prepareStatement(search_history_sql);
            pst.setInt(1, orderID);
            rs = pst.executeQuery();
            rs.getMetaData(); //取得Query資料
            jsonString = ResultSetToJson.ResultSetToJsonObject(rs);
           /* while(rs.next()) {
				String na = rs.getString("Food_Name");
				String id = rs.getString("Rest_Name");
				int as = rs.getInt("Cost");
				int ie = rs.getInt("Count");
				System.out.println(na + " " + id + " " + as+ " " + ie);
			}
            */
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.close(pst,con);
        }
    	return jsonString;
    }
    
    //測試區
  /*  public static void main(String args[]) {
    	searchDeliverHistoryOrder_Food(0);
    	
	}*/
}

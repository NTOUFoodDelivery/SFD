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
    public static void delOrder(Long orderID)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JdbcUtils.getconn();
            String sql = "DELETE FROM `order` WHERE Order_Id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, orderID);
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


    // 找出 未推播 訂單 並回傳詳細資訊
    // 暴風製造              請渣炸眨詐過目檢查OK
    public static ArrayList<JsonObject> searchIdelOrder()
    {
        ArrayList<JsonObject> a = new ArrayList<JsonObject>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getconn();
            String sql = "SELECT `order`.Order_Id, `order`.Total, order_food.`Count`, `order`.Start_Time, `order`.Other, meal.Food_Name, meal.Cost, restaurant_info.Rest_Name, restaurant_info.Rest_Address, `order`.Address " + 
            		"FROM `order` " + 
            		"INNER JOIN order_food ON `order`.Order_Id = order_food.Order_Id " + 
            		"INNER JOIN meal ON order_food.Food_Id = meal.Food_Id " + 
            		"INNER JOIN restaurant_info ON restaurant_info.Rest_Id = meal.Rest_Id " + 
            		"WHERE Order_Status = 'WAIT'";
            preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData();
            while(resultSet.next())
            {
                a.add(ResultSetToJson.ResultSetToJsonObject(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.close(preparedStatement,connection);
        }
        return a;
    }
/*----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    // 利用 userID 查詢 食客 當前訂單
    // OK
    public static JsonObject searchEaterOrder(Long userID)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        JsonObject jsonString = null;
        try {
            connection = JdbcUtils.getconn();

            String sql = "SELECT `order`.Order_Id, `order`.Total, `order`.Other, order_food.`Count`, meal.Food_Name, meal.Cost, restaurant_info.Rest_Name, restaurant_info.Rest_Address, `order`.Address, member.Account, member.User_Name, member.Phone_Number, " + 
            		"FROM `order` " + 
            		"INNER JOIN order_food ON `order`.Order_Id = order_food.Order_Id " + 
            		"INNER JOIN meal ON order_food.Food_Id = meal.Food_Id " + 
            		"INNER JOIN restaurant_info ON restaurant_info.Rest_Id = meal.Rest_Id " + 
            		"INNER JOIN customer_deliver_info ON `order`.Order_Id = customer_deliver_info.Order_Id " + 
            		"INNER JOIN member ON customer_deliver_info.Deliver_Id = member.User_Id " + 
            		"WHERE customer_deliver_info.Customer_Id = ?";
            //String search_history_sql = "SELECT * FROM `order` WHERE ( Order_Status LIKE 'WAIT' OR Order_Status LIKE 'DEALING' ) AND Order_Id LIKE (SELECT Order_Id FROM customer_deliver_info WHERE Customer_Id = ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userID);
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

/*------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    // 利用 userID 查詢 食客 歷史訂單
    // 暴風製造
    public static JsonObject searchEaterHistoryOrder(Long userID)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        JsonObject jsonString = null;
        try {
            connection = JdbcUtils.getconn();
/*add history customer deliver info !!!!!!!!!!!!!!!!!!!!!!!*/
            String sql ="SELECT history.History_Id, history.Start_Time, history.Total, history.Address, history.Other, history_food.Food_Name,"
            		+ " history_food.Count,  member.Accout, member.User_Name, history_food.Rest_Name" +
            		"FROM history " +
            		"INNER JOIN history_food ON history.History_Id = history_food.History_Id " + 
            		"INNER JOIN history_customer_deliver_info ON history.History_Id = history_customer_deliver_info.Order_Id " + 
            		"INNER JOIN member ON history_customer_deliver_info.Deliver_Id = member.User_Id " + 
            		"WHERE history_customer_deliver_info.Customer_Id = ?";
            //String search_history_sql = "SELECT History_Id, Start_Time, Total, Final_Status FROM History WHERE History_Id LIKE (SELECT Order_Id FROM customer_deliver_info WHERE Customer_Id = ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userID);
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
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    // 利用 userID 查詢 外送員 當前訂單
    // OK
    public static JsonObject searchDeliverOrder(Long userID)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        JsonObject jsonString = null;
        try {
            connection = JdbcUtils.getconn();

            String sql = "SELECT `order`.Order_Id, `order`.Total, `order`.Other, order_food.`Count`, meal.Food_Name, meal.Cost, restaurant_info.Rest_Name, restaurant_info.Rest_Address, `order`.Address, member.Account, member.User_Name, member.Phone_Number, " + 
            		"FROM `order` " + 
            		"INNER JOIN order_food ON `order`.Order_Id = order_food.Order_Id " + 
            		"INNER JOIN meal ON order_food.Food_Id = meal.Food_Id " + 
            		"INNER JOIN restaurant_info ON restaurant_info.Rest_Id = meal.Rest_Id " + 
            		"INNER JOIN customer_deliver_info ON `order`.Order_Id = customer_deliver_info.Order_Id " + 
            		"INNER JOIN member ON customer_deliver_info.Customer_Id = member.User_Id " + 
            		"WHERE customer_deliver_info.Deliver_Id = ?";
            //String search_history_sql = "SELECT * FROM `order` WHERE Order_Id = (SELECT Order_Id FROM customer_deliver_info WHERE Deliver_Id = ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userID);
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
/*---------------------------------------------------------------------------------------------------------------------------------------------------------*/
    // 利用 userID 查詢 外送員 歷史訂單
    public static JsonObject searchDeliverHistoryOrder(Long deliverId){
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        JsonObject jsonString = null;
        try {
            con = JdbcUtils.getconn();

            String sql ="SELECT history.History_Id, history.Start_Time, history.Total, history.Address, history.Other, history_food.Food_Name, history_food.Count, member.Accout, member.User_Name, member.Phone_Number, history_food.Rest_Address" +
            		"FROM history " +
            		"INNER JOIN history_food ON history.History_Id = history_food.History_Id " + 
            		"INNER JOIN history_customer_deliver_info ON history.History_Id = history_customer_deliver_info.Order_Id " + 
            		"INNER JOIN member ON history_customer_deliver_info.Customer_Id = member.User_Id " + 
            		"WHERE customer_deliver_info.Deliver_Id = ?";
            //String search_history_sql = "SELECT History_Id, Start_Time, Total, Final_Status FROM History WHERE History_Id LIKE (SELECT Order_Id FROM customer_deliver_info WHERE Deliver_Id = ?)";
            pst = (PreparedStatement)con.prepareStatement(sql);
            pst.setLong(1, deliverId);
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
   
    // 更改orderStauts
    public static void modifyOrderStauts(Long orderID, String OrderStauts)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JdbcUtils.getconn();
            String sql = "UPDATE `order` SET Order_Stauts = ? WHERE Order_Id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, OrderStauts);
            preparedStatement.setLong(2, orderID);
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
    
    //測試區
//    public static void main(String args[]) {
//    	searchDeliverHistoryOrder_Food(0);
//
//	}
}

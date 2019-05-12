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

import com.google.gson.JsonObject;

public class OrderDAO {

    // 把訂單加入資料庫
    public static void addOrder(Order order){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JdbcUtils.getconn();

            // set order table ----- BEGIN<33333333
            String order_sql = "INSERT INTO order(Order_Id, Start_Time, Type_Count, Total, Order_Status) VALUES(?, ?, ?, ?, ?);";
            preparedStatement = (PreparedStatement)connection.prepareStatement(order_sql);
            preparedStatement.setInt(1,order.getOrder_Id());
            preparedStatement.setString(2,order.getStart_Time());
            preparedStatement.setInt(3,order.getType_Count());
            preparedStatement.setInt(4,order.getTotal());
            preparedStatement.setString(5,order.getOrder_Status());
            preparedStatement.executeUpdate();
            // set order table ----- END
            // set order_food table ----- BEGIN
            String order_food_sql = "INSERT INTO order(Order_SERIAL, Food_Id, Total, Count) VALUES(?, ?, ?, ?);";
            preparedStatement = (PreparedStatement)connection.prepareStatement(order_food_sql);
            for(Order.MealsBean meal : order.getMeals()) {
                preparedStatement.setInt(1, order.getOrder_Id());
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
    public static void delOrder(int orderID){}

    // 找出 未推播 訂單
    public static void searchIdelOrder(){}

    // 利用 userID 查詢 食客 當前訂單
    public static void searchEaterOrder(int userID){}

    // 利用 userID 查詢 食客 歷史訂單
    public static void searchEaterHistoryOrder(int userID){}

    // 利用 userID 查詢 外送員 當前訂單
    public static void searchDeliverOrder(int userID){
    	
    	
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
    //Order_id 連結對應食物查詢
    public static JsonObject searchDeliverHistoryOrder_Food(int orderID){
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
    
    //測試區
 /*   public static void main(String args[]) {
    	searchDeliverHistoryOrder(1);
    	
	}*/
}

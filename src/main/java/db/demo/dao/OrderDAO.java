package db.demo.dao;

import db.demo.connect.JdbcUtils;
import db.demo.javabean.User;
import order.model.javabean.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDAO {

    // 把訂單加入資料庫
    public static void addOrder(Order order){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JdbcUtils.getconn();

            // set order table ----- BEGIN
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
    public static void searchDeliverOrder(int userID){}

    // 利用 userID 查詢 外送員 歷史訂單
    public static void searchDeliverHistoryOrder(int userID){}
}

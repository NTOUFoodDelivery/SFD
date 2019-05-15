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
import java.util.List;

import com.google.gson.JsonObject;

public class OrderDAO {

    // 把訂單加入資料庫
    public static void addOrder(Order order){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JdbcUtils.getconn();

            // set order table ----- BEGIN<33333333
            String order_sql = "INSERT INTO `order`(Order_Id, Start_Time, Type_Count, Total, Order_Status, Address, Other) VALUES(?, ?, ?, ?, ?, ?, ?);";
            preparedStatement = (PreparedStatement)connection.prepareStatement(order_sql);
            preparedStatement.setLong(1,order.getOrderID());
            preparedStatement.setString(2,order.getStartTime());
            preparedStatement.setInt(3,order.getTypeCount());
            preparedStatement.setInt(4,order.getTotal());
            preparedStatement.setString(5,order.getOrderStatus());
            preparedStatement.setString(6,order.getAddress());
            preparedStatement.setString(7,order.getOther());
            preparedStatement.executeUpdate();
            // set order table ----- END
            // set OCD table ----- BEGIN
            String OC_sql = "INSERT INTO customer_deliver_info(Order_Id, Customer_Id) VALUES(?, ?);";
            preparedStatement = (PreparedStatement)connection.prepareStatement(OC_sql);
            preparedStatement.setLong(1,order.getOrderID());
            preparedStatement.setLong(2,order.getCustomerID());
            preparedStatement.executeUpdate();

            // set OCD table ----- END
            // set order_food table ----- BEGIN
            String order_food_sql = "INSERT INTO `order_food`(Order_SERIAL, Order_Id, Food_Id, `Count`) VALUES(?, (SELECT Order_Id FROM `order` WHERE `order`.Order_Id = ?), (SELECT Food_Id FROM meal WHERE meal.Food_Id = ?), ?);";
            preparedStatement = (PreparedStatement)connection.prepareStatement(order_food_sql);
            for(Order.MealsBean meal : order.getMeals()) {
                String str = order.getOrderID()+""+meal.getFoodID();
                Long orderSerial = Long.parseLong(str.trim(),10);
                preparedStatement.setLong(1, orderSerial);
                preparedStatement.setLong(2, order.getOrderID());
                preparedStatement.setInt(3, meal.getFoodID());
                preparedStatement.setInt(4, meal.getCount());
                preparedStatement.executeUpdate();
            }

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

//    //  利用orderID 回傳 該筆訂單資訊
//    public static Order getOrder(Long orderID)
//    {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        try {
//            connection = JdbcUtils.getconn();
//            String sql = "SELECT `order`.Order_Id, `order`.Total, order_food.`Count`, `order`.Start_Time, `order`.Other, meal.Food_Name, meal.Cost, restaurant_info.Rest_Name, restaurant_info.Rest_Address, `order`.Address " +
//                    "FROM `order` " +
//                    "INNER JOIN order_food ON `order`.Order_Id = order_food.Order_Id " +
//                    "INNER JOIN meal ON order_food.Food_Id = meal.Food_Id " +
//                    "INNER JOIN restaurant_info ON restaurant_info.Rest_Id = meal.Rest_Id " +
//                    "WHERE Order_Id = ?";
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setLong(1, orderID);
//            resultSet = preparedStatement.executeQuery();
//            resultSet.getMetaData();
//
//        }
//        catch(SQLException e)
//        {
//            e.printStackTrace();
//        }
//        finally
//        {
//            JdbcUtils.close(preparedStatement,connection);
//        }
//    }

    // 找出 未推播 訂單 並回傳詳細資訊
    // 暴風製造              請渣炸眨詐過目檢查OK
    public static ArrayList<JsonObject> searchIdleOrderJsonObject()
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

    // 找出 未推播 訂單 並回傳詳細資訊 Order
    // 暴風製造              請渣炸眨詐過目檢查OK
    public static ArrayList<Order> searchIdleOrder()
    {
        ArrayList<Order> orders = new ArrayList<Order>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getconn();
            String sql = "SELECT `order`.Order_Id, `order`.Total, `order`.Start_Time, `order`.Order_Status, `order`.Other,`order`.Type_Count, `order`.Address, customer_deliver_info.Customer_Id, customer_deliver_info.Deliver_Id " +
                    "FROM `order`" +
                    "INNER JOIN customer_deliver_info ON `order`.Order_Id = customer_deliver_info.Order_Id" +
                    " WHERE Order_Status = 'WAIT'";
            preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData();

            while(resultSet.next())
            {
                Order order = new Order();
                List<Order.MealsBean> meals = new ArrayList<>();
                order.setOrderStatus(resultSet.getString("Order_Status"));
                order.setValue(0);

                order.setOrderID(resultSet.getInt("Order_Id"));
                order.setTotal(resultSet.getInt("Total"));
                order.setStartTime(resultSet.getString("Start_Time"));
                order.setOther(resultSet.getString("Other"));
                order.setTypeCount(resultSet.getInt("Type_Count"));
                order.setAddress(resultSet.getString("Address"));


                order.setCustomerID(resultSet.getInt("Customer_Id"));
                order.setDeliverID(resultSet.getInt("Deliver_Id"));



                ResultSet mealResultSet = null;
                String mealSql = "SELECT order_food.`Count`, meal.Food_Name, meal.Cost, meal.Food_Id, restaurant_info.Rest_Name, restaurant_info.Rest_Address\n" +
                        "FROM `order`" +
                        "INNER JOIN order_food ON `order`.Order_Id = order_food.Order_Id  \n" +
                        "INNER JOIN meal ON order_food.Food_Id = meal.Food_Id  \n" +
                        "INNER JOIN restaurant_info ON restaurant_info.Rest_Id = meal.Rest_Id  \n" +
                        "WHERE `order`.Order_Id = ?";
                preparedStatement = (PreparedStatement)connection.prepareStatement(mealSql);
                preparedStatement.setLong(1, order.getOrderID());
                mealResultSet = preparedStatement.executeQuery();

                int count = 0;
                while(mealResultSet.next()){
                    order.setRestName(mealResultSet.getString("Rest_Name"));
                    order.setRestAddress(mealResultSet.getString("Rest_Address"));
                    Order.MealsBean meal = new Order.MealsBean();
                    meal.setFoodID(mealResultSet.getInt("Food_Id"));
                    meal.setFoodName(mealResultSet.getString("Food_Name"));
                    meal.setCount(mealResultSet.getInt("Count"));
                    meals.add(meal);
                    count++;
                    System.out.println(count);
                }

                order.setMeals(meals);
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.close(preparedStatement,connection);
        }
        return orders;
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



    /*----------------------------------------------------------------------------------------------------------------------------------------------------------*/


    // 傳出ORDER 資料到HISTORY!!!
    public static void OrdertoHistory(Long OrderID)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getconn();
//  order_food.Order_SERIAL, order_food.`Count`, meal.Food_Name, restaurant_info.Rest_Name, customer_deliver_info.Customer_Id, customer_deliver_info.Deliver_Id " + 
            String sql = "SELECT  * FROM `order` WHERE Order_Id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, OrderID);
            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData(); //取得Query資料

            String inserthistory = "INSERT INTO history(History_Id, Start_Time, Type_Count, Total, Final_Status, Address, Other) VALUES(?, ?, ?, ?, ?, ?, ?);";
            preparedStatement2 = (PreparedStatement)connection.prepareStatement(inserthistory);
            preparedStatement2.setLong(1,resultSet.getLong("Order_Id"));
            preparedStatement2.setString(2,resultSet.getString("Start_Time"));
            preparedStatement2.setInt(3,resultSet.getInt("Type_Count"));
            preparedStatement2.setInt(4,resultSet.getInt("Total"));
            preparedStatement2.setString(5,resultSet.getString("Order_Status"));
            preparedStatement2.setString(6,resultSet.getString("Address"));
            preparedStatement2.setString(7,resultSet.getString("Other"));
            preparedStatement2.executeUpdate();
            //set HOCD-history_Id
            String insertHO_CD_history_Id = "INSERT INTO history_customer_deliver_info(History_Id) VALUES(?)";
            preparedStatement2 = (PreparedStatement)connection.prepareStatement(insertHO_CD_history_Id);
            preparedStatement2.setLong(1,resultSet.getLong("Order_Id"));
            preparedStatement2.executeUpdate();

            // set OCD table ----- BEGIN
            String select_OCDsql = "SELECT  * FROM customer_deliver_info WHERE Order_Id = ?";
            preparedStatement = connection.prepareStatement(select_OCDsql);
            preparedStatement.setLong(1, OrderID);
            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData(); //取得Query資料

            String OCD_sql = "INSERT INTO history_customer_deliver_info(History_Id, Customer_Id, Deliver_Id) VALUES(?, ?, ?);";
            preparedStatement2 = (PreparedStatement)connection.prepareStatement(OCD_sql);
            preparedStatement2.setLong(1,resultSet.getLong("Order_Id"));
            preparedStatement2.setLong(2,resultSet.getLong("Customer_Id"));
            preparedStatement2.setLong(3,resultSet.getLong("Deliver_Id"));
            preparedStatement2.executeUpdate();

            // set OCD table ----- END
            // set order_food table ----- BEGIN
            String selectfoodsql = "SELECT  order_food.Order_SERIAL, order_food.Order_Id, order_food.`Count`, meal.Food_Name, restaurant_info.Rest_Name "
                    + "FROM order_food INNER JOIN meal ON order_food.Food_Id = meal.Food_Id "
                    + "INNER JOIN restaurant_info ON meal.Rest_Id =  restaurant_info.Rest_Id WHERE order_food.Order_Id = ?";
            preparedStatement = connection.prepareStatement(selectfoodsql);
            preparedStatement.setLong(1, OrderID);
            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData(); //取得Query資料

            while(resultSet.next()) {
                String history_food_sql = "INSERT INTO history_food(History_SERIAL, History_Id, Food_Name, `Count`, Rest_Name) VALUES(?, ?, ?, ?, ?);";
                preparedStatement2 = (PreparedStatement)connection.prepareStatement(history_food_sql);
                preparedStatement2.setLong(1,resultSet.getLong("order_food.Order_SERIAL"));
                preparedStatement2.setLong(2,resultSet.getLong("order_food.Order_Id"));
                preparedStatement2.setString(3,resultSet.getString("meal.Food_Name"));
                preparedStatement2.setInt(4,resultSet.getInt("order_food.`Count`"));
                preparedStatement2.setString(5,resultSet.getString("restaurant_info.Rest_Name"));
                preparedStatement2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.close(preparedStatement,connection);
        }
    }



    /*----------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //訂單建立後新增DELIVER_ID到OCD

    public static void insertDtoOCD(Order order)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JdbcUtils.getconn();
            String sql = "UPDATE customer_deliver_info SET Deliver_Id = ? WHERE Order_Id LIKE ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, order.getDeliverID());
            preparedStatement.setLong(2, order.getOrderID());
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
    /*----------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
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
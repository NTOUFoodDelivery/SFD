package db.demo.dao;

import com.google.gson.JsonObject;
import db.demo.connect.JdbcUtils;
import order.model.javabean.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    // 把訂單加入資料庫
    public static void addOrder(Order order){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JdbcUtils.getconn();

            // set order table ----- BEGIN<33333333
            String order_sql = "INSERT INTO `order`(Order_Id, Start_Time, Type_Count, Total, Order_Status, Address, Other, Casting_Prio) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
            preparedStatement = (PreparedStatement)connection.prepareStatement(order_sql);
            preparedStatement.setLong(1,order.getOrder().getOrderID());
            preparedStatement.setString(2,order.getOrder().getStartTime());
            preparedStatement.setInt(3,order.getOrder().getTypeCount());
            preparedStatement.setInt(4,order.getOrder().getTotal());
            preparedStatement.setString(5,order.getOrder().getOrderStatus());
            preparedStatement.setString(6,order.getCustomer().getAddress());
            preparedStatement.setString(7,order.getCustomer().getOther());
            preparedStatement.setInt(8,order.getOrder().getCastingPrio());
            preparedStatement.executeUpdate();
            // set order table ----- END
            // set OCD table ----- BEGIN
            String OC_sql = "INSERT INTO customer_deliver_info(Order_Id, Customer_Id) VALUES(?, ?);";
            preparedStatement = (PreparedStatement)connection.prepareStatement(OC_sql);
            preparedStatement.setLong(1,order.getOrder().getOrderID());
            preparedStatement.setLong(2,order.getCustomer().getUserID());
            preparedStatement.executeUpdate();
            // set OCD table ----- END
            // set order_food table ----- BEGIN
            String order_food_sql = "INSERT INTO `order_food`(Order_SERIAL, Order_Id, Food_Id, `Count`) VALUES(?, (SELECT Order_Id FROM `order` WHERE `order`.Order_Id = ?), (SELECT Food_Id FROM meal WHERE meal.Food_Id = ?), ?);";
            preparedStatement = (PreparedStatement)connection.prepareStatement(order_food_sql);
            for(Order.OrderBean.MealsBean meal : order.getOrder().getMeals()) {
                String str = order.getOrder().getOrderID()+""+meal.getFoodID();
                Long orderSerial = Long.parseLong(str.trim(),10);
                preparedStatement.setLong(1, orderSerial);
                preparedStatement.setLong(2, order.getOrder().getOrderID());
                preparedStatement.setLong(3, meal.getFoodID());
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
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------*/
    // 找出 未推播 訂單 並回傳詳細資訊   5/17版本更新完畢
    // 暴風製造              請渣炸眨詐過目檢查OK
   /* public static ArrayList<JsonObject> searchIdleOrderJsonObject()
    {
        ArrayList<JsonObject> a = new ArrayList<JsonObject>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getconn();
            String sql = "SELECT `order`.Order_Id, `order`.Total, order_food.`Count`, `order`.Start_Time, `order`.Other,`order`.Casting_Prio, meal.Food_Name, meal.Cost, restaurant_info.Rest_Name, restaurant_info.Rest_Address, `order`.Address " +
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
    }*/
// 以上不用

    public static ArrayList<Order> searchIdleOrder()
    {
        ArrayList<Order> orders = new ArrayList<Order>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getconn();
            String sql = "SELECT `order`.Order_Id, `order`.Total, `order`.Start_Time, `order`.Order_Status, `order`.Other,`order`.Type_Count, `order`.Address, `order`.Casting_Prio, customer_deliver_info.Customer_Id, customer_deliver_info.Deliver_Id " +
                    "FROM `order`" +
                    "INNER JOIN customer_deliver_info ON `order`.Order_Id = customer_deliver_info.Order_Id" +
                    " WHERE Order_Status = 'WAIT'";
            preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData();

            while(resultSet.next())
            {
                Order order = new Order();
                List<Order.OrderBean.MealsBean> meals = new ArrayList<>();
                order.getOrder().setOrderStatus(resultSet.getString("Order_Status"));
                order.getOrder().setCastingPrio(0);

                order.getOrder().setOrderID(resultSet.getLong("Order_Id"));
                order.getOrder().setTotal(resultSet.getInt("Total"));
                order.getOrder().setStartTime(resultSet.getString("Start_Time"));
                order.getCustomer().setOther(resultSet.getString("Other"));
                order.getOrder().setTypeCount(resultSet.getInt("Type_Count"));
                order.getCustomer().setAddress(resultSet.getString("Address"));


                order.getCustomer().setUserID(resultSet.getLong("Customer_Id"));
                order.getDeliver().setUserID(resultSet.getLong("Deliver_Id"));
                order.getOrder().setCastingPrio(resultSet.getInt("Casting_Prio"));


                ResultSet mealResultSet = null;// 為未來預做版本 現階段餐廳地址名稱仍為同一個
                String mealSql = "SELECT order_food.`Count`, meal.Food_Name, meal.Cost, meal.Food_Id, restaurant_info.Rest_Name, restaurant_info.Rest_Address\n" +
                        "FROM order_food" +
                        " INNER JOIN meal ON order_food.Food_Id = meal.Food_Id  \n" +
                        "INNER JOIN restaurant_info ON restaurant_info.Rest_Id = meal.Rest_Id  \n" +
                        "WHERE order_food.Order_Id = ?";
                preparedStatement = (PreparedStatement)connection.prepareStatement(mealSql);
                preparedStatement.setLong(1, order.getOrder().getOrderID());
                mealResultSet = preparedStatement.executeQuery();

                while(mealResultSet.next()){
                    Order.OrderBean.MealsBean meal = new Order.OrderBean.MealsBean();
                    meal.setRestName(mealResultSet.getString("Rest_Name"));
                    meal.setRestAddress(mealResultSet.getString("Rest_Address"));
                    meal.setFoodID(mealResultSet.getLong("Food_Id"));
                    meal.setFoodName(mealResultSet.getString("Food_Name"));
                    meal.setCount(mealResultSet.getInt("order_food.`Count`"));
                    meal.setCost(mealResultSet.getInt("meal.Cost"));//補涵式
                    meals.add(meal);
                }
                order.getOrder().setMeals(meals);
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
    // 利用 userID 查詢 食客 當前訂單  5/17版本更新完畢
    // OK
   /* public static JsonObject searchEaterOrder(Long userID)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        JsonObject jsonString = null;
        try {
            connection = JdbcUtils.getconn();

            String sql = "SELECT `order`.Order_Id, `order`.Total, `order`.Other, order_food.`Count`, meal.Food_Name, meal.Cost, restaurant_info.Rest_Name, restaurant_info.Rest_Address, `order`.Address, `order`.Casting_Prio, member.Account, member.User_Name, member.Phone_Number " +
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
*/
    //以上不用
    public static ArrayList<Order> searchEaterOrder(Long userID)
    {
        ArrayList<Order> orders = new ArrayList<Order>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String eaterAccount = null;
        String eaterUserName = null;
        String eaterPhoneNumber = null;
        try {
            connection = JdbcUtils.getconn();
            String selfInfoSql = "SELECT Account, User_Name, Phone_Number FROM member WHERE User_Id = ?";
            preparedStatement = (PreparedStatement)connection.prepareStatement(selfInfoSql);
            preparedStatement.setLong(1,userID);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                eaterAccount = resultSet.getString("Account");
                eaterUserName = resultSet.getString("User_Name");
                eaterPhoneNumber = resultSet.getString("Phone_Number");
            }
            String sql = "SELECT `order`.Order_Id, `order`.Total, `order`.Type_Count, `order`.Other, `order`.Address, `order`.Casting_Prio, member.User_Id, member.Account, member.User_Name, member.Phone_Number" +
                    " FROM `order`" +
                    "INNER JOIN customer_deliver_info ON `order`.Order_Id = customer_deliver_info.Order_Id" +
                    " INNER JOIN member ON customer_deliver_info.Deliver_Id = member.User_Id " +
                    " WHERE customer_deliver_info.Customer_Id = ?";
            preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
            preparedStatement.setLong(1,userID);
            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData();

            while(resultSet.next())
            {
                Order order = new Order();
                order.getCustomer().setUserName(eaterUserName);
                order.getCustomer().setAccount(eaterAccount);
                order.getCustomer().setPhoneNumber(eaterPhoneNumber);
                List<Order.OrderBean.MealsBean> meals = new ArrayList<>();
                order.getOrder().setOrderID(resultSet.getLong("Order_Id"));
                order.getCustomer().setUserID(userID);
                order.getDeliver().setUserID(resultSet.getLong("User_Id"));
                order.getOrder().setTypeCount(resultSet.getInt("Type_Count"));
                order.getOrder().setTotal(resultSet.getInt("Total"));
                order.getCustomer().setOther(resultSet.getString("Other"));
                order.getOrder().setCastingPrio(resultSet.getInt("Casting_Prio"));
                order.getDeliver().setAccount(resultSet.getString("Account"));//補涵式  **補完請拿掉註解
                order.getDeliver().setUserName(resultSet.getString("User_Name"));//補涵式
                order.getDeliver().setPhoneNumber(resultSet.getString("Phone_Number"));//補涵式

                ResultSet mealResultSet = null;// 為未來預做版本 現階段餐廳地址名稱仍為同一個
                String mealSql = "SELECT order_food.Food_Id, order_food.`Count`, meal.Food_Name, meal.Cost, restaurant_info.Rest_Name, restaurant_info.Rest_Address" +
                        " FROM order_food" +
                        " INNER JOIN meal ON order_food.Food_Id = meal.Food_Id" +
                        " INNER JOIN restaurant_info ON restaurant_info.Rest_Id = meal.Rest_Id" +
                        " INNER JOIN customer_deliver_info ON order_food.Order_Id = customer_deliver_info.Order_Id" +
                        " WHERE customer_deliver_info.Customer_Id = ?";
                preparedStatement = (PreparedStatement)connection.prepareStatement(mealSql);
                preparedStatement.setLong(1, userID);
                mealResultSet = preparedStatement.executeQuery();

                while(mealResultSet.next()){
                    Order.OrderBean.MealsBean meal = new Order.OrderBean.MealsBean();
                    meal.setRestName(mealResultSet.getString("Rest_Name"));
                    meal.setRestAddress(mealResultSet.getString("Rest_Address"));
                    meal.setFoodID(mealResultSet.getLong("Food_Id"));
                    meal.setFoodName(mealResultSet.getString("Food_Name"));
                    meal.setCount(mealResultSet.getInt("Count"));
                    meal.setCost(mealResultSet.getInt("Cost"));
                    System.out.println(meal.getCount());
                    meals.add(meal);
                }
                order.getOrder().setMeals(meals);
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.close(preparedStatement,connection);
        }
        return orders;
    }

    /*----------------------------------------------------------------------------------------------------------------------------------------------------------*/


    // 傳出ORDER 資料到HISTORY!!!
    public static void ordertoHistory(Long OrderID)
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

            String inserthistory = "INSERT INTO history(History_Id, Start_Time, Type_Count, Total, Final_Status, Address, Other,Casting_Prio) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
            preparedStatement2 = (PreparedStatement)connection.prepareStatement(inserthistory);
            preparedStatement2.setLong(1,resultSet.getLong("Order_Id"));
            preparedStatement2.setString(2,resultSet.getString("Start_Time"));
            preparedStatement2.setInt(3,resultSet.getInt("Type_Count"));
            preparedStatement2.setInt(4,resultSet.getInt("Total"));
            preparedStatement2.setString(5,resultSet.getString("Order_Status"));
            preparedStatement2.setString(6,resultSet.getString("Address"));
            preparedStatement2.setString(7,resultSet.getString("Other"));
            preparedStatement2.setInt(8,resultSet.getInt("Casting_Prio"));
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
            preparedStatement.setLong(1, order.getDeliver().getUserID());
            preparedStatement.setLong(2, order.getOrder().getOrderID());
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
//    // 利用 userID 查詢 食客 歷史訂單
//    // 暴風製造
//    public static JsonObject searchEaterHistoryOrder(Long userID)
//    {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        JsonObject jsonString = null;
//        try {
//            connection = JdbcUtils.getconn();
//            String sql ="SELECT history.History_Id, history.Start_Time, history.Total, history.Address, history.Other, history_food.Food_Name,"
//                    + " history_food.Count, member.Account, member.User_Name, history_food.Rest_Name" +
//                    " FROM history " +
//                    "INNER JOIN history_food ON history.History_Id = history_food.History_Id " +
//                    "INNER JOIN history_customer_deliver_info ON history.History_Id = history_customer_deliver_info.History_Id " +
//                    "INNER JOIN member ON history_customer_deliver_info.Deliver_Id = member.User_Id " +
//                    "WHERE history_customer_deliver_info.Customer_Id = ?";
//            //String search_history_sql = "SELECT History_Id, Start_Time, Total, Final_Status FROM History WHERE History_Id LIKE (SELECT Order_Id FROM customer_deliver_info WHERE Customer_Id = ?)";
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setLong(1, userID);
//            resultSet = preparedStatement.executeQuery();
//            resultSet.getMetaData(); //取得Query資料
//            jsonString = ResultSetToJson.ResultSetToJsonObject(resultSet);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally{
//            JdbcUtils.close(preparedStatement,connection);
//        }
//        return jsonString;
//    }

    // 利用 userID 查詢 食客 歷史訂單
    // BerSerKer製造
    public static List<Order> searchEaterHistoryOrder(Long userID)
    {
        List<Order> orderList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        JsonObject jsonString = null;
        try {
            connection = JdbcUtils.getconn();
            String sql ="SELECT history.History_Id, history.Start_Time, history.Total, history.Address, history.Other, member.Account, member.User_Name " +
                    " FROM history "+
                    " INNER JOIN history_customer_deliver_info ON history.History_Id = history_customer_deliver_info.History_Id  " +
                    "INNER JOIN member ON history_customer_deliver_info.Deliver_Id = member.User_Id " +
                    "WHERE history_customer_deliver_info.Customer_Id = ? ";
            //String search_history_sql = "SELECT History_Id, Start_Time, Total, Final_Status FROM History WHERE History_Id LIKE (SELECT Order_Id FROM customer_deliver_info WHERE Customer_Id = ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userID);
            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData(); //取得Query資料
            while(resultSet.next()) {
                Order order = new Order();
                List<Order.OrderBean.MealsBean> mealsBeanList = new ArrayList<>();
                order.getOrder().setOrderID(resultSet.getLong("History_Id"));
                order.getOrder().setStartTime(resultSet.getString("Start_Time"));
                order.getOrder().setTotal(resultSet.getInt("Total"));
                order.getCustomer().setAddress(resultSet.getString("Address"));
                order.getCustomer().setOther(resultSet.getString("Other"));
                order.getDeliver().setAccount(resultSet.getString("Account"));
                order.getDeliver().setUserName(resultSet.getString("User_Name"));

                ResultSet mealResultSet = null;
                String mealSql = "SELECT history_food.Food_Name,history_food.Count" +
                        " FROM history" +
                        " INNER JOIN history_food ON history.History_Id = history_food.History_Id" +
                        " INNER JOIN history_customer_deliver_info ON history.History_Id = history_customer_deliver_info.History_Id" +
                        " WHERE history_customer_deliver_info.Customer_Id = ?";
                preparedStatement = connection.prepareStatement(mealSql);
                preparedStatement.setLong(1, userID);
                mealResultSet = preparedStatement.executeQuery();
                while (mealResultSet.next()){
                    Order.OrderBean.MealsBean mealsBean = new Order.OrderBean.MealsBean();
                    mealsBean.setFoodName(mealResultSet.getString("Food_Name"));
                    mealsBean.setCount(mealResultSet.getInt("Count"));
                    mealsBeanList.add(mealsBean);
                }
                order.getOrder().setMeals(mealsBeanList);
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.close(preparedStatement,connection);
        }
        return orderList;
    }
    /*-----------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
//    // 利用 userID 查詢 外送員 當前訂單
//    // OK
//    public static JsonObject searchDeliverOrder(Long userID)
//    {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        JsonObject jsonString = null;
//        try {
//            connection = JdbcUtils.getconn();
//
//            String sql = "SELECT `order`.Order_Id, `order`.Total, `order`.Other, order_food.`Count`, meal.Food_Name, meal.Cost, restaurant_info.Rest_Name, restaurant_info.Rest_Address, `order`.Address, member.Account, member.User_Name, member.Phone_Number " +
//                    "FROM `order` " +
//                    "INNER JOIN order_food ON `order`.Order_Id = order_food.Order_Id " +
//                    "INNER JOIN meal ON order_food.Food_Id = meal.Food_Id " +
//                    "INNER JOIN restaurant_info ON restaurant_info.Rest_Id = meal.Rest_Id " +
//                    "INNER JOIN customer_deliver_info ON `order`.Order_Id = customer_deliver_info.Order_Id " +
//                    "INNER JOIN member ON customer_deliver_info.Customer_Id = member.User_Id " +
//                    "WHERE customer_deliver_info.Deliver_Id = ?";
//            //String search_history_sql = "SELECT * FROM `order` WHERE Order_Id = (SELECT Order_Id FROM customer_deliver_info WHERE Deliver_Id = ?)";
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setLong(1, userID);
//            resultSet = preparedStatement.executeQuery();
//            resultSet.getMetaData(); //取得Query資料
//            jsonString = ResultSetToJson.ResultSetToJsonObject(resultSet);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally{
//            JdbcUtils.close(preparedStatement,connection);
//        }
//        return jsonString;
//    }

    // 利用 userID 查詢 外送員 當前訂單
    // BerSerKer製造
    public static List searchDeliverOrder(Long userID)
    {
        List<Order> orderList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        JsonObject jsonString = null;
        try {
            connection = JdbcUtils.getconn();

            String sql = "SELECT `order`.Order_Id, `order`.Total, `order`.Other, `order`.Address, member.Account, member.User_Name, member.Phone_Number " +
                    " FROM `order`" +
                    " INNER JOIN customer_deliver_info ON `order`.Order_Id = customer_deliver_info.Order_Id " +
                    " INNER JOIN member ON customer_deliver_info.Customer_Id = member.User_Id" +
                    " WHERE customer_deliver_info.Deliver_Id = ?";
            //String search_history_sql = "SELECT * FROM `order` WHERE Order_Id = (SELECT Order_Id FROM customer_deliver_info WHERE Deliver_Id = ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userID);
            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData(); //取得Query資料
            while(resultSet.next()) {
                Order order = new Order();
                List<Order.OrderBean.MealsBean> mealsBeanList = new ArrayList<>();
                order.getOrder().setOrderID(resultSet.getLong("Order_Id"));
                order.getOrder().setTotal(resultSet.getInt("Total"));
                order.getCustomer().setAddress(resultSet.getString("Address"));
                order.getCustomer().setOther(resultSet.getString("Other"));
                order.getDeliver().setAccount(resultSet.getString("Account"));
                order.getDeliver().setUserName(resultSet.getString("User_Name"));
                order.getDeliver().setPhoneNumber(resultSet.getString("Phone_Number"));

                ResultSet mealResultSet = null;
                String mealSql = "SELECT  order_food.`Count`, meal.Food_Name, meal.Cost " +
                        "FROM `order`" +
                        "INNER JOIN order_food ON `order`.Order_Id = order_food.Order_Id " +
                        " INNER JOIN meal ON order_food.Food_Id = meal.Food_Id " +
                        "INNER JOIN restaurant_info ON restaurant_info.Rest_Id = meal.Rest_Id " +
                        " INNER JOIN customer_deliver_info ON `order`.Order_Id = customer_deliver_info.Order_Id " +
                        " WHERE customer_deliver_info.Deliver_Id = ?";
                preparedStatement = connection.prepareStatement(mealSql);
                preparedStatement.setLong(1, userID);
                mealResultSet = preparedStatement.executeQuery();
                while (mealResultSet.next()){
                    Order.OrderBean.MealsBean mealsBean = new Order.OrderBean.MealsBean();
                    mealsBean.setFoodName(mealResultSet.getString("Food_Name"));
                    mealsBean.setCount(mealResultSet.getInt("Count"));
                    mealsBean.setCost(mealResultSet.getInt("Cost"));
                    mealsBeanList.add(mealsBean);
                }
                order.getOrder().setMeals(mealsBeanList);
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.close(preparedStatement,connection);
        }
        return orderList;
    }
    /*---------------------------------------------------------------------------------------------------------------------------------------------------------*/
//    // 利用 userID 查詢 外送員 歷史訂單
//    public static JsonObject searchDeliverHistoryOrder(Long deliverId){
//        Connection con = null;
//        PreparedStatement pst = null;
//        ResultSet rs = null;
//        JsonObject jsonString = null;
//        try {
//            con = JdbcUtils.getconn();
//
//            String sql ="SELECT history.History_Id, history.Start_Time, history.Total, history.Address, history.Other, history_food.Food_Name, history_food.Count, member.Accout, member.User_Name, member.Phone_Number, history_food.Rest_Address" +
//                    "FROM history " +
//                    "INNER JOIN history_food ON history.History_Id = history_food.History_Id " +
//                    "INNER JOIN history_customer_deliver_info ON history.History_Id = history_customer_deliver_info.Order_Id " +
//                    "INNER JOIN member ON history_customer_deliver_info.Customer_Id = member.User_Id " +
//                    "WHERE customer_deliver_info.Deliver_Id = ?";
//            //String search_history_sql = "SELECT History_Id, Start_Time, Total, Final_Status FROM History WHERE History_Id LIKE (SELECT Order_Id FROM customer_deliver_info WHERE Deliver_Id = ?)";
//            pst = (PreparedStatement)con.prepareStatement(sql);
//            pst.setLong(1, deliverId);
//            rs = pst.executeQuery();
//            rs.getMetaData(); //取得Query資料
//            jsonString = ResultSetToJson.ResultSetToJsonObject(rs);
//         /*while(rs.next()) {
//				int na = rs.getInt("History_Id");
//
//				System.out.println(na );
//			}*/
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally{
//            JdbcUtils.close(pst,con);
//        }
//        return jsonString;
//    }

    // 利用 userID 查詢 外送員 歷史訂單
    // BerSerKer 製造
    public static List searchDeliverHistoryOrder(Long deliverId){
        List<Order> orderList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        JsonObject jsonString = null;
        try {
            connection = JdbcUtils.getconn();

            String sql ="SELECT history.History_Id, history.Start_Time, history.Total, history.Address, history.Other,  member.Account, member.User_Name, member.Phone_Number " +
                    "FROM history" +
                    " INNER JOIN history_customer_deliver_info ON history.History_Id = history_customer_deliver_info.History_Id " +
                    " INNER JOIN member ON history_customer_deliver_info.Customer_Id = member.User_Id " +
                    " WHERE history_customer_deliver_info.Deliver_Id = ?";
            //String search_history_sql = "SELECT History_Id, Start_Time, Total, Final_Status FROM History WHERE History_Id LIKE (SELECT Order_Id FROM customer_deliver_info WHERE Deliver_Id = ?)";
            preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
            preparedStatement.setLong(1, deliverId);
            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData(); //取得Query資料
            while(resultSet.next()) {
                Order order = new Order();
                List<Order.OrderBean.MealsBean> mealsBeanList = new ArrayList<>();
                order.getOrder().setOrderID(resultSet.getLong("History_Id"));
                order.getOrder().setStartTime(resultSet.getString("Start_Time"));
                order.getOrder().setTotal(resultSet.getInt("Total"));
                order.getCustomer().setAddress(resultSet.getString("Address"));
                order.getCustomer().setOther(resultSet.getString("Other"));
                order.getDeliver().setAccount(resultSet.getString("Account"));
                order.getDeliver().setUserName(resultSet.getString("User_Name"));
                order.getDeliver().setPhoneNumber(resultSet.getString("Phone_Number"));

                ResultSet mealResultSet = null;
                String mealSql = "SELECT history_food.Food_Name, history_food.Count, history_food.Rest_Name " +
                        "  FROM history " +
                        " INNER JOIN history_food ON history.History_Id = history_food.History_Id " +
                        " INNER JOIN history_customer_deliver_info ON history.History_Id = history_customer_deliver_info.History_Id " +
                        " WHERE history_customer_deliver_info.Deliver_Id = ?";
                preparedStatement = connection.prepareStatement(mealSql);
                preparedStatement.setLong(1, deliverId);
                mealResultSet = preparedStatement.executeQuery();
                while (mealResultSet.next()){
                    Order.OrderBean.MealsBean mealsBean = new Order.OrderBean.MealsBean();
                    mealsBean.setFoodName(mealResultSet.getString("Food_Name"));
                    mealsBean.setCount(mealResultSet.getInt("Count"));
                    mealsBean.setRestName(mealResultSet.getString("Rest_Name"));
                    mealsBeanList.add(mealsBean);
                }
                order.getOrder().setMeals(mealsBeanList);
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.close(preparedStatement,connection);
        }
        return orderList;
    }
    /*----------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    //  更改orderStatus
    public static void modifyOrderStatus(Long orderID, String OrderStatus)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JdbcUtils.getconn();
            String sql = "UPDATE `order` SET Order_Status = ? WHERE Order_Id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, OrderStatus);
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
    
    //拿到orderStatus
    public static String getOrderStatus(Long orderID)
    {
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String status = null;
        try {
            connection = JdbcUtils.getconn();
            String sql = "SELECT Order_Status FROM `order` WHERE Order_Id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,orderID);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
            	status = resultSet.getString("Order_Status");
                //System.out.println(identity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.close(preparedStatement,connection);
        }
        return status;
    }

    // 更改 訂單權重
    public static void modifyOrderCastingPrio(Long orderID,int castingPrio)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JdbcUtils.getconn();
            String sql = "UPDATE `order` SET Casting_Prio = ? WHERE Order_Id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, orderID);
            preparedStatement.setInt(2, castingPrio);
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

    // 拿到 訂單權重
    public static int getOrderCastingPrio(Long orderID)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int castingPrio = 0;
        try {
            connection = JdbcUtils.getconn();
            String sql = "SELECT Casting_Prio FROM `order` WHERE Order_Id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, orderID);
            resultSet = preparedStatement.executeQuery();
            castingPrio = resultSet.getInt("Casting_Prio");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JdbcUtils.close(preparedStatement,connection);
        }
        return castingPrio;
    }

}
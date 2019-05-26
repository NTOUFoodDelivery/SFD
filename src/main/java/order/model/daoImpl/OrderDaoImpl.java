package order.model.daoImpl;

import db.demo.connect.JdbcUtils;
import order.model.dao.OrderDao;
import order.model.javabean.Order;
import util.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public boolean addOrder(Order order) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        boolean success = false;
        try {
            // set order table ----- BEGIN
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
                success = true;
            }
            // set order_food table ----- END
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            C3P0Util.close(connection);
        }
        return success;
    }

    @Override
    public boolean delOrder(Long orderID) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        boolean success = false;
        try {
            connection = JdbcUtils.getconn();
            String sql = "DELETE FROM `order` WHERE Order_Id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, orderID);
            preparedStatement.executeUpdate();
            success = true;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            C3P0Util.close(connection);
        }
        return success;
    }

    @Override
    public ArrayList<Order> searchIdleOrder() {
        ArrayList<Order> orders = new ArrayList<Order>();
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
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
                order.setCustomer(new Order.CustomerBean());
                order.setDeliver(new Order.DeliverBean());
                order.setOrder(new Order.OrderBean());

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
                    meal.setCount(mealResultSet.getInt("Count"));
                    meal.setCost(mealResultSet.getInt("meal.Cost"));//補涵式
                    meals.add(meal);
                }
                order.getOrder().setMeals(meals);
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            C3P0Util.close(connection);
        }
        return orders;
    }

    @Override
    public ArrayList<Order> searchEaterOrder(Long userID) {
        ArrayList<Order> orders = new ArrayList<Order>();
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        String eaterAccount = null;
        String eaterUserName = null;
        String eaterPhoneNumber = null;
        try {
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
                order.setCustomer(new Order.CustomerBean());
                order.setDeliver(new Order.DeliverBean());
                order.setOrder(new Order.OrderBean());

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
                        " WHERE customer_deliver_info.Order_Id = ?";
                preparedStatement = (PreparedStatement)connection.prepareStatement(mealSql);
                preparedStatement.setLong(1, order.getOrder().getOrderID());
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
            C3P0Util.close(connection);
        }
        return orders;
    }

    @Override
    public boolean ordertoHistory(Long orderID) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        PreparedStatement preparedStatement2;
        ResultSet resultSet;
        boolean success = false;
        try {
//  order_food.Order_SERIAL, order_food.`Count`, meal.Food_Name, restaurant_info.Rest_Name, customer_deliver_info.Customer_Id, customer_deliver_info.Deliver_Id " +
            String sql = "SELECT  * FROM `order` WHERE Order_Id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, orderID);
            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData(); //取得Query資料

            if (resultSet.next()) {
                System.out.println("sdasd");
                String startTime = resultSet.getString("Start_Time");
                int typeCount = resultSet.getInt("Type_Count");
                int total = resultSet.getInt("Total");
                String orderStatus = resultSet.getString("Order_Status");
                String address = resultSet.getString("Address");
                String other = resultSet.getString("Other");
                int castingPrio = resultSet.getInt("Casting_Prio");

                String inserthistory = "INSERT INTO history(History_Id, Start_Time, Type_Count, Total, Final_Status, Address, Other,Casting_Prio) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
                preparedStatement2 = (PreparedStatement) connection.prepareStatement(inserthistory);
                preparedStatement2.setLong(1, orderID);
                preparedStatement2.setString(2, startTime);
                preparedStatement2.setInt(3, typeCount);
                preparedStatement2.setInt(4, total);
                preparedStatement2.setString(5, orderStatus);
                preparedStatement2.setString(6, address);
                preparedStatement2.setString(7, other);
                preparedStatement2.setInt(8, castingPrio);
                preparedStatement2.executeUpdate();
                //set HOCD-history_Id
                String insertHO_CD_history_Id = "INSERT INTO history_customer_deliver_info(History_Id) VALUES(?)";
                preparedStatement2 = (PreparedStatement) connection.prepareStatement(insertHO_CD_history_Id);
                preparedStatement2.setLong(1, orderID);
                preparedStatement2.executeUpdate();

                // set OCD table ----- BEGIN
                String select_OCDsql = "SELECT  * FROM customer_deliver_info WHERE Order_Id = ?";
                preparedStatement = connection.prepareStatement(select_OCDsql);
                preparedStatement.setLong(1, orderID);
                resultSet = preparedStatement.executeQuery();
                resultSet.getMetaData(); //取得Query資料

                if(resultSet.next()) {
                    Long orderId = resultSet.getLong("Order_Id");
                    Long customerId = resultSet.getLong("Customer_Id");
                    Long deliverId = resultSet.getLong("Deliver_Id");

                    String OCD_sql = "INSERT INTO history_customer_deliver_info(History_Id, Customer_Id, Deliver_Id) VALUES(?, ?, ?);";
                    preparedStatement2 = (PreparedStatement) connection.prepareStatement(OCD_sql);
                    preparedStatement2.setLong(1, orderId);
                    preparedStatement2.setLong(2, customerId);
                    preparedStatement2.setLong(3, deliverId);
                    preparedStatement2.executeUpdate();

                    // set OCD table ----- END
                    // set order_food table ----- BEGIN
                    String selectfoodsql = "SELECT  order_food.Order_SERIAL, order_food.Order_Id, order_food.`Count`, meal.Food_Name, restaurant_info.Rest_Name "
                            + "FROM order_food INNER JOIN meal ON order_food.Food_Id = meal.Food_Id "
                            + "INNER JOIN restaurant_info ON meal.Rest_Id =  restaurant_info.Rest_Id WHERE order_food.Order_Id = ?";
                    preparedStatement = connection.prepareStatement(selectfoodsql);
                    preparedStatement.setLong(1, orderID);
                    resultSet = preparedStatement.executeQuery();
                    resultSet.getMetaData(); //取得Query資料

                    while (resultSet.next()) {
                        String history_food_sql = "INSERT INTO history_food(History_SERIAL, History_Id, Food_Name, `Count`, Rest_Name) VALUES(?, ?, ?, ?, ?);";
                        preparedStatement2 = (PreparedStatement) connection.prepareStatement(history_food_sql);
                        preparedStatement2.setLong(1, resultSet.getLong("Order_SERIAL"));
                        preparedStatement2.setLong(2, resultSet.getLong("Order_Id"));
                        preparedStatement2.setString(3, resultSet.getString("Food_Name"));
                        preparedStatement2.setInt(4, resultSet.getInt("Count"));
                        preparedStatement2.setString(5, resultSet.getString("Rest_Name"));
                        preparedStatement2.executeUpdate();
                    }
                }
            }
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            C3P0Util.close(connection);
        }
        return success;
    }

    @Override
    public boolean insertDtoOCD(Order order) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement = null;
        boolean success = false;
        try {
            String sql = "UPDATE customer_deliver_info SET Deliver_Id = ? WHERE Order_Id LIKE ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, order.getDeliver().getUserID());
            preparedStatement.setLong(2, order.getOrder().getOrderID());
            preparedStatement.executeUpdate();
            success = true;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JdbcUtils.close(preparedStatement,connection);
        }
        return success;
    }

    @Override
    public List<Order> searchEaterHistoryOrder(Long userID) {
        return null;
    }

    @Override
    public List searchDeliverOrder(Long userID) {
        return null;
    }

    @Override
    public List searchDeliverHistoryOrder(Long deliverId) {
        return null;
    }

    @Override
    public boolean modifyOrderStatus(Long orderID, String OrderStatus) {
        return false;
    }

    @Override
    public String getOrderStatus(Long orderID) {
        return null;
    }

    @Override
    public boolean modifyOrderCastingPrio(Long orderID, int castingPrio) {
        return false;
    }

    @Override
    public int getOrderCastingPrio(Long orderID) {
        return 0;
    }
}
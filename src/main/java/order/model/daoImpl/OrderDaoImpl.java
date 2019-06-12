package order.model.daoImpl;

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
             //set order table ----- BEGIN
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
            String OC_sql = "INSERT INTO customer_deliver_info(Order_Id, Customer_Id, Deliver_Id) VALUES(?, ?, ?);";
            preparedStatement = (PreparedStatement)connection.prepareStatement(OC_sql);
            preparedStatement.setLong(1,order.getOrder().getOrderID());
            preparedStatement.setLong(2,order.getCustomer().getUserID());
            preparedStatement.setLong(3, 0);
            preparedStatement.executeUpdate();
            // set OCD table ----- END
            // set order_food table ----- BEGIN
            String order_food_sql = "INSERT INTO `order_food`(Order_SERIAL, Order_Id, Food_Id, `Count`) VALUES(?, ?,  ?, ?);";
            preparedStatement = (PreparedStatement)connection.prepareStatement(order_food_sql);  //
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
            //set order_food table ----- END
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            C3P0Util.close(connection);
            return success;
        }
    }

    @Override
    public boolean delOrder(Long orderID) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        boolean success = false;
        try {
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
            return success;
        }
    }

    @Override
    public ArrayList<Order> searchIdleOrder() {
        ArrayList<Order> orders = new ArrayList<Order>();
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            String sql = "SELECT `order`.Order_Id, `order`.Total, `order`.Start_Time, `order`.Order_Status, `order`.Other,`order`.Type_Count, `order`.Address, `order`.Casting_Prio, customer_deliver_info.Customer_Id,member.User_Name,member.Account,member.Phone_Number\n" +
                    "FROM `order`\n" +
                    "INNER JOIN customer_deliver_info ON `order`.Order_Id = customer_deliver_info.Order_Id\n" +
                    "INNER JOIN member ON  `member`.User_Id = customer_deliver_info.Customer_Id\n" +
                    "WHERE Order_Status = 'WAIT'";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData();

            while(resultSet.next())
            {
                Order order = new Order();
                Order.CustomerBean customerBean = new Order.CustomerBean();
                customerBean.setUserID(resultSet.getLong("Customer_Id"));
                customerBean.setUserName(resultSet.getString("User_Name"));
                customerBean.setAddress(resultSet.getString("Address"));
                customerBean.setOther(resultSet.getString("Other"));
                customerBean.setPhoneNumber(resultSet.getString("Phone_Number"));
                customerBean.setAccount(resultSet.getString("Account"));

                Order.OrderBean orderBean = new Order.OrderBean();
                orderBean.setOrderID(resultSet.getLong("Order_Id"));
                orderBean.setTotal(resultSet.getInt("Total"));
                orderBean.setTypeCount(resultSet.getInt("Type_Count"));
                orderBean.setStartTime(resultSet.getString("Start_Time"));
                orderBean.setOrderStatus(resultSet.getString("Order_Status"));
                orderBean.setCastingPrio(resultSet.getInt("Casting_Prio"));

                List<Order.OrderBean.MealsBean> mealsBeanXList = new ArrayList<>();

                ResultSet mealResultSet;
                String mealSql = "SELECT order_food.`Count`, meal.Food_Name, meal.Cost, meal.Food_Id, restaurant_info.Rest_Name, restaurant_info.Rest_Address\n" +
                        "FROM order_food" +
                        " INNER JOIN meal ON order_food.Food_Id = meal.Food_Id  \n" +
                        "INNER JOIN restaurant_info ON restaurant_info.Rest_Id = meal.Rest_Id  \n" +
                        "WHERE order_food.Order_Id = ?";
                preparedStatement = connection.prepareStatement(mealSql);
                preparedStatement.setLong(1, orderBean.getOrderID());
                mealResultSet = preparedStatement.executeQuery();

                /*mealsBeanX.setRestName(mealResultSet.getString("Rest_Name"));
                mealsBeanX.setRestAddress(mealResultSet.getString("Rest_Address"));*/
                while(mealResultSet.next()){
                    Order.OrderBean.MealsBean mealsBean = new Order.OrderBean.MealsBean();

                    mealsBean.setRestName(mealResultSet.getString("Rest_Name"));
                    mealsBean.setRestAddress(mealResultSet.getString("Rest_Address"));
                    mealsBean.setFoodID(mealResultSet.getLong("Food_Id"));
                    mealsBean.setFoodName(mealResultSet.getString("Food_Name"));
                    mealsBean.setCount(mealResultSet.getInt("Count"));
                    mealsBean.setCost(mealResultSet.getInt("Cost"));//補涵式
                    mealsBeanXList.add(mealsBean);
                }

                orderBean.setMeals(mealsBeanXList);
                order.setCustomer(customerBean);
                order.setOrder(orderBean);
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            C3P0Util.close(connection);
            return orders;
        }
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
            return orders;
        }
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
            return success;
        }
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
            C3P0Util.close(connection);
            return success;
        }
    }

    @Override
    public List<Order> searchEaterHistoryOrder(Long userID) {
        List<Order> orderList = new ArrayList<>();
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
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
                order.setCustomer(new Order.CustomerBean());
                order.setDeliver(new Order.DeliverBean());
                order.setOrder(new Order.OrderBean());

                List<Order.OrderBean.MealsBean> mealsBeanList = new ArrayList<>();
                order.getOrder().setOrderID(resultSet.getLong("History_Id"));
                order.getOrder().setStartTime(resultSet.getString("Start_Time"));
                order.getOrder().setTotal(resultSet.getInt("Total"));
                order.getCustomer().setAddress(resultSet.getString("Address"));
                order.getCustomer().setOther(resultSet.getString("Other"));
                order.getDeliver().setAccount(resultSet.getString("Account"));
                order.getDeliver().setUserName(resultSet.getString("User_Name"));

                ResultSet mealResultSet;
                String mealSql = "SELECT history_food.Food_Name,history_food.Count, history_food.Rest_Name" + //加了NAME
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
           		   mealsBean.setRestName(mealResultSet.getString("Rest_Name"));
                    mealsBeanList.add(mealsBean);
                }
                order.getOrder().setMeals(mealsBeanList);
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            C3P0Util.close(connection);
            return orderList;
        }
    }

    @Override
    public List searchDeliverOrder(Long userID) {
        List<Order> orderList = new ArrayList<>();
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
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
                order.setCustomer(new Order.CustomerBean());
                order.setDeliver(new Order.DeliverBean());
                order.setOrder(new Order.OrderBean());

                List<Order.OrderBean.MealsBean> mealsBeanList = new ArrayList<>();
                order.getOrder().setOrderID(resultSet.getLong("Order_Id"));
                order.getOrder().setTotal(resultSet.getInt("Total"));
                order.getCustomer().setAddress(resultSet.getString("Address"));
                order.getCustomer().setOther(resultSet.getString("Other"));
                order.getDeliver().setAccount(resultSet.getString("Account"));
                order.getDeliver().setUserName(resultSet.getString("User_Name"));
                order.getDeliver().setPhoneNumber(resultSet.getString("Phone_Number"));

                ResultSet mealResultSet = null;
                String mealSql = "SELECT  order_food.`Count`, meal.Food_Name, meal.Cost, restaurant_info.Rest_Name, restaurant_info.Rest_Address" +
                        " FROM order_food" +
                        " INNER JOIN meal ON order_food.Food_Id = meal.Food_Id " +
                        "INNER JOIN restaurant_info ON restaurant_info.Rest_Id = meal.Rest_Id " +
                        " INNER JOIN customer_deliver_info ON order_food.Order_Id = customer_deliver_info.Order_Id " +
                        " WHERE customer_deliver_info.Order_Id = ?";
                preparedStatement = connection.prepareStatement(mealSql);
                preparedStatement.setLong(1, order.getOrder().getOrderID());
                mealResultSet = preparedStatement.executeQuery();
                while (mealResultSet.next()){	
                    Order.OrderBean.MealsBean mealsBean = new Order.OrderBean.MealsBean();
                    mealsBean.setFoodName(mealResultSet.getString("Food_Name"));
                    mealsBean.setCount(mealResultSet.getInt("Count"));
                    mealsBean.setCost(mealResultSet.getInt("Cost"));
					mealsBean.setFoodName(mealResultSet.getString("Rest_Name"));            		
					mealsBean.setRestAddress(mealResultSet.getString("Rest_Address"));
                    mealsBeanList.add(mealsBean);
                }
                order.getOrder().setMeals(mealsBeanList);
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            C3P0Util.close(connection);
            return orderList;
        }
    }

    @Override
    public List searchDeliverHistoryOrder(Long deliverId) {
        List<Order> orderList = new ArrayList<>();
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
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
                order.setCustomer(new Order.CustomerBean());
                order.setDeliver(new Order.DeliverBean());
                order.setOrder(new Order.OrderBean());

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
            C3P0Util.close(connection);
            return orderList;
        }
    }

    @Override
    public boolean modifyOrderStatus(Long orderID, String OrderStatus) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        boolean success = false;
        try {
            String sql = "UPDATE `order` SET Order_Status = ? WHERE Order_Id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, OrderStatus);
            preparedStatement.setLong(2, orderID);
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
            return success;
        }
    }

    @Override
    public String getOrderStatus(Long orderID) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String status = null;
        try {
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
            C3P0Util.close(connection);
            return status;
        }
    }

    @Override
    public boolean modifyOrderCastingPrio(Long orderID, int castingPrio) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        boolean success = false;
        try {
            String sql = "UPDATE `order` SET Casting_Prio = ? WHERE Order_Id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, orderID);
            preparedStatement.setInt(2, castingPrio);
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
            return success;
        }
    }

    @Override
    public int getOrderCastingPrio(Long orderID) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        int castingPrio = 0;
        try {
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
            C3P0Util.close(connection);
            return castingPrio;
        }
    }

public ArrayList<Order> searchallOrder() {
    ArrayList<Order> orders = new ArrayList<Order>();
    Connection connection = C3P0Util.getConnection();
    PreparedStatement preparedStatement, pst;
    ResultSet resultSet, rs;
    try {
        String sql = "SELECT `order`.Order_Id, `order`.Total, `order`.Start_Time, `order`.Order_Status, `order`.Other,`order`.Type_Count, `order`.Address, `order`.Casting_Prio, customer_deliver_info.Customer_Id,customer_deliver_info.Deliver_Id, member.User_Name,member.Account,member.Phone_Number\n" +
                " FROM `order`\n" +
                " INNER JOIN customer_deliver_info ON `order`.Order_Id = customer_deliver_info.Order_Id\n" +
                " INNER JOIN member ON  `member`.User_Id = customer_deliver_info.Customer_Id\n" ;
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        resultSet.getMetaData();
        
        String sql_del = "SELECT customer_deliver_info.Deliver_Id, member.User_Name,member.Account,member.Phone_Number"
        		+ " FROM customer_deliver_info" 
                +" INNER JOIN member ON  `member`.User_Id = customer_deliver_info.Deliver_Id" ;
    	pst = connection.prepareStatement(sql);
        rs = pst.executeQuery();
        rs.getMetaData();
        
        

        while(resultSet.next() && rs.next())
        {
            Order order = new Order();
            Order.CustomerBean customerBean = new Order.CustomerBean();
            Order.DeliverBean deliverBean = new Order.DeliverBean();
            
            customerBean.setUserID(resultSet.getLong("Customer_Id"));       
            customerBean.setUserName(resultSet.getString("User_Name"));
            customerBean.setAddress(resultSet.getString("Address"));
            customerBean.setOther(resultSet.getString("Other"));
            customerBean.setPhoneNumber(resultSet.getString("Phone_Number"));
            customerBean.setAccount(resultSet.getString("Account"));

            Order.OrderBean orderBean = new Order.OrderBean();
            orderBean.setOrderID(resultSet.getLong("Order_Id"));
            orderBean.setTotal(resultSet.getInt("Total"));
            orderBean.setTypeCount(resultSet.getInt("Type_Count"));
            orderBean.setStartTime(resultSet.getString("Start_Time"));
            orderBean.setOrderStatus(resultSet.getString("Order_Status"));
            orderBean.setCastingPrio(resultSet.getInt("Casting_Prio"));

            if(resultSet.getLong("Deliver_Id") != 0 ) {
            	deliverBean.setUserName(rs.getString("User_Name"));
            	deliverBean.setAccount(rs.getString("Account"));
            	deliverBean.setPhoneNumber(rs.getString("Phone_Number"));
            }
            List<Order.OrderBean.MealsBean> mealsBeanXList = new ArrayList<>();

            ResultSet mealResultSet;
            String mealSql = "SELECT order_food.`Count`, meal.Food_Name, meal.Cost, meal.Food_Id, restaurant_info.Rest_Name, restaurant_info.Rest_Address\n" +
                    " FROM order_food" +
                    " INNER JOIN meal ON order_food.Food_Id = meal.Food_Id  \n" +
                    "INNER JOIN restaurant_info ON restaurant_info.Rest_Id = meal.Rest_Id  \n" +
                    "WHERE order_food.Order_Id = ?";
            preparedStatement = connection.prepareStatement(mealSql);
            preparedStatement.setLong(1, orderBean.getOrderID());
            mealResultSet = preparedStatement.executeQuery();

            /*mealsBeanX.setRestName(mealResultSet.getString("Rest_Name"));
            mealsBeanX.setRestAddress(mealResultSet.getString("Rest_Address"));*/
            while(mealResultSet.next()){
                Order.OrderBean.MealsBean mealsBean = new Order.OrderBean.MealsBean();

                mealsBean.setRestName(mealResultSet.getString("Rest_Name"));
                mealsBean.setRestAddress(mealResultSet.getString("Rest_Address"));
                mealsBean.setFoodID(mealResultSet.getLong("Food_Id"));
                mealsBean.setFoodName(mealResultSet.getString("Food_Name"));
                mealsBean.setCount(mealResultSet.getInt("Count"));
                mealsBean.setCost(mealResultSet.getInt("Cost"));//補涵式
                mealsBeanXList.add(mealsBean);
            }

            orderBean.setMeals(mealsBeanXList);
            order.setCustomer(customerBean);
            order.setOrder(orderBean);
            order.setDeliver(deliverBean);
            orders.add(order);
        }
        } catch (SQLException e) {
        	e.printStackTrace();
        }finally{
        	C3P0Util.close(connection);
        	return orders;
        }
	}

@Override
public ArrayList<Order> searchallHistoryOrder() {
    ArrayList<Order> orders = new ArrayList<Order>();
    Connection connection = C3P0Util.getConnection();
    PreparedStatement preparedStatement, pst;
    ResultSet resultSet, rs;
    try {
        String sql = "SELECT history.History_Id, history.Start_Time, history.Type_Count, history.Total, history.Final_Status, history.Address, history.Other,history_customer_deliver_info.Customer_Id, member.User_Name,member.Account,member.Phone_Number"
        		+ " FROM history"
        		+" INNER JOIN history_customer_deliver_info ON history.History_Id = history_customer_deliver_info.History_Id\n" 
                +" INNER JOIN member ON  `member`.User_Id = history_customer_deliver_info.Customer_Id" ;
        String sql_del = "SELECT history_customer_deliver_info.Deliver_Id, member.User_Name,member.Account,member.Phone_Number"
        		+ " FROM history_customer_deliver_info" 
                +" INNER JOIN member ON  `member`.User_Id = history_customer_deliver_info.Deliver_Id" ;
        preparedStatement = connection.prepareStatement(sql);
        pst = connection.prepareStatement(sql_del);
        resultSet = preparedStatement.executeQuery();
        rs = pst.executeQuery();
        resultSet.getMetaData();
        rs.getMetaData();

        while(resultSet.next() && rs.next())
        {
            Order order = new Order();
            Order.CustomerBean customerBean = new Order.CustomerBean();
            Order.DeliverBean deliverBean = new Order.DeliverBean();
            customerBean.setUserID(resultSet.getLong("Customer_Id"));       
            customerBean.setUserName(resultSet.getString("User_Name"));
            customerBean.setAddress(resultSet.getString("Address"));
            customerBean.setOther(resultSet.getString("Other"));
            customerBean.setPhoneNumber(resultSet.getString("Phone_Number"));
            customerBean.setAccount(resultSet.getString("Account"));

            deliverBean.setUserID(rs.getLong("Deliver_Id"));       
            deliverBean.setUserName(rs.getString("User_Name"));
            deliverBean.setPhoneNumber(rs.getString("Phone_Number"));
            deliverBean.setAccount(rs.getString("Account"));
            
            Order.OrderBean orderBean = new Order.OrderBean();
            orderBean.setOrderID(resultSet.getLong("History_Id"));
            orderBean.setTotal(resultSet.getInt("Total"));
            orderBean.setTypeCount(resultSet.getInt("Type_Count"));
            orderBean.setStartTime(resultSet.getString("Start_Time"));
            orderBean.setOrderStatus(resultSet.getString("Final_Status")); // 與ORDERSTATUS  相同地位

            List<Order.OrderBean.MealsBean> mealsBeanXList = new ArrayList<>();

            ResultSet mealResultSet;
            String mealSql = "SELECT History_food.`Count`, History_food.Food_Name, History_food.Rest_Name" +
                    " FROM history_food" +
                    " WHERE history_food.History_Id = ?";
            preparedStatement = connection.prepareStatement(mealSql);
            preparedStatement.setLong(1, orderBean.getOrderID());
            mealResultSet = preparedStatement.executeQuery();

            /*mealsBeanX.setRestName(mealResultSet.getString("Rest_Name"));
            mealsBeanX.setRestAddress(mealResultSet.getString("Rest_Address"));*/
            while(mealResultSet.next()){
                Order.OrderBean.MealsBean mealsBean = new Order.OrderBean.MealsBean();

                mealsBean.setRestName(mealResultSet.getString("Rest_Name"));
                mealsBean.setFoodName(mealResultSet.getString("Food_Name"));
                mealsBean.setCount(mealResultSet.getInt("Count"));
                mealsBeanXList.add(mealsBean);
            }

            orderBean.setMeals(mealsBeanXList);
            order.setCustomer(customerBean);
            order.setOrder(orderBean);
            orders.add(order);
        }
        } catch (SQLException e) {
        	e.printStackTrace();
        }finally{
        	C3P0Util.close(connection);
        	return orders;
        }
	}

}
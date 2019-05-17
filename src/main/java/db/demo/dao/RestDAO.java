package db.demo.dao;

import com.google.gson.JsonObject;
import db.demo.connect.JdbcUtils;
import db.demo.javabean.Rest;
import menu.model.response.javabean.Menu;
import tool.ResultSetToJson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RestDAO {

    // 查詢所有合作餐廳
    public static JsonObject searchRestInfo(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        JsonObject jsonString = null;
        try {
            connection = JdbcUtils.getconn();
            String sql = "SELECT * FROM restaurant_info";
            preparedStatement = (PreparedStatement)connection.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData();
            jsonString = ResultSetToJson.ResultSetToJsonObject(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.close(preparedStatement,connection);
        }
        return jsonString;
    }

    // 利用 restName 和 restAddress 查詢 指定合作餐廳 的菜單
    public static JsonObject searchRestMenu(String restName, String restAddress){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        JsonObject jsonString = null;
        try {
            connection = JdbcUtils.getconn();
            String sql = "SELECT * FROM meal WHERE Rest_Id = (SELECT Rest_Id FROM restaurant_info WHERE Rest_Name= ? and Rest_Address= ?)";
            preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
            preparedStatement.setString(1,restName);
            preparedStatement.setString(2,restAddress);
            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData();
            jsonString = ResultSetToJson.ResultSetToJsonObject(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.close(preparedStatement,connection);
        }
        return jsonString;
    }

    // 新增 合作餐廳
    // 已測試成功
    public static void addRest(Rest rest)
    {
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
        	connection = JdbcUtils.getconn();
        	String sql = "INSERT INTO restaurant_info(Rest_Id, Rest_Name, Rest_Address, Description, Rest_Photo) VALUES(?, ?, ?, ?, ?);";
        	preparedStatement = connection.prepareStatement(sql);
        	preparedStatement.setLong(1, rest.getRestID());
        	preparedStatement.setString(2, rest.getRestName());
        	preparedStatement.setString(3, rest.getRestAddress());
        	preparedStatement.setString(4, rest.getDescription());
            preparedStatement.setString(5, rest.getRestPhoto());
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

    // 利用 restID 刪除 合作餐廳
    // 已測試成功
    public static void delRest(Long restID)
    {
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
        	connection = JdbcUtils.getconn();
        	String sql = "DELETE FROM restaurant_info WHERE Rest_Id = ?";
        	preparedStatement = connection.prepareStatement(sql);
        	preparedStatement.setLong(1, restID);
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

    // 新增 指定合作餐廳 的菜單
    // 已測試成功
    public static void addRestMenu(Menu menu)
    {
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
        	connection = JdbcUtils.getconn();
        	String sql = "INSERT INTO meal(Food_Id, Food_Name, Rest_Id, Cost, Description, Image) VALUES(?, ?, ?, ?, ?, ?);";
        	preparedStatement = connection.prepareStatement(sql);
        	preparedStatement.setLong(1, menu.getFoodID());
        	preparedStatement.setString(2, menu.getFood_Name());
        	preparedStatement.setLong(3, menu.getRestID());
        	preparedStatement.setInt(4, menu.getCost());
        	preparedStatement.setString(5, menu.getDescription());
        	preparedStatement.setString(6, menu.getImage());
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

    // 利用 restID 和 foodID 刪除 指定合作餐廳 的菜單
    // 已測試成功
    public static void delRestMenu(Long foodID)
    {
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
        	connection = JdbcUtils.getconn();
        	String sql = "DELETE FROM meal WHERE Food_Id = ?";
        	preparedStatement = connection.prepareStatement(sql);
        	preparedStatement.setLong(1, foodID);
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
}

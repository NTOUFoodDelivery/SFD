package db.demo.dao;


import db.a;
import db.demo.connect.JdbcUtils;
import db.demo.javabean.User;
import tool.ResultSetToJson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonObject;

public class UserDAO {

    // 登入
    public static int login(String account, String password, String userType){
        User u = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int userID = -1;
        try {
            connection = JdbcUtils.getconn();
            String sql = "select * from member where account=? and Password=? and User_Type=?";
            preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,userType);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                userID = resultSet.getInt("User_Id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            JdbcUtils.close(preparedStatement,connection);
        }
        return userID;
    }

    // 註冊
    public static void addUser(User user){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JdbcUtils.getconn();
            String sql = "INSERT INTO member(User_Id, account, Password, User_Name, Email, Phone_number, Last_Address, User_Type, User_Status) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
            preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
            preparedStatement.setInt(1,user.getUserID());
            preparedStatement.setString(2,user.getAccount());
            preparedStatement.setString(3,user.getPassword());
            preparedStatement.setString(4,user.getUserName());
            preparedStatement.setString(5,user.getEmail());
            preparedStatement.setString(6,user.getPhoneNumber());
            preparedStatement.setString(7,user.getLastAddress());
            preparedStatement.setString(8,user.getUserType());
            preparedStatement.setString(9,user.getUserStatus());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.close(preparedStatement,connection);
        }
    }

    // 利用 userID 查看 使用者目前身份
    public static JsonObject showUserIdentity(int userID)
    {
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        JsonObject jsonString = null;
        try {
            connection = JdbcUtils.getconn();
            String sql = "SELECT User_Status FROM member WHERE User_Id = ?";
            preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
            preparedStatement.setInt(1,userID);
            //不確定目前  我把*改成了User_Status  他resultSet 會只吃到User_Status嗎
            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData();
            jsonString = ResultSetToJson.ResultSetToJsonObject(resultSet);
            while(resultSet.next()) 
            {
            	String na = resultSet.getString("User_Status");
				System.out.println(na);
				System.out.println("你有拿到嗎?");
			}
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.close(preparedStatement,connection);
        }
        return jsonString;
    }

    // 利用 userID 刪除 使用者
    public static void delUser(int userID)
    {
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
        	connection = JdbcUtils.getconn();
        	String sql = "DELETE FROM member WHERE User_Id = ?";
        	preparedStatement = connection.prepareStatement(sql);
        	preparedStatement.setInt(1, userID);
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

    // 利用 userID ban 使用者
    // 暴風的寫法  請證實他 
    public static void banUser(int userID)              
    {
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
        	connection = JdbcUtils.getconn();
        	String sql = "UPDATE member\n" + 
        			"SET User_Status = ban\n" + 
        			"WHERE User_Id = ?;";
        	preparedStatement = connection.prepareStatement(sql);
        	preparedStatement.setInt(1, userID);
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

    // 利用 userID 看 資料庫 有無相同 userID
    public static boolean searchUser(int userID){ return false; }

    // 利用 userID 查詢 空閒的 外送員
    public static void searchIdelDeliver(int userID){}
    
    public static void main(String args[]) {
    	showUserIdentity(0);
	}
    
}

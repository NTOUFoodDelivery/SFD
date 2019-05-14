package db.demo.dao;


import db.demo.connect.JdbcUtils;
import db.demo.javabean.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {

    // 登入
    public static Long login(String account, String password, String userType){
        User u = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Long userID = -1L;
        try {
            connection = JdbcUtils.getconn();
            String sql = "select * from member where account=? and Password=? and User_Type=?";
            preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,userType);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                userID = resultSet.getLong("User_Id");
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
            preparedStatement.setLong(1,user.getUserID());
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
    // 已測試成功
    public static String showUserIdentity(Long userID)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String identity = null;
        try {
            connection = JdbcUtils.getconn();
            String sql = "SELECT User_Status FROM member WHERE User_Id = ?";
            preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
            preparedStatement.setLong(1,userID);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                identity = resultSet.getString("User_Status");
                //System.out.println(identity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.close(preparedStatement,connection);
        }
        return identity;
    }

    // 利用 userID 查看 使用者目前Type
    // 已測試成功
    public static String showUserType(Long userID)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String identity = null;
        try {
            connection = JdbcUtils.getconn();
            String sql = "SELECT User_Type FROM member WHERE User_Id = ?";
            preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
            preparedStatement.setLong(1,userID);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                identity = resultSet.getString("User_Type");
                //System.out.println(identity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.close(preparedStatement,connection);
        }
        return identity;
    }
    
    // 利用 userID 刪除 使用者
    // 已測試成功
    public static void delUser(Long userID)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JdbcUtils.getconn();
            String sql = "DELETE FROM member WHERE User_Id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userID);
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

    // 利用 userID 更改 使用者的Status
    // 已測試成功
    public static void modifyUserStatus(Long userID, String userStatus)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JdbcUtils.getconn();
            String sql = "UPDATE member SET User_Stauts = ? WHERE User_Id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userStatus);
            preparedStatement.setLong(2, userID);
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
    
    // 利用 userID 更改 使用者的Type
    // 已測試成功
    public static void modifyUserType(Long userID, String userType)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JdbcUtils.getconn();
            String sql = "UPDATE member SET User_Typr = ? WHERE User_Id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userType);
            preparedStatement.setLong(2, userID);
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
    // 已測試成功
    public static boolean searchUser(Long userID)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtils.getconn();
            String sql = "SELECT User_Id FROM member WHERE User_Id = ?";
            preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
            preparedStatement.setLong(1, userID);
            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData();
            if(resultSet.next())
            {
            	//System.out.println("有喔");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.close(preparedStatement,connection);
        }
        //System.out.println("沒喔");
        return false;
    }

    // 利用 userID 查詢 全部的空閒的 外送員 
    // 已測試成功
    public static ArrayList<Long> searchIdleDeliver()
    {
        ArrayList<Long> a = new ArrayList<Long>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        //int index = 0;
        try {
            connection = JdbcUtils.getconn();
            String sql = "SELECT User_Id FROM member WHERE User_Status = 'DELIVER_ON'";
            preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData();
            while(resultSet.next())
            {
                a.add(resultSet.getLong("User_Id"));
                //System.out.println(a.get(index));
                //index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.close(preparedStatement,connection);
        }
        return a;
    }
    
    // 食客or外送員  傳送客服回報給 管理者
    public static void addFeedback(Long FeedbackID, Long UserID, String Content)
    {
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
        	connection = JdbcUtils.getconn();
        	String sql = "INSERT INTO feedback(Feedback_Id, User_Id, Content) VALUES(?, ?, ?);";
        	preparedStatement = connection.prepareStatement(sql);
        	preparedStatement.setLong(1, FeedbackID);
        	preparedStatement.setLong(2, UserID);
        	preparedStatement.setString(3, Content);
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
    
    //管理者拿到feedback資訊
    public static void AdministratorGetFeedback(Long FeedbackID)
    {
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
        	connection = JdbcUtils.getconn();
        	String sql = "SELECT * FROM feedback WHERE Feedback_Id = ?;";
        	preparedStatement = connection.prepareStatement(sql);
        	preparedStatement.setLong(1, FeedbackID);
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
    
    // 管理者  傳送客服回報給 食客or外送員
    public static void replyFeedback(Long FeedbackID, String BackContent)
    {
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
        	connection = JdbcUtils.getconn();
        	String sql = "UPDATE feedback SET Back_Content = ? WHERE Feedback_Id = ?;";
        	preparedStatement = connection.prepareStatement(sql);
        	preparedStatement.setString(1, BackContent);
        	preparedStatement.setLong(2, FeedbackID);
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
    
    //食客or外送員拿到feedback資訊
    public static void CustomerOrDeliverGetFeedback(Long FeedbackID)
    {
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
        	connection = JdbcUtils.getconn();
        	String sql = "SELECT * FROM feedback WHERE Feedback_Id = ?;";
        	preparedStatement = connection.prepareStatement(sql);
        	preparedStatement.setLong(1, FeedbackID);
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

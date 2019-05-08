package db.demo.dao;


import db.demo.connect.JdbcUtils;
import db.demo.javabean.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // 登入
    public static boolean login(String userID, String password, String userType){
        User u = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean result = false;
        try {
            connection = JdbcUtils.getconn();
            String sql = "select * from member where User_Id=? and Password=? and User_Type=?";
            preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
            preparedStatement.setString(1,userID);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,userType);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                result = true;
            }
            else{
                result = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            JdbcUtils.close(preparedStatement,connection);
        }
        return result;
    }

    // 註冊
    public static void addUser(User user){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JdbcUtils.getconn();
            String sql = "INSERT INTO member(User_Id, Password, User_Name, Email, Phone_number, Last_Address, User_Type, User_Status) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
            preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getUserID());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setString(3,user.getUserName());
            preparedStatement.setString(4,user.getEmail());
            preparedStatement.setString(5,user.getPhoneNumber());
            preparedStatement.setString(6,user.getLastAddress());
            preparedStatement.setString(7,user.getUserType());
            preparedStatement.setString(8,user.getUserStatus());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.close(preparedStatement,connection);
        }
    }

    // 利用 userID 刪除 使用者
    public static void delUser(int userID){}

    // 利用 userID ban 使用者
    public static void banUser(int userID){}

    // 利用 userID 看 資料庫 有無相同 userID
    public static boolean searchUser(int userID){ return false; }

    // 利用 userID 查詢 空閒的 外送員
    public static void searchIdelDeliver(int userID){}
}

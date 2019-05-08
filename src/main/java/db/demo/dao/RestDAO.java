package db.demo.dao;

import com.google.gson.JsonObject;
import db.demo.connect.JdbcUtils;
import db.demo.javabean.Rest;
import db.demo.javabean.User;
import tool.ResultSetToJson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RestDAO {

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
}

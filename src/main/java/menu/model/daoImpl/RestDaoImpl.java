package menu.model.daoImpl;

import menu.model.dao.RestDao;
import menu.model.javabean.Rest;
import util.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestDaoImpl implements RestDao {

    /**
     *
     * @description: 查詢所有餐廳
     * @author BerSerKer
     * @date 2019-05-21 10:24
     * @param []
     * @return java.util.List<menu.model.javabean.Rest>
     */
    @Override
    public List<Rest> searchRestInfo() {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        List<Rest> restList = new ArrayList<>();
        String sql = "SELECT * FROM restaurant_info";
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData();
            while (resultSet.next()){
                Rest rest = new Rest();
                rest.setRestID(resultSet.getLong("Rest_Id"));
                rest.setRestName(resultSet.getString("Rest_Name"));
                rest.setRestAddress(resultSet.getString("Rest_Address"));
                rest.setDescription(resultSet.getString("Description"));
                rest.setRestPhoto(resultSet.getString("Rest_Photo"));
                restList.add(rest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            C3P0Util.close(connection);
        }
        return restList;
    }

    /**
     *
     * @description: 新增 餐廳
     * @author BerSerKer
     * @date 2019-05-21 10:25
     * @param [rest]
     * @return boolean
     */
    @Override
    public boolean addRest(Rest rest) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        boolean success = false;
        String sql = "INSERT INTO restaurant_info(Rest_Id, Rest_Name, Rest_Address, Description, Rest_Photo) VALUES(?, ?, ?, ?, ?);";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, rest.getRestID());
            preparedStatement.setString(2, rest.getRestName());
            preparedStatement.setString(3, rest.getRestAddress());
            preparedStatement.setString(4, rest.getDescription());
            preparedStatement.setString(5, rest.getRestPhoto());
            preparedStatement.executeUpdate();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            C3P0Util.close(connection);
        }
        return success;
    }

    /**
     *
     * @description: 刪除 餐廳
     * @author BerSerKer
     * @date 2019-05-21 10:25
     * @param [restID]
     * @return boolean
     */
    @Override
    public boolean delRest(Long restID) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        boolean success = false;
        String sql = "DELETE FROM restaurant_info WHERE Rest_Id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, restID);
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
}

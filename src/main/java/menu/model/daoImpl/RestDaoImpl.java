package menu.model.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import menu.model.dao.RestDao;
import menu.model.javabean.Rest;
import util.db.C3P0Util;

public class RestDaoImpl implements RestDao {

  /**
   * @return java.util.List<menu.model.javabean.Rest>
   * @description: 查詢所有餐廳
   * @author BerSerKer
   * @date 2019-05-21 10:24
   */
  @Override
  public List<Rest> searchRestInfo() {
    Connection connection = C3P0Util.getConnection();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    List<Rest> restList = new ArrayList<>();
    String sql = "SELECT * FROM restaurant_info";
    try {
      preparedStatement = connection.prepareStatement(sql);
      resultSet = preparedStatement.executeQuery();
      resultSet.getMetaData();
      while (resultSet.next()) {
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
    } finally {
      C3P0Util.close(connection, preparedStatement, resultSet);
      return restList;
    }
  }

  /**
   * @return boolean
   * @description: 新增 餐廳
   * @author BerSerKer
   * @date 2019-05-21 10:25
   */
  @Override
  public boolean addRest(Rest rest) {
    Connection connection = C3P0Util.getConnection();
    PreparedStatement preparedStatement = null;
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
    } finally {
      C3P0Util.close(connection, preparedStatement);
      return success;
    }
  }

  /**
   * @return boolean
   * @description: 刪除 餐廳
   * @author BerSerKer
   * @date 2019-05-21 10:25
   */
  @Override
  public boolean delRest(Long restID) {
    Connection connection = C3P0Util.getConnection();
    PreparedStatement preparedStatement = null;
    boolean success = false;
    String sql = "DELETE FROM restaurant_info WHERE Rest_Id = ?";
    try {
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setLong(1, restID);
      preparedStatement.executeUpdate();
      success = true;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      C3P0Util.close(connection, preparedStatement);
      return success;
    }
  }

  /**
   * @return boolean
   * @description: 修改 餐廳
   * @author thomas205327
   * @date 2019-05-29 20:01
   */
  @Override
  public boolean fixRest(Rest rest) {
    Connection connection = C3P0Util.getConnection();
    PreparedStatement preparedStatement = null;
    boolean success = false;
    String sql = "UPDATE restaurant_info SET Rest_Name = ?, Rest_Address = ?, Description = ?, Rest_Photo = ? WHERE Rest_Id = ?";
    try {
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, rest.getRestName());
      preparedStatement.setString(2, rest.getRestAddress());
      preparedStatement.setString(3, rest.getDescription());
      preparedStatement.setString(4, rest.getRestPhoto());
      preparedStatement.setLong(5, rest.getRestID());
      preparedStatement.executeUpdate();
      success = true;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      C3P0Util.close(connection, preparedStatement);
      return success;
    }
  }
}

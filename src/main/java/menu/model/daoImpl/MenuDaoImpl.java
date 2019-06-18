package menu.model.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import menu.model.dao.MenuDao;
import menu.model.javabean.Menu;
import util.db.C3P0Util;

public class MenuDaoImpl implements MenuDao {

  /**
   * @return java.util.List<menu.model.javabean.Menu>
   * @description: 查詢 指定餐廳 的菜單
   * @author BerSerKer
   * @date 2019-05-21 10:58
   */
  @Override
  public List<Menu> searchRestMenu(Long restID) {
    Connection connection = C3P0Util.getConnection();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    String sql = "SELECT * FROM meal WHERE Rest_Id = ?";
    List<Menu> menuList = new ArrayList<>();
    try {
      preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
      preparedStatement.setLong(1, restID);
      resultSet = preparedStatement.executeQuery();
      resultSet.getMetaData();
      while (resultSet.next()) {
        Menu menu = new Menu();
        menu.setFoodID(resultSet.getLong("Food_Id"));
        menu.setFoodName(resultSet.getString("Food_Name"));
        menu.setRestID(resultSet.getLong("Rest_Id"));
        menu.setCost(resultSet.getInt("Cost"));
        menu.setDescription(resultSet.getString("Description"));
        menu.setImage(resultSet.getString("Image"));
        menuList.add(menu);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      C3P0Util.close(connection, preparedStatement, resultSet);
      return menuList;
    }
  }

  /**
   * @param [restName, restAddress]
   * @return java.util.List<menu.model.javabean.Menu>
   * @description: 查詢 指定餐廳 的菜單
   * @author BerSerKer
   * @date 2019-05-21 11:00
   */
  @Override
  public List<Menu> searchRestMenu(String restName, String restAddress) {
    Connection connection = C3P0Util.getConnection();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    List<Menu> menuList = new ArrayList<>();
    String sql = "SELECT * FROM meal WHERE Rest_Id = (SELECT Rest_Id FROM restaurant_info WHERE Rest_Name= ? and Rest_Address= ?)";
    try {
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, restName);
      preparedStatement.setString(2, restAddress);
      resultSet = preparedStatement.executeQuery();
      resultSet.getMetaData();
      while (resultSet.next()) {
        Menu menu = new Menu();
        menu.setFoodID(resultSet.getLong("Food_Id"));
        menu.setFoodName(resultSet.getString("Food_Name"));
        menu.setRestID(resultSet.getLong("Rest_Id"));
        menu.setCost(resultSet.getInt("Cost"));
        menu.setDescription(resultSet.getString("Description"));
        menu.setImage(resultSet.getString("Image"));
        menuList.add(menu);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      C3P0Util.close(connection, preparedStatement, resultSet);
      return menuList;
    }
  }

  /**
   * @return boolean
   * @description: 新增 指定餐廳 的菜單
   * @author BerSerKer
   * @date 2019-05-21 11:02
   */
  @Override
  public boolean addRestMenu(Menu menu) {
    Connection connection = C3P0Util.getConnection();
    PreparedStatement preparedStatement = null;
    String sql = "INSERT INTO meal(Food_Id, Food_Name, Rest_Id, Cost, Description, Image) VALUES(?, ?, ?, ?, ?, ?);";
    boolean success = false;
    try {
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setLong(1, menu.getFoodID());
      preparedStatement.setString(2, menu.getFoodName());
      preparedStatement.setLong(3, menu.getRestID());
      preparedStatement.setInt(4, menu.getCost());
      preparedStatement.setString(5, menu.getDescription());
      preparedStatement.setString(6, menu.getImage());
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
   * @description: 刪除 指定餐廳 的菜單
   * @author BerSerKer
   * @date 2019-05-21 11:03
   */
  @Override
  public boolean delRestMenu(Long foodID) {
    Connection connection = C3P0Util.getConnection();
    PreparedStatement preparedStatement = null;
    boolean success = false;
    String sql = "DELETE FROM meal WHERE Food_Id = ?";
    try {
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setLong(1, foodID);
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
   * @description: 修改 指定餐廳 的菜單
   * @author thomas205327
   * @date 2019-05-29 20:01
   */
  @Override
  public boolean fixRestMenu(Menu menu) {
    Connection connection = C3P0Util.getConnection();
    PreparedStatement preparedStatement = null;
    boolean success = false;
    String sql = "UPDATE meal SET Food_Name = ?, Cost = ?, Description = ?, Image = ? WHERE Rest_Id = ? AND Food_Id = ?";
    try {
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, menu.getFoodName());
      preparedStatement.setInt(2, menu.getCost());
      preparedStatement.setString(3, menu.getDescription());
      preparedStatement.setString(4, menu.getImage());
      preparedStatement.setLong(5, menu.getRestID());
      preparedStatement.setLong(6, menu.getFoodID());
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

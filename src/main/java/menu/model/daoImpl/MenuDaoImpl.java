package menu.model.daoImpl;

import menu.model.dao.MenuDao;
import menu.model.javabean.Menu;
import util.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuDaoImpl implements MenuDao {

    /**
     *
     * @description: 查詢 指定餐廳 的菜單
     * @author BerSerKer
     * @date 2019-05-21 10:58
     * @param [restID]
     * @return java.util.List<menu.model.javabean.Menu>
     */
    @Override
    public List<Menu> searchRestMenu(Long restID) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String sql = "SELECT * FROM meal WHERE Rest_Id = ?";
        List<Menu> menuList = new ArrayList<>();
        try {
            preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
            preparedStatement.setLong(1,restID);
            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData();
            while(resultSet.next()){
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
        }finally{
            C3P0Util.close(connection);
            return menuList;
        }
    }

    /**
     *
     * @description: 查詢 指定餐廳 的菜單
     * @author BerSerKer
     * @date 2019-05-21 11:00
     * @param [restName, restAddress]
     * @return java.util.List<menu.model.javabean.Menu>
     */
    @Override
    public List<Menu> searchRestMenu(String restName, String restAddress) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        List<Menu> menuList = new ArrayList<>();
        String sql = "SELECT * FROM meal WHERE Rest_Id = (SELECT Rest_Id FROM restaurant_info WHERE Rest_Name= ? and Rest_Address= ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,restName);
            preparedStatement.setString(2,restAddress);
            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData();
            while(resultSet.next()){
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
        }finally{
            C3P0Util.close(connection);
            return menuList;
        }
    }

    /**
     *
     * @description: 新增 指定餐廳 的菜單
     * @author BerSerKer
     * @date 2019-05-21 11:02
     * @param [menu]
     * @return boolean
     */
    @Override
    public boolean addRestMenu(Menu menu) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
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

    /**
     *
     * @description: 刪除 指定餐廳 的菜單
     * @author BerSerKer
     * @date 2019-05-21 11:03
     * @param [foodID]
     * @return boolean
     */
    @Override
    public boolean delRestMenu(Long foodID) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        boolean success = false;
        String sql = "DELETE FROM meal WHERE Food_Id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, foodID);
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

    /**
     *
     * @description: 修改 指定餐廳 的菜單
     * @author thomas205327
     * @date 2019-05-29 20:01
     * @param [restID, foodID, foodName, cost, description, image]
     * @return boolean
     */
    @Override
    public boolean fixRestMenu(Long restID, Long foodID, String foodName, int cost, String description, String image)
    {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        boolean success = false;
        String sql = "UPDATE meal SET Food_Name = ?, Cost = ?, Description = ?, Image = ? WHERE Rest_Id = ? AMD Food_Id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, foodName);
            preparedStatement.setInt(2, cost);
            preparedStatement.setString(3, description);
            preparedStatement.setString(4, image);
            preparedStatement.setLong(5, restID);
            preparedStatement.setLong(5, foodID);
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

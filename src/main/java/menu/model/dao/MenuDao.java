package menu.model.dao;

import menu.model.javabean.Menu;

import java.util.List;

public interface MenuDao {
    // 查詢 指定餐廳 的菜單
    List<Menu> searchRestMenu(Long restID);
    // 查詢 指定餐廳 的菜單
    List <Menu>searchRestMenu(String restName, String restAddress);
    // 新增 指定餐廳 的菜單
    boolean addRestMenu(Menu menu);
    // 刪除 指定餐廳 的菜單
    boolean delRestMenu(Long foodID);
    // 修改 指定餐廳 的菜單
    boolean fixRestMenu(Long restID, Long foodID, String foodName, int cost, String description, String image);
}

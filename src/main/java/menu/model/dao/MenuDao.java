package menu.model.dao;

import java.util.List;

import menu.model.javabean.Menu;

public interface MenuDao {

  // 查詢 指定餐廳 的菜單
  List<Menu> searchRestMenu(Long restID);

  // 查詢 指定餐廳 的菜單
  List<Menu> searchRestMenu(String restName, String restAddress);

  // 新增 指定餐廳 的菜單
  boolean addRestMenu(Menu menu);

  // 刪除 指定餐廳 的菜單
  boolean delRestMenu(Long foodID);

  // 修改 指定餐廳 的菜單
  boolean fixRestMenu(Menu menu);
}

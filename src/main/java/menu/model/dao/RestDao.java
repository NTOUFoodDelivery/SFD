package menu.model.dao;

import menu.model.javabean.Rest;

import java.util.List;

public interface RestDao {
    // 查詢所有餐廳
    List<Rest> searchRestInfo();
    // 新增 餐廳
    boolean addRest(Rest rest);
    // 刪除 餐廳
    boolean delRest(Long restID);
    // 修改 餐廳
    boolean fixRest(Rest rest);
}

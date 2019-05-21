package menu.model.daoImpl;

import menu.model.dao.MenuDao;
import menu.model.javabean.Menu;

import java.util.List;

public class MenuDaoImpl implements MenuDao {
    @Override
    public List<Menu> searchRestMenu(Long restID) {
        return null;
    }

    @Override
    public List<Menu> searchRestMenu(String restName, String restAddress) {
        return null;
    }

    @Override
    public boolean addRestMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean delRestMenu(Long foodID) {
        return false;
    }
}

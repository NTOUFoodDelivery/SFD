package menu.controller.service;

import java.util.List;
import menu.model.daoImpl.MenuDaoImpl;
import menu.model.daoImpl.RestDaoImpl;
import menu.model.javabean.Menu;
import menu.model.javabean.Rest;
import menu.util.setting.RestCommand;

public class RestInfoService {

  private RestDaoImpl restDao;
  private MenuDaoImpl menuDao;

  /**
   * <p>拿到所有餐廳.</p>
   */
  public List<Rest> getRestInfo() {
    restDao = new RestDaoImpl();
    List<Rest> restList = restDao.searchRestInfo();
    restDao = null;
    return restList;
  }

  /**
   * <p>拿到 一家餐廳 的菜單.</p>
   *
   * @param rest 餐廳
   */
  public Object getRestMenu(Rest rest) {
    String restName = rest.getRestName();
    String restAddress = rest.getRestAddress();
    menuDao = new MenuDaoImpl();
    Object result = menuDao.searchRestMenu(restName, restAddress);
    menuDao = null;
    return result;
  }

  /**
   * <p>拿到 一家餐廳 的菜單.</p>
   */
  public List<Menu> getRestMenu(Long restID) {
    menuDao = new MenuDaoImpl();
    List<Menu> result = menuDao.searchRestMenu(restID);
    menuDao = null;
    return result;
  }

  /**
   * <p>更改 餐廳相關資訊.</p>
   *
   * @param restCommand 餐廳 指令
   * @param rest 餐廳
   */
  public boolean modifyRestInfo(RestCommand restCommand, Rest rest) {

    boolean result = false;
    if (restCommand != null) {
      restDao = new RestDaoImpl();
      switch (restCommand) {
        case ADD: {
          if (restDao.addRest(rest)) {
            result = true;
          }
          break;
        }
        case DELETE: {
          if (restDao.delRest(rest.getRestID())) {
            result = true;
          }
          break;
        }
        case EDIT: {
          if (restDao.fixRest(rest)) {
            result = true;
          }
          break;
        }
        default: {
          break;
        }
      }
      restDao = null;
    }
    return result;
  }

  /**
   * <p>更改 餐廳菜單資訊.</p>
   *
   * @param restCommand 餐廳 指令
   * @param menu 菜單
   */
  public boolean modifyRestMenu(RestCommand restCommand, Menu menu) {

    boolean result = false;
    if (restCommand != null) {
      menuDao = new MenuDaoImpl();
      switch (restCommand) {
        case ADD: {
          if (menuDao.addRestMenu(menu)) {
            result = true;
          }
          break;
        }
        case DELETE: {
          if (menuDao.delRestMenu(menu.getFoodID())) {
            result = true;
          }

          break;
        }
        case EDIT: {
          if (menuDao.fixRestMenu(menu)) {
            result = true;
          }
          break;
        }
        default: {
          break;
        }
      }
    }
    return result;
  }

}

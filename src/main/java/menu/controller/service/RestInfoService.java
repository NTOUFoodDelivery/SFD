package menu.controller.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletContext;
import menu.model.daoImpl.MenuDaoImpl;
import menu.model.daoImpl.RestDaoImpl;
import menu.model.javabean.Menu;
import menu.model.javabean.Rest;
import menu.util.setting.RestCommand;
import util.HttpCommonAction;

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
  public Object modifyRestInfo(RestCommand restCommand, Rest rest) {

    Object result = null;
    String msg = "Command :: " + restCommand.toString();
    if (restCommand != null) {

      restDao = new RestDaoImpl();
      switch (restCommand) {
        case ADD: {
          boolean success = restDao.addRest(rest);
          if (success) {
            msg += " work!!";
          } else {
            msg += " can not work!!";
          }
          result = HttpCommonAction.generateStatusResponse(success, msg);
          break;
        }
        case DELETE: {
          System.out.println(rest.getRestID());
          boolean success = restDao.delRest(rest.getRestID());
          System.out.println("ddxcvxdvffd");
          if (success) {
            msg += " work!!";
          } else {
            msg += " can not work!!";
          }
          result = HttpCommonAction.generateStatusResponse(success, msg);
          break;
        }
        case EDIT: {
          boolean success = restDao.fixRest(rest);
          if (success) {
            msg += " work!!";
          } else {
            msg += " can not work!!";
          }
          result = HttpCommonAction.generateStatusResponse(success, msg);
          break;
        }
        default: {
          break;
        }
      }
      restDao = null;
    } else {
      result = HttpCommonAction.generateStatusResponse(false, msg + " can not found!!");
    }
    return result;
  }

  /**
   * <p>更改 餐廳菜單資訊.</p>
   *
   * @param restCommand 餐廳 指令
   * @param menu 菜單
   */
  public Object modifyRestMenu(RestCommand restCommand, Menu menu, ServletContext servletContext) {

    Object result = null;
    String msg = "Command :: " + restCommand.toString();
    if (restCommand != null) {
      Map<Long, List<Menu>> restMenuHashMap = (ConcurrentHashMap<Long, List<Menu>>) servletContext
          .getAttribute("restMenuHashMap"); // 拿到 servlet context 紀錄 各家餐廳 的菜單
      Long restId = menu.getRestID();
      menuDao = new MenuDaoImpl();
      switch (restCommand) {
        case ADD: {
          boolean success = menuDao.addRestMenu(menu);
          if (success) {
            restMenuHashMap.put(restId, menuDao.searchRestMenu(restId));
            msg += " work!!";
          } else {
            msg += " can not work!!";
          }
          result = HttpCommonAction.generateStatusResponse(success, msg);
          break;
        }
        case DELETE: {
          boolean success = menuDao.delRestMenu(menu.getFoodID());
          if (success) {
            restMenuHashMap.remove(restId);
            msg += " work!!";
          } else {
            msg += " can not work!!";
          }
          result = HttpCommonAction.generateStatusResponse(success, msg);
          break;
        }
        case EDIT: {
          boolean success = menuDao.fixRestMenu(menu);
          if (success) {
            restMenuHashMap.replace(restId, menuDao.searchRestMenu(restId));
            msg += " work!!";
          } else {
            msg += " can not work!!";
          }
          result = HttpCommonAction.generateStatusResponse(success, msg);
          break;
        }
        default: {
          break;
        }
      }
    } else {
      result = HttpCommonAction.generateStatusResponse(false, msg + " can not found!!");
    }
    return result;
  }

}

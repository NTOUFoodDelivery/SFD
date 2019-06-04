package menu.controller.service;

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
  public Object getRestInfo() {
    restDao = new RestDaoImpl();
    Object result = restDao.searchRestInfo();
    restDao = null;
    return result;
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
  public Object getRestMenu(Long restID) {
    menuDao = new MenuDaoImpl();
    Object result = menuDao.searchRestMenu(restID);
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
  public Object modifyRestMenu(RestCommand restCommand, Menu menu) {

    Object result = null;
    String msg = "Command :: " + restCommand.toString();
    if (restCommand != null) {
      menuDao = new MenuDaoImpl();
      switch (restCommand) {
        case ADD: {
          boolean success = menuDao.addRestMenu(menu);
          if (success) {
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

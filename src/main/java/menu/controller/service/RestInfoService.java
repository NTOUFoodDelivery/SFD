package menu.controller.service;

import menu.model.daoImpl.MenuDaoImpl;
import menu.model.daoImpl.RestDaoImpl;
import menu.model.javabean.Menu;
import menu.model.javabean.Rest;
import menu.model.request.javabean.RestMenuReq;
import menu.util.setting.RestCommand;
import util.HttpCommonAction;

public class RestInfoService {
    private RestDaoImpl restDao;
    private MenuDaoImpl menuDao;

    // 拿到所有餐廳
    public Object getRestInfo(){
        restDao = new RestDaoImpl();
        Object result = restDao.searchRestInfo();
        restDao = null;
        return result;
    }

    // 拿到 一家餐廳 的菜單
    public Object getRestMenu(RestMenuReq restMenuReq){
        String restName = restMenuReq.getRestName();
        String restAddress = restMenuReq.getRestAddress();
        menuDao = new MenuDaoImpl();
        Object result = menuDao.searchRestMenu(restName,restAddress);
        menuDao = null;
        return result;
    }

    // 拿到 一家餐廳 的菜單
    public Object getRestMenu(Long restID){
        menuDao = new MenuDaoImpl();
        Object result = menuDao.searchRestMenu(restID);
        menuDao = null;
        return result;
    }

    // 更改 餐廳相關資訊
    public Object modifyRestInfo(RestCommand restCommand, Rest rest){

        Object result = null;
        String msg = "Command :: "+restCommand.toString();
        if(restCommand != null) {
            restDao = new RestDaoImpl();
            switch (restCommand) {
                case ADD: {
                    boolean success = restDao.addRest(rest);
                    if(success){
                        msg += " work!!";
                    } else {
                        msg += " can not work!!";
                    }
                    result = HttpCommonAction.generateStatusResponse(success,msg);
                    break;
                }
                case DELETE: {
                    boolean success = restDao.delRest(rest.getRestID());
                    if(success){
                        msg += " work!!";
                    } else {
                        msg += " can not work!!";
                    }
                    result = HttpCommonAction.generateStatusResponse(success,msg);
                    break;
                }
                case EDIT: {
//                    restDao.
                    break;
                }
            }
            restDao = null;
        } else {
            result = HttpCommonAction.generateStatusResponse(false,msg+" can not found!!");
        }
        return result;
    }

    // 更改 餐廳菜單資訊
    public Object modifyRestMenu(RestCommand restCommand, Menu menu){

        Object result = null;
        String msg = "Command :: "+restCommand.toString();
        if(restCommand != null) {
            menuDao = new MenuDaoImpl();
            switch (restCommand) {
                case ADD: {
                    boolean success = menuDao.addRestMenu(menu);
                    if(success){
                        msg += " work!!";
                    } else {
                        msg += " can not work!!";
                    }
                    result = HttpCommonAction.generateStatusResponse(success,msg);
                    break;
                }
                case DELETE: {
                    boolean success = menuDao.delRestMenu(menu.getFoodID());
                    if(success){
                        msg += " work!!";
                    } else {
                        msg += " can not work!!";
                    }
                    result = HttpCommonAction.generateStatusResponse(success,msg);
                    break;
                }
                case EDIT: {
                    break;
                }
            }
        }else {
            result = HttpCommonAction.generateStatusResponse(false,msg+" can not found!!");
        }
        return result;
    }

}

package menu.controller.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import db.demo.dao.RestDAO;
import db.demo.javabean.Rest;
import menu.model.javabean.MenuSetting;
import menu.model.request.javabean.RestMenuReq;
import menu.model.response.javabean.Menu;
import tool.javabean.CommonRequest;

import java.util.ArrayList;
import java.util.List;

public class RestInfoService {

    // 拿到所有餐廳
    public static JsonObject getRestInfo(){
        // 查詢 所有餐廳
        return RestDAO.searchRestInfo();
    }

    // 拿到 一家餐廳 的菜單
    public static JsonObject getRestMenu(RestMenuReq restMenuReq){
        String restName = restMenuReq.getRestName();
        String restAddress = restMenuReq.getRestAddress();
        // 查詢 一家餐廳 的菜單
        return RestDAO.searchRestMenu(restName,restAddress);
    }

    // 更改 餐廳相關資訊
    public static void modifyRestInfo(CommonRequest commonRequest){

        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        String cmd = commonRequest.getQuery().getCommand(); // 新增 刪除 修改
        List<Object> resultBeans = commonRequest.getResult();
        List<Rest> restList = new ArrayList<>();
        for(Object object: resultBeans){
            JsonObject jsonObject = gson.toJsonTree(object).getAsJsonObject();
            restList.add(gson.fromJson(jsonObject.toString(),Rest.class)); // 餐廳資訊物件
        }

        switch (cmd){
            case MenuSetting.Command.ADD:{
                System.out.println("add");
                for(Rest rest : restList){
                    RestDAO.addRest(rest);
                }
                break;
            }
            case MenuSetting.Command.DELETE:{
                System.out.println("delete");
                for(Rest rest : restList){
                    System.out.println(rest.getRestID());
                    RestDAO.delRest(rest.getRestID());
                }
                break;
            }
            case MenuSetting.Command.MODIFY:{
                System.out.println("modify");
                break;
            }
            default:{
                System.out.println("default");
                break;
            }
        }
    }

    // 更改 餐廳菜單資訊
    public static void modifyRestMenu(CommonRequest commonRequest){

        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        String cmd = commonRequest.getQuery().getCommand(); // 新增 刪除 修改
        List<Object> resultBeans = commonRequest.getResult();
        List<Menu> MenuList = new ArrayList<>();
        for(Object object: resultBeans){
            JsonObject jsonObject = gson.toJsonTree(object).getAsJsonObject();
            MenuList.add(gson.fromJson(jsonObject.toString(),Menu.class)); // 餐廳菜單資訊物件
        }

        switch (cmd){
            case MenuSetting.Command.ADD:{
                System.out.println("add");
                for(Menu menu : MenuList){
                    RestDAO.addRestMenu(menu);
                }
                break;
            }
            case MenuSetting.Command.DELETE:{
                System.out.println("delete");
                for(Menu menu : MenuList){
                    RestDAO.delRestMenu(menu.getFoodID());
                }
                break;
            }
            case MenuSetting.Command.MODIFY:{
                System.out.println("modify");
                break;
            }
            default:{
                System.out.println("default");
                break;
            }
        }
    }

}

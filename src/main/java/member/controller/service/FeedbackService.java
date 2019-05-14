package member.controller.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import db.demo.dao.UserDAO;
import member.model.javabean.FeedBack;
import member.model.javabean.MemberSetting;
import tool.HttpCommonAction;
import tool.javabean.CommonRequest;

import java.util.ArrayList;
import java.util.List;

public class FeedbackService {

    // 更改 回饋
    public static void modifyFeedback(CommonRequest commonRequest){
        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

        String cmd = commonRequest.getQuery().getCommand();
        List<Object> resultBeans = commonRequest.getResult();
        List<FeedBack> feedBackList = new ArrayList<>();
        for(Object object: resultBeans){
            JsonObject jsonObject = gson.toJsonTree(object).getAsJsonObject();
            feedBackList.add(gson.fromJson(jsonObject.toString(),FeedBack.class)); // feedBack資料存取
        }

        switch (cmd){
            case MemberSetting.Feedback.ADD:{
                System.out.println("ADD");
                for(FeedBack feedBack : feedBackList){
                    UserDAO.addFeedback(feedBack.getFeedBackID(),feedBack.getUserID(),feedBack.getContent());
                }
                break;
            }
            case MemberSetting.Feedback.ADMINISTRATORGET:{
                System.out.println("ADMINISTRATORGET");
                for(FeedBack feedBack : feedBackList){
                    UserDAO.AdministratorGetFeedback(feedBack.getFeedBackID());
                }
                break;
            }
            case MemberSetting.Feedback.REPLY:{
                System.out.println("REPLY");
                for(FeedBack feedBack : feedBackList){
                    UserDAO.replyFeedback(feedBack.getFeedBackID(),feedBack.getContent());
                }
                break;
            }
            case MemberSetting.Feedback.CUSTOMERORDELIVERGET:{
                System.out.println("CUSTOMERORDELIVERGET");
                for(FeedBack feedBack : feedBackList){
                    UserDAO.CustomerOrDeliverGetFeedback(feedBack.getFeedBackID());
                }
                break;
            }
            default:{
                System.out.println("default");
                break;
            }
        }
    }
}

package member.controller.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import db.demo.dao.UserDAO;
import member.model.javabean.FeedbackReq;
import member.model.javabean.MemberSetting;

import java.util.List;

public class FeedbackService {

    // 更改 回饋
    public static void modifyFeedback(FeedbackReq feedBack){
        Gson gson = new GsonBuilder().disableHtmlEscaping().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

        String cmd = feedBack.getQuery().getCommand();
        List<FeedbackReq.ResultBean> resultBeans = feedBack.getResult();

        switch (cmd){
            case MemberSetting.Feedback.ADD:{ // session
                System.out.println("ADD");
                for(FeedbackReq.ResultBean resultBean : resultBeans){
                    UserDAO.addFeedback(resultBean.getFacebackID(),resultBean.getUserID(),resultBean.getContent());
                }
                break;
            }
            case MemberSetting.Feedback.ADMINISTRATORGET:{
                System.out.println("ADMINISTRATORGET");
                for(FeedbackReq.ResultBean resultBean : resultBeans){
                    UserDAO.AdministratorGetFeedback(resultBean.getFacebackID());
                }
                break;
            }
            case MemberSetting.Feedback.REPLY:{
                System.out.println("REPLY");
                for(FeedbackReq.ResultBean resultBean : resultBeans){
                    UserDAO.replyFeedback(resultBean.getFacebackID(),resultBean.getBackContent());
                }
                break;
            }
            case MemberSetting.Feedback.CUSTOMERORDELIVERGET:{
                System.out.println("CUSTOMERORDELIVERGET");
                for(FeedbackReq.ResultBean resultBean : resultBeans){
                    UserDAO.CustomerOrDeliverGetFeedback(resultBean.getFacebackID());
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

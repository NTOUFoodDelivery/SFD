package member.model.daoImpl;

import db.demo.javabean.User;
import member.model.dao.UserDao;

import java.util.ArrayList;

public class UserDaoImpl implements UserDao
{

    @Override
    public Long loginID(String account, String password, String userType) {
        return null;
    }

    @Override
    public User loginUser(String account, String password, String userType) {
        return null;
    }

    @Override
    public boolean addUser(User user) {
        return false;
    }

    @Override
    public String showUserIdentity(Long userID) {
        return null;
    }

    @Override
    public String showUserType(Long userID) {
        return null;
    }

    @Override
    public boolean delUser(Long userID) {
        return false;
    }

    @Override
    public boolean modifyUserStatus(Long userID, String userStatus) {
        return false;
    }

    @Override
    public boolean modifyUserType(Long userID, String userType) {
        return false;
    }

    @Override
    public boolean searchUser(Long userID) {
        return false;
    }

    @Override
    public ArrayList<User> searchIdleDeliverUser() {
        return null;
    }

    @Override
    public boolean addFeedback(Long FeedbackID, Long UserID, String Content) {
        return false;
    }

    @Override
    public void AdministratorGetFeedback(Long FeedbackID) {

    }

    @Override
    public void replyFeedback(Long FeedbackID, String BackContent) {

    }

    @Override
    public void CustomerOrDeliverGetFeedback(Long FeedbackID) {

    }
}

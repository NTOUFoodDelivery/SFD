package member.model.daoImpl;

import member.model.dao.UserDao;
import member.model.javabean.FeedbackRes;
import member.model.javabean.User;
import util.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao
{
    /**
     *
     * @description: 登入
     * @author BerSerKer
     * @date 2019-05-20 22:15
     * @param [account, password, userType]
     * @return java.lang.Long
     */
    @Override
    public Long loginID(String account, String password, String userType) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Long userID = 0L;
        String sql = "select * from member where account=? and Password=? and User_Type=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,userType);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                userID = resultSet.getLong("User_Id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(connection);
        }
        return userID;
    }
    /**
     *
     * @description: 登入
     * @author BerSerKer
     * @date 2019-05-20 22:15
     * @param [account, password, userType]
     * @return member.model.javabean.User
     */
    @Override
    public User loginUser(String account, String password, String userType) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        User user = null ;
        String sql = "select * from member where account=? and Password=? and User_Type=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,account);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,userType);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                user = new User();
                user.setUserID(resultSet.getLong("User_Id"));
                user.setAccount(resultSet.getString("Account"));
                user.setEmail(resultSet.getString("Email"));
                user.setLastAddress(resultSet.getString("Last_Address"));
                user.setPassword(resultSet.getString("Password"));
                user.setPhoneNumber(resultSet.getString("Phone_Number"));
                user.setUserName(resultSet.getString("User_Name"));
                user.setUserStatus(resultSet.getString("User_Status"));
                user.setUserType(resultSet.getString("User_Type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(connection);
        }
        return user;
    }

    /**
     *
     * @description: 註冊
     * @author BerSerKer
     * @date 2019-05-20 22:36
     * @param [user]
     * @return boolean
     */
    @Override
    public boolean addUser(User user) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        boolean success = false;
        String sql = "INSERT INTO member(User_Id, account, Password, User_Name, Email, Phone_number, Last_Address, User_Type, User_Status) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,user.getUserID());
            preparedStatement.setString(2,user.getAccount());
            preparedStatement.setString(3,user.getPassword());
            preparedStatement.setString(4,user.getUserName());
            preparedStatement.setString(5,user.getEmail());
            preparedStatement.setString(6,user.getPhoneNumber());
            preparedStatement.setString(7,user.getLastAddress());
            preparedStatement.setString(8,user.getUserType());
            preparedStatement.setString(9,user.getUserStatus());
            preparedStatement.executeUpdate();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            C3P0Util.close(connection);
        }
        return success;
    }

    /**
     *
     * @description: 查看 使用者目前身份
     * @author BerSerKer
     * @date 2019-05-20 22:39
     * @param [userID]
     * @return java.lang.String
     */
    @Override
    public String showUserStatus(Long userID) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String identity = null;
        String sql = "SELECT User_Status FROM member WHERE User_Id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,userID);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                identity = resultSet.getString("User_Status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(connection);
        }
        return identity;
    }
    /**
     *
     * @description: 查看 使用者目前Type
     * @author BerSerKer
     * @date 2019-05-20 22:44
     * @param [userID]
     * @return java.lang.String
     */
    @Override
    public String showUserType(Long userID) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String userType = null;
        String sql = "SELECT User_Type FROM member WHERE User_Id = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,userID);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                userType = resultSet.getString("User_Type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            C3P0Util.close(connection);
        }
        return userType;
    }

    /**
     *
     * @description: 刪除 使用者
     * @author BerSerKer
     * @date 2019-05-20 22:46
     * @param [userID]
     * @return boolean
     */
    @Override
    public boolean delUser(Long userID) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        String sql = "DELETE FROM member WHERE User_Id = ?";
        boolean success = false;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userID);
            preparedStatement.executeUpdate();
            success = true;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            C3P0Util.close(connection);
        }
        return success;
    }

    /**
     *
     * @description: 更改 使用者的Status
     * @author BerSerKer
     * @date 2019-05-20 22:48
     * @param [userID, userStatus]
     * @return boolean
     */
    @Override
    public boolean modifyUserStatus(Long userID, String userStatus) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        String sql = "UPDATE member SET User_Status = ? WHERE User_Id = ?;";
        boolean success = false;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userStatus);
            preparedStatement.setLong(2, userID);
            preparedStatement.executeUpdate();
            success = true;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            C3P0Util.close(connection);
        }
        return success;
    }

    /**
     *
     * @description: 更改 使用者的Type
     * @author BerSerKer
     * @date 2019-05-20 22:50
     * @param [userID, userType]
     * @return boolean
     */
    @Override
    public boolean modifyUserType(Long userID, String userType) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        String sql = "UPDATE member SET User_Type = ? WHERE User_Id = ?;";
        boolean success = false;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userType);
            preparedStatement.setLong(2, userID);
            preparedStatement.executeUpdate();
            success = true;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            C3P0Util.close(connection);
        }
        return success;
    }

    /**
     *
     * @description: 找尋 有無該使用者
     * @author BerSerKer
     * @date 2019-05-20 22:54
     * @param [userID]
     * @return boolean
     */
    @Override
    public boolean searchUser(Long userID) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String sql = "SELECT User_Id FROM member WHERE User_Id = ?";
        boolean success = false;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userID);
            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData();
            if(resultSet.next()){ success = true;}
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            C3P0Util.close(connection);
        }
        return success;
    }
    
    /**
     *  
     * @description: 查詢 全部的空閒的 外送員
     * @author BerSerKer
     * @date 2019-05-20 23:10
     * @param []
     * @return java.util.List<member.model.javabean.User>
     */
    @Override
    public List<User> searchIdleDeliverUser() {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String sql = "SELECT * FROM member WHERE User_Status = 'DELIVER_ON'";
        List<User> userList = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            resultSet.getMetaData();
            while(resultSet.next())
            {
                User user = new User();
                user.setUserID(resultSet.getLong("User_Id"));
                user.setAccount(resultSet.getString("Account"));
                user.setEmail(resultSet.getString("Email"));
                user.setLastAddress(resultSet.getString("Last_Address"));
                user.setPassword(resultSet.getString("Password"));
                user.setPhoneNumber(resultSet.getString("Phone_Number"));
                user.setUserName(resultSet.getString("User_Name"));
                user.setUserStatus(resultSet.getString("User_Status"));
                user.setUserType(resultSet.getString("User_Type"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            C3P0Util.close(connection);
        }
        return userList;
    }

    /**
     *  
     * @description: 食客/外送員 傳送客服 給 管理者
     * @author BerSerKer
     * @date 2019-05-20 23:11
     * @param [FeedbackID, UserID, Content]
     * @return boolean
     */
    @Override
    public boolean addFeedback(Long FeedbackID, Long UserID, String Content) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        String sql = "INSERT INTO feedback(Feedback_Id, User_Id, Content) VALUES(?, ?, ?);";
        boolean success = false;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, FeedbackID);
            preparedStatement.setLong(2, UserID);
            preparedStatement.setString(3, Content);
            preparedStatement.executeUpdate();
            success = true;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            C3P0Util.close(connection);
        }
        return success;
    }

    /**
     *
     * @description: 管理者 回覆客服
     * @author BerSerKer
     * @date 2019-05-20 23:32
     * @param [FeedbackID, BackContent]
     * @return void
     */
    @Override
    public boolean replyFeedback(Long FeedbackID, String BackContent) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        String sql = "UPDATE feedback SET Back_Content = ? WHERE Feedback_Id = ?;";
        boolean success = false;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, BackContent);
            preparedStatement.setLong(2, FeedbackID);
            preparedStatement.executeUpdate();
            success = true;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            C3P0Util.close(connection);
        }
        return success;
    }

    /**
     *
     * @description: 食客/外送員 拿到feedback資訊
     * @author BerSerKer
     * @date 2019-05-20 23:52
     * @param [userID]
     * @return java.util.List<member.model.javabean.FeedbackRes>
     */
    @Override
    public List<FeedbackRes> searchFeedback(Long userID) {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String sql = "SELECT * FROM feedback WHERE User_Id = ?";
        List <FeedbackRes> feedbackList = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,userID);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                FeedbackRes feedbackRes = new FeedbackRes();
                feedbackRes.setFeedbackID(resultSet.getLong("Feedback_Id"));
                feedbackRes.setUserID(resultSet.getLong("User_Id"));
                feedbackRes.setContent(resultSet.getString("Content"));
                feedbackRes.setBackContent(resultSet.getString("Back_Content"));
                feedbackList.add(feedbackRes);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            C3P0Util.close(connection);
        }
        return feedbackList;
    }

    /**
     *  
     * @description: 管理員 拿到feedback資訊
     * @author BerSerKer
     * @date 2019-05-21 00:48
     * @param []
     * @return java.util.List<member.model.javabean.FeedbackRes>
     */
    @Override
    public List<FeedbackRes> searchFeedback() {
        Connection connection = C3P0Util.getConnection();
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String sql = "SELECT * FROM feedback";
        List <FeedbackRes> feedbackList = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                FeedbackRes feedbackRes = new FeedbackRes();
                feedbackRes.setFeedbackID(resultSet.getLong("Feedback_Id"));
                feedbackRes.setUserID(resultSet.getLong("User_Id"));
                feedbackRes.setContent(resultSet.getString("Content"));
                feedbackRes.setBackContent(resultSet.getString("Back_Content"));
                feedbackList.add(feedbackRes);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            C3P0Util.close(connection);
        }
        return feedbackList;
    }
}

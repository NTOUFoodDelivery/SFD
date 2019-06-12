package member.model.daoImpl;

import member.model.dao.UserDao;
import member.model.javabean.Feedback;
import member.model.javabean.User;
import member.util.setting.UserStatus;
import member.util.setting.UserType;
import util.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

  /**
   * <p>找尋 符合條件的 使用者.</p>
   *
   * @param account account
   * @param password password
   * @param userType userType
   * @return Long
   */
  @Override
  public Long searchUserId(String account, String password, String userType) {
    Connection connection = C3P0Util.getConnection();
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    Long userID = 0L;
    String sql = "select * from member where account=? and Password=? and User_Type=?";
    try {
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, account);
      preparedStatement.setString(2, password);
      preparedStatement.setString(3, userType);
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        userID = resultSet.getLong("User_Id");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      C3P0Util.close(connection);
      return userID;
    }
  }

  /**
   * <p>找尋 符合條件的 使用者.</p>
   *
   * @param account account
   * @param password password
   * @param userType userType
   * @return Long
   */
  @Override
  public List<String> searchUseraccount() {
	    Connection connection = C3P0Util.getConnection();
	    PreparedStatement preparedStatement;
	    ResultSet resultSet;
	    String userAccount = null;
	    String sql = "SELECT account FROM member ";
	    List<String> accountList = new ArrayList<>();
	    try {
	      preparedStatement = connection.prepareStatement(sql);
	      resultSet = preparedStatement.executeQuery();
	      while (resultSet.next()) {
	    	  userAccount = resultSet.getString("User_Account");
	        accountList.add(userAccount);
	      }
	    } catch (SQLException e) {
	      e.printStackTrace();
	    } finally {
	      C3P0Util.close(connection);
	      return accountList;
	    }
	  }

  /**
   * <p>找尋 符合條件的 使用者.</p>
   *
   * @param account account
   * @param password password
   * @param userType userType
   * @return User
   */
  @Override
  public User searchUser(String account, String password, String userType) {
    Connection connection = C3P0Util.getConnection();
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    User user = null;
    String sql = "select * from member where account=? and Password=? and User_Type=?";
    try {
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, account);
      preparedStatement.setString(2, password);
      preparedStatement.setString(3, userType);
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        user = new User();
        user.setUserId(resultSet.getLong("User_Id"));
        user.setAccount(resultSet.getString("Account"));
        user.setEmail(resultSet.getString("Email"));
        user.setLastAddress(resultSet.getString("Last_Address"));
        user.setPassword(resultSet.getString("Password"));
        user.setPhoneNumber(resultSet.getString("Phone_Number"));
        user.setUserName(resultSet.getString("User_Name"));
        user.setUserStatus(UserStatus.getUserStatus(resultSet.getString("User_Status")));
        user.setUserNow(UserType.getUserType(resultSet.getString("User_Now")));
        user.setUserType(UserType.getUserType(resultSet.getString("User_Type")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      C3P0Util.close(connection);
      return user;
    }
  }

  /**
   * <p>找尋 符合條件的 使用者.</p>
   *
   * @param account account
   * @param password password
   * @return User
   */
  @Override
  public User searchUser(String account, String password) {
    Connection connection = C3P0Util.getConnection();
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    User user = null;
    String sql = "select * from member where account=? and Password=?";
    try {
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, account);
      preparedStatement.setString(2, password);
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        user = new User();
        user.setUserId(resultSet.getLong("User_Id"));
        user.setAccount(resultSet.getString("Account"));
        user.setEmail(resultSet.getString("Email"));
        user.setLastAddress(resultSet.getString("Last_Address"));
        user.setPassword(resultSet.getString("Password"));
        user.setPhoneNumber(resultSet.getString("Phone_Number"));
        user.setUserName(resultSet.getString("User_Name"));
        user.setUserStatus(UserStatus.getUserStatus(resultSet.getString("User_Status")));
        user.setUserNow(UserType.getUserType(resultSet.getString("User_Now")));
        user.setUserType(UserType.getUserType(resultSet.getString("User_Type")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      C3P0Util.close(connection);
      return user;
    }
  }

  /**
   * <p>找尋 符合條件的 使用者.</p>
   *
   * @param account account
   * @return User
   */
  @Override
  public User searchUser(String account) {
    Connection connection = C3P0Util.getConnection();
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    User user = null;
    String sql = "select * from member where account=?";
    try {
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, account);
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        user = new User();
        user.setUserId(resultSet.getLong("User_Id"));
        user.setAccount(resultSet.getString("Account"));
        user.setEmail(resultSet.getString("Email"));
        user.setLastAddress(resultSet.getString("Last_Address"));
        user.setPassword(resultSet.getString("Password"));
        user.setPhoneNumber(resultSet.getString("Phone_Number"));
        user.setUserName(resultSet.getString("User_Name"));
        user.setUserStatus(UserStatus.getUserStatus(resultSet.getString("User_Status")));
        user.setUserNow(UserType.getUserType(resultSet.getString("User_Now")));
        user.setUserType(UserType.getUserType(resultSet.getString("User_Type")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      C3P0Util.close(connection);
      return user;
    }
  }


  /**
   * <p>得到使用者資訊.</p>
   *
   * @param userId userId
   * @return User
   */
  @Override
  public User showUser(Long userId) {
    Connection connection = C3P0Util.getConnection();
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    User user = null;
    String sql = "select * from member where User_Id =?";
    try {
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setLong(1, userId);
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        user = new User();
        user.setUserId(resultSet.getLong("User_Id"));
        user.setAccount(resultSet.getString("Account"));
        user.setEmail(resultSet.getString("Email"));
        user.setLastAddress(resultSet.getString("Last_Address"));
        user.setPassword(resultSet.getString("Password"));
        user.setPhoneNumber(resultSet.getString("Phone_Number"));
        user.setUserName(resultSet.getString("User_Name"));
        user.setUserStatus(UserStatus.getUserStatus(resultSet.getString("User_Status")));
        user.setUserNow(UserType.getUserType(resultSet.getString("User_Now")));
        user.setUserType(UserType.getUserType(resultSet.getString("User_Type")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      C3P0Util.close(connection);
      return user;
    }
  }


  /**
   * <p>註冊.</p>
   *
   * @param user user
   * @return boolean
   */
  @Override
  public boolean addUser(User user) {
    Connection connection = C3P0Util.getConnection();
    PreparedStatement preparedStatement;
    boolean success = false;
    String sql = "INSERT INTO member(User_Id, account, Password, User_Name, Email, Phone_number, Last_Address, User_Type, User_Now, User_Status) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    try {
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setLong(1, user.getUserId());
      preparedStatement.setString(2, user.getAccount());
      preparedStatement.setString(3, user.getPassword());
      preparedStatement.setString(4, user.getUserName());
      preparedStatement.setString(5, user.getEmail());
      preparedStatement.setString(6, user.getPhoneNumber());
      preparedStatement.setString(7, user.getLastAddress());
      preparedStatement.setString(8, user.getUserType().toString());
      preparedStatement.setString(9, user.getUserNow().toString());
      preparedStatement.setString(10, user.getUserStatus().toString());
      preparedStatement.executeUpdate();
      success = true;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      C3P0Util.close(connection);
      return success;
    }
  }


  /**
   * <p>查看 使用者目前身份.</p>
   *
   * @param userID userID
   * @return String
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
      preparedStatement.setLong(1, userID);
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        identity = resultSet.getString("User_Status");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      C3P0Util.close(connection);
      return identity;
    }
  }


  /**
   * <p>查看 使用者目前Type.</p>
   *
   * @param userId userId
   * @return String
   */
  @Override
  public String showUserType(Long userId) {
    Connection connection = C3P0Util.getConnection();
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    String userType = null;
    String sql = "SELECT User_Type FROM member WHERE User_Id = ?";
    try {
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setLong(1, userId);
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        userType = resultSet.getString("User_Type");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      C3P0Util.close(connection);
      return userType;
    }
  }


  /**
   * <p>刪除 使用者.</p>
   *
   * @param userId userId
   * @return boolean
   */
  @Override
  public boolean delUser(Long userId) {
    Connection connection = C3P0Util.getConnection();
    PreparedStatement preparedStatement;
    String sql = "DELETE FROM member WHERE User_Id = ?";
    boolean success = false;
    try {
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setLong(1, userId);
      preparedStatement.executeUpdate();
      success = true;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      C3P0Util.close(connection);
      return success;
    }
  }


  /**
   * <p>更改 使用者的Status.</p>
   *
   * @param userId userId
   * @param userStatus userStatus
   * @return boolean
   */
  @Override
  public boolean modifyUserStatus(Long userId, String userStatus) {
    Connection connection = C3P0Util.getConnection();
    PreparedStatement preparedStatement;
    String sql = "UPDATE member SET User_Status = ? WHERE User_Id = ?;";
    boolean success = false;
    try {
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, userStatus);
      preparedStatement.setLong(2, userId);
      preparedStatement.executeUpdate();
      success = true;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      C3P0Util.close(connection);
      return success;
    }
  }
  
  /**
   * <p>更改 使用者的Now.</p>
   *
   * @param userId userId
   * @param userStatus userStatus
   * @return boolean
   */
  @Override
  public boolean modifyUserNow(Long userId, String userNow) {
    Connection connection = C3P0Util.getConnection();
    PreparedStatement preparedStatement;
    
    String sql = "UPDATE member SET User_Now = ? WHERE User_Id = ?;";
    boolean success = false;
    try {
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, userNow);
      preparedStatement.setLong(2, userId);
      preparedStatement.executeQuery();
      success = true;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      C3P0Util.close(connection);
      return success;
    }
  }

  /**
   * <p>獲得 使用者的Now.</p>
   *
   * @param userId userId
   * @param userStatus userStatus
   * @return boolean
   */
  @Override
  public String showUserNow(Long userID) {
	    Connection connection = C3P0Util.getConnection();
	    PreparedStatement preparedStatement;
	    ResultSet resultSet;
	    String identity = null;
	    String sql = "SELECT User_Now FROM member WHERE User_Id = ?";
	    try {
	      preparedStatement = connection.prepareStatement(sql);
	      preparedStatement.setLong(1, userID);
	      resultSet = preparedStatement.executeQuery();
	      if (resultSet.next()) {
	        identity = resultSet.getString("User_Now");
	      }
	    } catch (SQLException e) {
	      e.printStackTrace();
	    } finally {
	      C3P0Util.close(connection);
	      return identity;
	    }
	  }

  /**
   * @param [userID, userType]
   * @return boolean
   * @description: 更改 使用者的Type
   * @author BerSerKer
   * @date 2019-05-20 22:50
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
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      C3P0Util.close(connection);
      return success;
    }

  }

  /**
   * @return boolean
   * @description: 找尋 有無該使用者
   * @author BerSerKer
   * @date 2019-05-20 22:54
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
      if (resultSet.next()) {
        success = true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      C3P0Util.close(connection);
      return success;
    }
  }

  /**
   * @return java.util.List<member.model.javabean.User>
   * @description: 查詢 全部的空閒的 外送員
   * @author BerSerKer
   * @date 2019-05-20 23:10
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
      while (resultSet.next()) {
        User user = new User();
        user.setUserId(resultSet.getLong("User_Id"));
        user.setAccount(resultSet.getString("Account"));
        user.setEmail(resultSet.getString("Email"));
        user.setLastAddress(resultSet.getString("Last_Address"));
        user.setPassword(resultSet.getString("Password"));
        user.setPhoneNumber(resultSet.getString("Phone_Number"));
        user.setUserName(resultSet.getString("User_Name"));
        user.setUserStatus(UserStatus.getUserStatus(resultSet.getString("User_Status")));
        user.setUserType(UserType.getUserType(resultSet.getString("User_Type")));
        userList.add(user);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      C3P0Util.close(connection);
      return userList;
    }
  }

  /**
   * @param [FeedbackID, UserID, Content]
   * @return boolean
   * @description: 食客/外送員 傳送客服 給 管理者
   * @author BerSerKer
   * @date 2019-05-20 23:11
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
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      C3P0Util.close(connection);
      return success;
    }
  }

  /**
   * @param [FeedbackID, BackContent]
   * @return void
   * @description: 管理者 回覆客服
   * @author BerSerKer
   * @date 2019-05-20 23:32
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
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      C3P0Util.close(connection);
      return success;
    }
  }

  /**
   * @return java.util.List<member.model.javabean.Feedback>
   * @description: 食客/外送員 拿到feedback資訊
   * @author BerSerKer
   * @date 2019-05-20 23:52
   */
  @Override
  public List<Feedback> searchFeedback(Long userID) {
    Connection connection = C3P0Util.getConnection();
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    String sql = "SELECT * FROM feedback WHERE User_Id = ?";
    List<Feedback> feedbackList = new ArrayList<>();
    try {
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setLong(1, userID);
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Feedback feedbackRes = new Feedback();
        feedbackRes.setFeedbackId(resultSet.getLong("Feedback_Id"));
        feedbackRes.setUserID(resultSet.getLong("User_Id"));
        feedbackRes.setContent(resultSet.getString("Content"));
        feedbackRes.setBackContent(resultSet.getString("Back_Content"));
        feedbackList.add(feedbackRes);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      C3P0Util.close(connection);
      return feedbackList;
    }
  }

  /**
   * @return java.util.List<member.model.javabean.Feedback>
   * @description: 管理員 拿到feedback資訊
   * @author BerSerKer
   * @date 2019-05-21 00:48
   */
  @Override
  public List<Feedback> searchFeedback() {
    Connection connection = C3P0Util.getConnection();
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    String sql = "SELECT * FROM feedback";
    List<Feedback> feedbackList = new ArrayList<>();
    try {
      preparedStatement = connection.prepareStatement(sql);
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Feedback feedbackRes = new Feedback();
        feedbackRes.setFeedbackId(resultSet.getLong("Feedback_Id"));
        feedbackRes.setUserID(resultSet.getLong("User_Id"));
        feedbackRes.setContent(resultSet.getString("Content"));
        feedbackRes.setBackContent(resultSet.getString("Back_Content"));
        feedbackList.add(feedbackRes);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      C3P0Util.close(connection);
      return feedbackList;
    }
  }
}

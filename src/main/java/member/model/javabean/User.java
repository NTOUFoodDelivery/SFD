package member.model.javabean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import member.util.setting.UserStatus;
import member.util.setting.UserType;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class User {

  @SerializedName("User_Id")
  @Expose
  private long userID;
  @SerializedName("Account")
  @Expose
  private String account;
  @SerializedName("Password")
  @Expose
  private String password;
  @SerializedName("User_Name")
  @Expose
  private String userName;
  @SerializedName("Email")
  @Expose
  private String email;
  @SerializedName("Phone_Number")
  @Expose
  private String phoneNumber;
  @SerializedName("Last_Address")
  @Expose
  private String lastAddress;
  @SerializedName("User_Type")
  @Expose
  private UserType userType;
  @SerializedName("User_Status")
  @Expose
  private UserStatus userStatus;


  public long getUserID() {
    return userID;
  }

  public void setUserID(long userID) {
    this.userID = userID;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getLastAddress() {
    return lastAddress;
  }

  public void setLastAddress(String lastAddress) {
    this.lastAddress = lastAddress;
  }

  public UserType getUserType() {
    return userType;
  }

  public void setUserType(UserType userType) {
    this.userType = userType;
  }

  public UserStatus getUserStatus() {
    return userStatus;
  }

  public void setUserStatus(UserStatus userStatus) {
    this.userStatus = userStatus;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("userID", userID)
        .append("account", account)
        .append("password", password)
        .append("userName", userName)
        .append("email", email)
        .append("phoneNumber", phoneNumber)
        .append("lastAddress", lastAddress)
        .append("userType", userType)
        .append("userStatus", userStatus)
        .toString();
  }
}
package db.demo.javabean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    private String userType;
    @SerializedName("User_Status")
    @Expose
    private String userStatus;


    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getAccount() { return account; }

    public void setAccount(String account) { this.account = account; }

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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

}
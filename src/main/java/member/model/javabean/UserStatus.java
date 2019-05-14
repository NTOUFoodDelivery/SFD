package member.model.javabean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserStatus {
    /**
     * User_Id : 231
     * User_Status : ftdyh
     */

    @SerializedName("User_Id")
    @Expose
    private Long userID;
    @SerializedName("User_Status")
    @Expose
    private String userStatus;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

}

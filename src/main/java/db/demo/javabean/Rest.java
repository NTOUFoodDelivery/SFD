package db.demo.javabean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rest {

    @SerializedName("Rest_Id")
    @Expose
    private long restID;
    @SerializedName("Rest_Name")
    @Expose
    private String restName;
    @SerializedName("Rest_Address")
    @Expose
    private String restAddress;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Rest_Photo")
    @Expose
    private String restPhoto;

    public long getRestID() {
        return restID;
    }

    public void setRestID(long restID) {
        this.restID = restID;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public String getRestAddress() {
        return restAddress;
    }

    public void setRestAddress(String restAddress) {
        this.restAddress = restAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRestPhoto() {
        return restPhoto;
    }

    public void setRestPhoto(String restPhoto) {
        this.restPhoto = restPhoto;
    }
}

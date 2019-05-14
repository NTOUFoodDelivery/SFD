package menu.model.response.javabean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestInfo {
    /**
     * Rest_Id : tfydhn
     * Rest_Name : obuvygfdh
     * Rest_address : ygfhdjsk
     * Description : ygfhjd
     */

    @SerializedName("Rest_Id")
    @Expose
    private String restID;
    @SerializedName("Rest_Name")
    @Expose
    private String restName;
    @SerializedName("Rest_address")
    @Expose
    private String restAddress;
    @SerializedName("Description")
    @Expose
    private String description;

    public String getRestID() {
        return restID;
    }

    public void setRestID(String restID) {
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

}

package menu.model.javabean;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Rest {

    /**
     * Rest_Id : 123
     * Rest_Name : test1
     * Rest_Address : test1
     * Description : test1
     * Rest_Photo : url
     */

    @SerializedName("Rest_Id")
    private Long restID;
    @SerializedName("Rest_Name")
    private String restName;
    @SerializedName("Rest_Address")
    private String restAddress;
    @SerializedName("Description")
    private String description;
    @SerializedName("Rest_Photo")
    private String restPhoto;

    public Long getRestID() {
        return restID;
    }

    public void setRestID(Long restID) {
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("restID", restID)
                .append("restName", restName)
                .append("restAddress", restAddress)
                .append("description", description)
                .append("restPhoto", restPhoto)
                .toString();
    }
}

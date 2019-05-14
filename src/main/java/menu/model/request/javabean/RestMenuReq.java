package menu.model.request.javabean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestMenuReq {
    /**
     * restName : Apple203
     * restAddress : 中正路111號
     */

    @SerializedName("Rest_Name")
    @Expose
    private String restName;
    @SerializedName("Rest_Address")
    @Expose
    private String restAddress;

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
}

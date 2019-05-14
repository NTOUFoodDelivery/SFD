package order.model.javabean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PushResult {
    /**
     * deliverID : 213
     * orderID : f23trkogire
     * accept : true
     */

    @SerializedName("Deliver_Id")
    @Expose
    private Long deliverID;
    @SerializedName("Order_Id")
    @Expose
    private Long orderID;
    @SerializedName("Accept")
    @Expose
    private boolean accept;

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public Long getDeliverID() {
        return deliverID;
    }

    public void setDeliverID(Long deliverID) {
        this.deliverID = deliverID;
    }
}

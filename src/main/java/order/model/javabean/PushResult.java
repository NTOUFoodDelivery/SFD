package order.model.javabean;

public class PushResult {
    /**
     * deliverID : 213
     * orderID : f23trkogire
     * accept : true
     */

    private Long deliverID;
    private Long orderID;
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

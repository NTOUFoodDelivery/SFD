package order.model.javabean;

public class Message {
    /**
     * userID : 123
     * deliver : 456
     * msg : HI
     * time : sd
     */

    private Long userID;
    private Long deliver;
    private String msg;
    private String time;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getDeliver() {
        return deliver;
    }

    public void setDeliver(Long deliver) {
        this.deliver = deliver;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

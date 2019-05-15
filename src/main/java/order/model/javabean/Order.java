package order.model.javabean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {
    /**
     * Order_Id : 12314
     * Customer_Id : 123
     * Customer_Id : 243
     * Start_Time : tfdyhj
     * Rest_Address : fdsk
     * Rest_Name : dsf
     * Address : dslkvmds
     * Type_Count : 1
     * Total : 634
     * meals : [{"Food_Name": dc, "Food_Id":1236,"Count":5}]
     * Order_Status : vlcd;sx
     * Other : "asdasd"
     */

    @SerializedName("Customer_Id")
    @Expose
    private long customerID;
    @SerializedName("Deliver_Id")
    @Expose
    private long deliverID;
    @SerializedName("Order_Id")
    @Expose
    private long orderID;
    @SerializedName("Start_Time")
    @Expose
    private String startTime;
    @SerializedName("Rest_Address")
    @Expose
    private String restAddress;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Rest_Name")
    @Expose
    private String restName;
    @SerializedName("Type_Count")
    @Expose
    private int typeCount;
    @SerializedName("Total")
    @Expose
    private int total;
    @SerializedName("Order_Status")
    @Expose
    private String orderStatus;
    @SerializedName("meals")
    @Expose
    private List<MealsBean> meals;
    @SerializedName("Other")
    @Expose
    private String other;

    private int value;

    public Order() {
    }

    public long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(long customerID) {
        this.customerID = customerID;
    }

    public long getDeliverID() {
        return deliverID;
    }

    public void setDeliverID(long deliverID) {
        this.deliverID = deliverID;
    }

    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTypeCount() {
        return typeCount;
    }

    public void setTypeCount(int typeCount) {
        this.typeCount = typeCount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public List<MealsBean> getMeals() {
        return meals;
    }

    public void setMeals(List<MealsBean> meals) {
        this.meals = meals;
    }

    public String getRestAddress() {
        return restAddress;
    }

    public void setRestAddress(String restAddress) {
        this.restAddress = restAddress;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public static class MealsBean {
        /**
         * Food_Id : 1236
         * Food_Name : df
         * Count : 5
         */

        @SerializedName("Food_Id")
        @Expose
        private int foodID;
        @SerializedName("Food_Name")
        @Expose
        private String foodName;
        @SerializedName("Count")
        @Expose
        private int count;

        public int getFoodID() {
            return foodID;
        }

        public void setFoodID(int foodID) {
            this.foodID = foodID;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }
    }
}

package order.model.javabean;

import java.util.List;

public class Order {
    /**
     * Order_Id : 12314
     * Customer_Id : 123
     * Customer_Id : 243
     * Start_Time : tfdyhj
     * Address : dslkvmds
     * Type_Count : 1
     * Total : 634
     * meals : [{"Food_Id":1236,"Count":5}]
     * Order_Status : vlcd;sx
     * Other : "asdasd"
     */

    private long Customer_Id;
    private long Deliver_Id;
    private long Order_Id;
    private String Start_Time;
    private String Address;
    private int Type_Count;
    private int Total;
    private String Order_Status;
    private List<MealsBean> meals;
    private String Other;

    public long getOrder_Id() {
        return Order_Id;
    }

    public void setOrder_Id(long Order_Id) {
        this.Order_Id = Order_Id;
    }

    public String getStart_Time() {
        return Start_Time;
    }

    public void setStart_Time(String Start_Time) {
        this.Start_Time = Start_Time;
    }

    public int getType_Count() {
        return Type_Count;
    }

    public void setType_Count(int type_Count) {
        Type_Count = type_Count;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

    public String getOrder_Status() {
        return Order_Status;
    }

    public void setOrder_Status(String Order_Status) {
        this.Order_Status = Order_Status;
    }

    public List<MealsBean> getMeals() {
        return meals;
    }

    public void setMeals(List<MealsBean> meals) {
        this.meals = meals;
    }

    public long getDeliver_Id() {
        return Deliver_Id;
    }

    public void setDeliver_Id(long deliver_Id) {
        Deliver_Id = deliver_Id;
    }

    public String getOther() {
        return Other;
    }

    public void setOther(String other) {
        Other = other;
    }

    public long getCustomer_Id() {
        return Customer_Id;
    }

    public void setCustomer_Id(long customer_Id) {
        Customer_Id = customer_Id;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public static class MealsBean {
        /**
         * Food_Id : 1236
         * Count : 5
         */

        private int Food_Id;
        private int Count;

        public int getFood_Id() {
            return Food_Id;
        }

        public void setFood_Id(int Food_Id) {
            this.Food_Id = Food_Id;
        }

        public int getCount() {
            return Count;
        }

        public void setCount(int Count) {
            this.Count = Count;
        }
    }
}

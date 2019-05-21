package order.model.javabean;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class Order {

    /**
     * Customer : {"User_Id":1,"Account":"a","User_Name":"你好","Address":"我家","Other":"gufjdk","Phone_Number":"02222"}
     * Deliver : {"User_Id":3,"Account":"d","User_Name":"Testd","Phone_Number":"111111"}
     * Order : {"Order_Id":2536,"Total":50,"Type_Count":2,"Meals":[{"Rest_Name":"阿MAN 早午餐","Rest_Address":"基隆市中正區中正路822號1樓","Food_Id":3,"Food_Name":"麥克雞塊","Cost":25,"Count":1},{"Rest_Name":"阿MAN 早午餐","Rest_Address":"基隆市中正區中正路822號1樓","Food_Id":4,"Food_Name":"蘿蔔糕","Cost":25,"Count":1}],"Start_Time":"2019-05-15 03:31:00","Order_Status":"WAIT","Casting_Prio":0}
     */

    @SerializedName("Customer")
    private CustomerBean customer;
    @SerializedName("Deliver")
    private DeliverBean deliver;
    @SerializedName("Order")
    private OrderBean order;

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }

    public DeliverBean getDeliver() {
        return deliver;
    }

    public void setDeliver(DeliverBean deliver) {
        this.deliver = deliver;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("customer", customer)
                .append("deliver", deliver)
                .append("order", order)
                .toString();
    }

    public static class CustomerBean {
        /**
         * User_Id : 1
         * Account : a
         * User_Name : 你好
         * Address : 我家
         * Other : gufjdk
         * Phone_Number : 02222
         */

        @SerializedName("User_Id")
        private Long userID;
        @SerializedName("Account")
        private String account;
        @SerializedName("User_Name")
        private String userName;
        @SerializedName("Address")
        private String address;
        @SerializedName("Other")
        private String other;
        @SerializedName("Phone_Number")
        private String phoneNumber;

        public Long getUserID() {
            return userID;
        }

        public void setUserID(Long userID) {
            this.userID = userID;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("userID", userID)
                    .append("account", account)
                    .append("userName", userName)
                    .append("address", address)
                    .append("other", other)
                    .append("phoneNumber", phoneNumber)
                    .toString();
        }
    }

    public static class DeliverBean {
        /**
         * User_Id : 3
         * Account : d
         * User_Name : Testd
         * Phone_Number : 111111
         */

        @SerializedName("User_Id")
        private Long userID;
        @SerializedName("Account")
        private String account;
        @SerializedName("User_Name")
        private String userName;
        @SerializedName("Phone_Number")
        private String phoneNumber;

        public Long getUserID() {
            return userID;
        }

        public void setUserID(Long userID) {
            this.userID = userID;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("userID", userID)
                    .append("account", account)
                    .append("userName", userName)
                    .append("phoneNumber", phoneNumber)
                    .toString();
        }
    }

    public static class OrderBean {
        /**
         * Order_Id : 2536
         * Total : 50
         * Type_Count : 2
         * Meals : [{"Rest_Name":"阿MAN 早午餐","Rest_Address":"基隆市中正區中正路822號1樓","Food_Id":3,"Food_Name":"麥克雞塊","Cost":25,"Count":1},{"Rest_Name":"阿MAN 早午餐","Rest_Address":"基隆市中正區中正路822號1樓","Food_Id":4,"Food_Name":"蘿蔔糕","Cost":25,"Count":1}]
         * Start_Time : 2019-05-15 03:31:00
         * Order_Status : WAIT
         * Casting_Prio : 0
         */

        @SerializedName("Order_Id")
        private Long orderID;
        @SerializedName("Total")
        private int total;
        @SerializedName("Type_Count")
        private int typeCount;
        @SerializedName("Start_Time")
        private String startTime;
        @SerializedName("Order_Status")
        private String orderStatus;
        @SerializedName("Casting_Prio")
        private int castingPrio;
        @SerializedName("Meals")
        private List<MealsBean> meals;

        public Long getOrderID() {
            return orderID;
        }

        public void setOrderID(Long orderID) {
            this.orderID = orderID;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTypeCount() {
            return typeCount;
        }

        public void setTypeCount(int typeCount) {
            this.typeCount = typeCount;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public int getCastingPrio() {
            return castingPrio;
        }

        public void setCastingPrio(int castingPrio) {
            this.castingPrio = castingPrio;
        }

        public List<MealsBean> getMeals() {
            return meals;
        }

        public void setMeals(List<MealsBean> meals) {
            this.meals = meals;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("orderID", orderID)
                    .append("total", total)
                    .append("typeCount", typeCount)
                    .append("startTime", startTime)
                    .append("orderStatus", orderStatus)
                    .append("castingPrio", castingPrio)
                    .append("meals", meals)
                    .toString();
        }

        public static class MealsBean {
            /**
             * Rest_Name : 阿MAN 早午餐
             * Rest_Address : 基隆市中正區中正路822號1樓
             * Food_Id : 3
             * Food_Name : 麥克雞塊
             * Cost : 25
             * Count : 1
             */

            @SerializedName("Rest_Name")
            private String restName;
            @SerializedName("Rest_Address")
            private String restAddress;
            @SerializedName("Food_Id")
            private Long foodID;
            @SerializedName("Food_Name")
            private String foodName;
            @SerializedName("Cost")
            private int cost;
            @SerializedName("Count")
            private int count;

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

            public Long getFoodID() {
                return foodID;
            }

            public void setFoodID(Long foodID) {
                this.foodID = foodID;
            }

            public String getFoodName() {
                return foodName;
            }

            public void setFoodName(String foodName) {
                this.foodName = foodName;
            }

            public int getCost() {
                return cost;
            }

            public void setCost(int cost) {
                this.cost = cost;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            @Override
            public String toString() {
                return new ToStringBuilder(this)
                        .append("restName", restName)
                        .append("restAddress", restAddress)
                        .append("foodID", foodID)
                        .append("foodName", foodName)
                        .append("cost", cost)
                        .append("count", count)
                        .toString();
            }
        }
    }
}

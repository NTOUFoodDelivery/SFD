package order.model.javabean;

public class OrderSetting {
    public static final int ORDERSTAGE = 5;
    public class OrderStatus {
        public static final String CANCEL = "CANCEL";
        public static final String WAIT = "WAIT";
        public static final String TIME_OUT = "TIME_OUT";
        public static final String DEALING = "DEALING";
        public static final String FINISH = "FINISH"; // 二個人按
        public static final String CONFIRMING = "CONFIRMING"; // 一個人按
        public static final String PUSHING = "PUSHING";
    }

}
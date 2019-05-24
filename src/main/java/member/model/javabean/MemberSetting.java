package member.model.javabean;

public class MemberSetting {
    public class UserStatus {
        public static final String OFFLINE = "OFFLINE";
        public static final String CUSTOMER = "CUSTOMER";
        public static final String DELIVER_OFF = "DELIVER_OFF";
        public static final String DELIVER_ON = "DELIVER_ON";
        public static final String DELIVER_BUSY = "Deliver_Busy";
        public static final String USER_BAN = "USER_BAN";
        public static final String ADMINISTRATOR = "ADMINISTRATOR";
        public static final String PUSHING = "PUSHING";
    }
    public class UserType {
        public static final String CUSTOMER = "Customer";
        public static final String CUSTOMER_AND_DELIVER = "Customer_and_Deliver";
        public static final String ADMINISTRATOR = "Administrator";
    }
    public class Command {
        public static final String OFFLINE = "OFFLINE";
        public static final String CUSTOMER = "CUSTOMER";
        public static final String DELIVER_OFF = "DELIVER_OFF";
        public static final String DELIVER_ON = "DELIVER_ON";
        public static final String DELIVER_BUSY = "DELIVER_BUSY";
        public static final String USER_BAN = "USER_BAN";
        public static final String ADMINISTRATOR = "ADMINISTRATOR";
        public static final String DELETE = "DELETE";
        public static final String PUSHING = "PUSHING";
    }
    public class Feedback {
        public static final String CREATE = "CREATE";
        public static final String REPLY = "REPLY";
        public static final String SHOW = "SHOW";
    }
}
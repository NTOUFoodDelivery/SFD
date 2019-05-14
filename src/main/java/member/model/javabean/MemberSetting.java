package member.model.javabean;

public class MemberSetting {
    public class UserStatus {
        public static final String OFFLINE = "Offline";
        public static final String CUSTOMER = "Customer";
        public static final String DELIVER_OFF = "Deliver_Off";
        public static final String DELIVER_ON = "Deliver_On";
        public static final String DELIVER_BUSY = "Deliver_Busy";
        public static final String USER_BAN = "User_Ban";
        public static final String ADMINISTRATOR = "ADMINISTRATOR";
        public static final String PUSHING = "PUSHING";
    }
    public class UserType {
        public static final String CUSTOMER = "Customer";
        public static final String CUSTOMER_AND_DELIVER = "Customer_and_Deliver";
        public static final String ADMINISTRATOR = "Administrator";
    }
    public class Command {
        public static final String OFFLINE = "Offline";
        public static final String CUSTOMER = "Customer";
        public static final String DELIVER_OFF = "Deliver_Off";
        public static final String DELIVER_ON = "Deliver_On";
        public static final String DELIVER_BUSY = "Deliver_Busy";
        public static final String USER_BAN = "User_Ban";
        public static final String ADMINISTRATOR = "ADMINISTRATOR";
        public static final String DELETE = "DELETE";
        public static final String PUSHING = "PUSHING";
    }
    public class Feedback {
        public static final String ADD = "Add";
        public static final String ADMINISTRATORGET = "AdministratorGet";
        public static final String REPLY = "Reply";
        public static final String CUSTOMERORDELIVERGET = "CustomerOrDeliverGet";
    }
}
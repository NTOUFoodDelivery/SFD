package order.model.javabean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestInfoRequest {
    /**
     * query : {"command":"add","what":"rest"}
     * result : [{"Rest_ID":123,"Rest_Name":"test1","Rest_Address":"test1","Description":"test1"},{"Rest_ID":456,"Rest_Name":"test2","Rest_Address":"test2","Description":"test2"}]
     */

    private QueryBean query;
    private List<ResultBean> result;

    public QueryBean getQuery() {
        return query;
    }

    public void setQuery(QueryBean query) {
        this.query = query;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class QueryBean {
        /**
         * command : add
         * what : rest
         */

        private String command;
        private String what;

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }

        public String getWhat() {
            return what;
        }

        public void setWhat(String what) {
            this.what = what;
        }
    }

    public static class ResultBean {
        /**
         * Rest_ID : 123
         * Rest_Name : test1
         * Rest_Address : test1
         * Description : test1
         * Rest_Photo : test1
         */

        @SerializedName("Rest_ID")
        private Long restID;
        @SerializedName("Rest_Name")
        private String restName;
        @SerializedName("Rest_Address")
        private String restAddress;
        @SerializedName("Description")
        private String description;
        @SerializedName("Rest_Photo")
        private String restPhoto;

        public Long getRestID() {
            return restID;
        }

        public void setRestID(Long restID) {
            this.restID = restID;
        }

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getRestPhoto() {
            return restPhoto;
        }

        public void setRestPhoto(String restPhoto) {
            this.restPhoto = restPhoto;
        }
    }
}

package tool.javabean;

import com.google.gson.JsonObject;

import java.util.List;

public class CommonRequest {
    /**
     * query : {"command":"add","what":"menu"}
     * result : [{}]
     */

    private QueryBean query;
    private List<Object> result;

    public QueryBean getQuery() {
        return query;
    }

    public void setQuery(QueryBean query) {
        this.query = query;
    }

    public List<Object> getResult() {
        return result;
    }

    public void setResult(List<Object> result) {
        this.result = result;
    }

    public static class QueryBean {
        /**
         * command : add
         * what : menu
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
}

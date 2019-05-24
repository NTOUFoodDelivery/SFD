package util.javabean;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class CommonRequest {

    /**
     * query : {"command":"add","what":"menu"}
     * result : [{}]
     */

    private QueryBean query;
    private List<?> result;

    public QueryBean getQuery() {
        return query;
    }

    public void setQuery(QueryBean query) {
        this.query = query;
    }

    public List<?> getResult() {
        return result;
    }

    public void setResult(List<?> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("query", query)
                .append("result", result)
                .toString();
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

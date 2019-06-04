package util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ResultSetToJson {

  /**
   * <p>ResultSet To JsonArray.</p>
   *
   * @param rs ResultSet
   */
  public static final JsonArray resultSetToJsonArray(ResultSet rs) {
    JsonObject element;
    JsonArray ja = new JsonArray();
    ResultSetMetaData rsmd;
    String columnName;
    String columnValue;
    try {
      rsmd = rs.getMetaData();
      while (rs.next()) {
        element = new JsonObject();
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
          columnName = rsmd.getColumnName(i + 1);
          columnValue = rs.getString(columnName);
          element.addProperty(columnName, columnValue);
        }
        ja.add(element);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ja;
  }

  /**
   * <p>resultSet To JsonObject.</p>
   *
   * @param rs ResultSet
   */
  public static final JsonObject resultSetToJsonObject(ResultSet rs) {
    JsonObject element;
    JsonArray ja = new JsonArray();
    JsonObject jo = new JsonObject();
    ResultSetMetaData rsmd;
    String columnName;
    String columnValue;
    try {
      rsmd = rs.getMetaData();
      while (rs.next()) {
        element = new JsonObject();
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
          columnName = rsmd.getColumnName(i + 1);
          //if (columnName.equals("Image")) {
          //  if (rs.getString(columnName) != null) {
          //    byte[] bytes = rs.getBytes("Image");
          //    columnValue = Base64.encodeBase64String(bytes);
          //  }
          //} else {
          columnValue = rs.getString(columnName);
          //}
          element.addProperty(columnName, columnValue);
        }
        ja.add(element);
      }
      jo.add("result", ja);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return jo;
  }

  public static final String resultSetToJsonString(ResultSet rs) {
    return resultSetToJsonObject(rs).toString();
  }
}
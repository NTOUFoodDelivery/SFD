package util.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class C3P0Util {

    private static ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource(); // 正式
//    private static ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource("BerSerKer"); // 本地端測試
//    private static ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource("Yaoder"); // 本地端測試
//    private static ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource("KevinSFD"); // 本地端測試
//    private static ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource("thomas205327"); // 本地端測試
//    private static ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource("wudishidove"); // 本地端測試

    /**
     * 從連線池獲得連接物件
     * @return   Connection
     */
    public static Connection getConnection(){
        try {
            return comboPooledDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 連接物件放回連線池
     * @param connection
     */
    public static void close(Connection connection){
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

  /**
   * 連接物件放回連線池
   *
   * @param connection
   * @param preparedStatement
   */
  public static void close(Connection connection, PreparedStatement preparedStatement){
    if(preparedStatement!=null){
      try {
        preparedStatement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    if(connection!=null){
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 連接物件放回連線池
   *
   * @param connection
   * @param preparedStatement
   * @param resultSet
   */
  public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet){
    if(resultSet!=null){
      try {
        resultSet.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    if(preparedStatement!=null){
      try {
        preparedStatement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    if(connection!=null){
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 連接物件放回連線池
   *
   * @param connection
   * @param preparedStatement
   * @param resultSet
   */
  public static void close(Connection connection, ResultSet resultSet , PreparedStatement... preparedStatements){
    if(resultSet!=null){
      try {
        resultSet.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    for(PreparedStatement preparedStatement : preparedStatements) {
      if (preparedStatement != null) {
        try {
          preparedStatement.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    if(connection!=null){
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 連接物件放回連線池
   *
   * @param preparedStatements
   */
  public static void close(PreparedStatement... preparedStatements){
    for(PreparedStatement preparedStatement : preparedStatements) {
      if (preparedStatement != null) {
        try {
          preparedStatement.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * 連接物件放回連線池
   *
   * @param resultSets
   */
  public static void close(ResultSet... resultSets){

    for(ResultSet resultSet : resultSets) {
      if (resultSet != null) {
        try {
          resultSet.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }
}



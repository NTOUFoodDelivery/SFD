package util.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class C3P0Util {

//    private static ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource(); // 正式
    private static ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource("BerSerKer"); // 本地端測試
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
}



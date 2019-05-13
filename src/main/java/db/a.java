package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


public class a
{
	private Connection con = null;  //Database objects
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private String insertdbSQL = "INSERT INTO member(User_Id, Password, User_Name, Email, Phone_number, Last_Address, User_Type, User_Status) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
	//?叫做佔位符
	private String selectSQL = "SELECT User_Name, User_Id FROM member WHERE Password LIKE ?";

    //ResultSetMetaData rsmd = rs.getMetaData(); //取得Query資料

  //  int numColumns = rsmd.getColumnCount();
	
	public a() {
		try { 
		      Class.forName("com.mysql.cj.jdbc.Driver"); 
			//Class.forName("com.mysql.jdbc.Driver"); 
		      //註冊driver 
		      con = DriverManager.getConnection( 
			//"jdbc:mysql://127.0.0.1:3306/sfd_db?"
		    "jdbc:mysql://b13568559a2078:c511925d@us-cdbr-iron-east-02.cleardb.net/heroku_9519aa67df829c7?"
			+ "serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF-8", 
			//"root","jaja121605");
			"b13568559a2078"," c511925d");
		      //取得connection

		//jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=Big5
		//localhost是主機名,test是database名
		//useUnicode=true&characterEncoding=Big5使用的編碼 
		      if(con != null && !con.isClosed()) {
	                System.out.println("資料庫連線測試成功！"); 
	                //con.close();
	          }
		    } 
		    catch(ClassNotFoundException e) 
		    { 
		      System.out.println("DriverClassNotFound :"+e.toString()); 
		    }//有可能會產生sqlexception 
		    catch(SQLException x) { 
		      System.out.println("Exception :"+x.toString()); 
		    } 
		
	}
	public void insertTable() {
		try {
			pst = con.prepareStatement(insertdbSQL);
			pst.setString(1, "c98798");
			pst.setString(2, "b00000");
			pst.setString(3, "蘇包");
			pst.setString(4, "bao@gmail.com");
			pst.setString(5, "0978716315");
			pst.setString(6, "smail taipei");
			pst.setString(7, "EAT");
			pst.setString(8, "fuck");
			pst.executeUpdate();
		}
		catch(SQLException e) 
	    { 
	      System.out.println("InsertDB Exception :" + e.toString()); 
	    } 
	    finally 
	    { 
	      //Close(); 
	    } 
	}
	public String searchTable(String username) {
		String id = null;
		String na = null;
		try {
			pst = con.prepareStatement(selectSQL);
			//pst.setString(1, "User_Name");
			//pst.setString(1, "member");
			//pst.setString(2, "User_Id");
			pst.setString(1, "b00000");
			rs = pst.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData(); //取得Query資料
			int numColumns = rsmd.getColumnCount();
			while(rs.next()) {
				na = rs.getString("User_Id");
				id = rs.getString("User_Name");
				System.out.println(na + "#" + id);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			Close();
		}
		return id;
	}
	public void Close() {
		try {
			if(pst != null) {
				pst.close();
				pst = null;
			}
			if(rs != null) {
				rs.close();
				rs = null;
			}
		}
		catch(SQLException e) 
	    { 
	      System.out.println("Close Exception :" + e.toString()); 
	    } 
	}
//	public static void main(String args[]) {
//		a f = new a();
//		f.searchTable("a00000");
//		//f.insertTable();
//	}
	
}
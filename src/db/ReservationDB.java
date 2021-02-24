
package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ReservationDB {
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	public PersonDB personDB; //person 테이블 불러올 때:db.personDB.insert(...)
	public BusDB busDB;
	public TicketDB ticketDB;
	public QueueDB queueDB;
	public ReservationDB() throws Exception {
		try {			
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("드라이버 로딩 성공");
			/**********************************************************/
			//String url = "jdbc:mysql://localhost:3306?serverTimezone=UTC";
			//String password = "1234";
			/*정선s configuration*****************************************/
			String url = "jdbc:mysql://localhost:3306/busReservationSystem?serverTimezone=UTC";
			String password = "password";
			/**********************************************************/
			con = DriverManager.getConnection(url,"root", password);
			System.out.println("데이터베이스 연결성공");
			stmt = con.createStatement();
			System.out.println("연결객체 획득 성공");
			/*테이블 쿼리 클래스들*/
			personDB = new PersonDB(this);
			busDB = new BusDB(this);
			ticketDB = new TicketDB(this);
			queueDB= new QueueDB(this);
		}
		catch (Exception e) {
			throw new Exception("데이터베이스 연결 오류");
		}		
	}
	void executeUpdate(String query) throws Exception {
		try {
			int rows = stmt.executeUpdate(query); 
			if (rows<= 0) throw new Exception();
		}
		catch(Exception e) {throw new Exception(e.getMessage());}
	}

}
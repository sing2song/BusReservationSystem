package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ReservationDB {
	Connection con = null;
	Statement stmt = null;
	public PersonDB personDB = new PersonDB(this); //person 테이블 불러올 때:db.personDB.insert(...)
	public BusDB busDB = new BusDB(this);
	public TicketDB ticketDB = new TicketDB(this);
	public QueueDB queueDB= new QueueDB(this);
	public ReservationDB() throws Exception {
		try {			
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 로딩 성공");
			String password = "password";
			String url = "jdbc:mysql://localhost:3306?serverTimezone=UTC";
			con = DriverManager.getConnection(url,"root", password);
			System.out.println("데이터베이스 연결성공");
			stmt = con.createStatement();
			stmt.executeQuery("use busReservationSystem");
			System.out.println("연결객체 획득 성공");
		} catch (Exception e) {
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

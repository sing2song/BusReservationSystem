import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ReservationDB {
	Connection con = null;
	Statement stmt = null;
	
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
	/*QUERIES*/
	public boolean insertPerson(String name, int balance ) {
		try {
			String query = String.format("");
			executeUpdate(query);
			return true;
		}
		catch(Exception e) {
			System.out.println("error" + e.getMessage());
			return false;}
	}
	public boolean existsPerson(int id) {
		return true;
	}
	public boolean updatePersonPlus(int id, int amount) {
		
		return false;
	}
	public boolean updatePersonMinus(int id, int amount) {
		
		return false;
	}
	
	/*BUS*/
	
	/*PERSON*/
	/*TICKET*/
	/*QUEUE*/
	private void executeUpdate(String query) throws Exception {
		try {
			int rows = stmt.executeUpdate(query); 
			if (rows<= 0) throw new Exception();
		}
		catch(Exception e) {throw new Exception(e.getMessage());}
	}
	
}

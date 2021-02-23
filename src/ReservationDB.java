import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ReservationDB {
	Connection con = null;
	Statement stmt = null;
	
	public ReservationDB() throws Exception {
		try {			
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 로딩 성공");
			//"jdbc:mysql://localhost:3306?serverTimezone=UTC","root","1234");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampleDB?serverTimezone=UTC","root","ssong");
			System.out.println("데이터베이스 연결성공");
			stmt = con.createStatement();
			System.out.println("연결객체 획득 성공");
		} catch (Exception e) {
			throw new Exception("데이터베이스 연결 오류");
		}		
	}	
}

package db;
import java.sql.Statement;

public class PersonDB {
	/*QUERIES*/
	public boolean insertPerson(String name, int balance ) {
		try {
			String query = String.format("");
			ReservationDB.executeUpdate(query);
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

}

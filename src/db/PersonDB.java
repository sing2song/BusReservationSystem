package db;
import java.sql.Statement;
import java.util.ArrayList;

import model.Person;
public class PersonDB {
	public ReservationDB db;
	public PersonDB(ReservationDB db) { this.db = db;}
	/*QUERIES***************************************************/
	/*CREATE*/
	public boolean insert(String name, int balance ) {
		try {
			String query = String.format("");
			db.executeUpdate(query);
			return true;
		}
		catch(Exception e) {
			System.out.println("error" + e.getMessage());
			return false;}
	}
	public Person select(int id) {
		return null;
	}
	/*READ*/
	public ArrayList<Person> selectAll() {
		return null;
	}
	/*UPDATE*/
	public boolean update(int id, int amount) {
		return false;
	}
	/**HELPER***************************************/
	public boolean exists(int id) {
		return true;
	}
}

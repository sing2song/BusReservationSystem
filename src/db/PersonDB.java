package db;
import java.sql.ResultSet;
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
			String query = String.format("insert into person(name,balance) values (%s,%d)",name,balance);
			db.executeUpdate(query);
			return true;
		}
		catch(Exception e) {
			System.out.println("[insert person 실패]" + e.getMessage());
			return false;
		}
	}
	public Person select(int id) {
		try {
			String query 
			= String.format("select * from person where personid= %d;", id);
			ResultSet rs = db.stmt.executeQuery(query);			

			rs.next();
			int accid = rs.getInt(1);
			String name = rs.getString(2);
			int balance = rs.getInt(3);

			Person person = new Person(accid,name,balance);

			return person;
		}
		catch(Exception ex) {
			return null;
		}
	}
	/*READ*/
	/* personId, name, balance */
	public ArrayList<Person> selectAll() {
		try {
			ArrayList<Person> arr = new ArrayList<Person>();
			String query = String.format("select * from person;");
			ResultSet rs = db.stmt.executeQuery(query);
			while(rs.next()) {
				int personid = rs.getInt(1);
				String name = rs.getString(2);
				int balance = rs.getInt(3);
				arr.add(new Person(personid, name, balance));
			}
			return arr;
		}
		catch(Exception ex) {
			return null;
		}
	}
	/*UPDATE*/
	public boolean update(int id, int amount) {
		try {	
			String query;
			if( exists(id) == true) 
				query = String.format("update person set balance= balance+ %d where accid=%d;",
						amount, id);
			else
				query = String.format("update person set balance= balance- %d where accid=%d;",
						amount, id);

			db.executeUpdate(query);

			return true;
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}	
	}
	/**HELPER***************************************/
	public boolean exists(int id) {
		try {
			String query = String.format("select * from person where personid= %d;", id);
			ResultSet rs = db.stmt.executeQuery(query);
			while(rs.next()) {
				return true;
			}
		}
		catch(Exception ex) {
			return false;
		}
		return false;
	}
}

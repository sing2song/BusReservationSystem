
package db;
import java.sql.ResultSet;
import java.sql.SQLException;
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
			String query = String.format("insert into person (name,balance) values (\"%s\",%d)",name,balance);
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
			Person person = rsToPerson(rs);
			
			return person;
		}
		catch(Exception ex) {
			System.out.println("[select person 실패]" + ex.getMessage());
			return null;
		}
	}
	/*READ*/
	public ArrayList<Person> selectAll() {
		try {
			ArrayList<Person> arr = new ArrayList<Person>();
			String query = String.format("select * from person;");
			ResultSet rs = db.stmt.executeQuery(query);
			while(rs.next()) arr.add(rsToPerson(rs));
			return arr;
		}
		catch(Exception ex) {
			System.out.println("[select all person 실패]" + ex.getMessage());
			return null;
		}
	}
	/*UPDATE*/
	public boolean update(int id, int amount) {
		try {	
			String query;
			query = String.format("update person set balance= balance+ %d where personid=%d and balance+%d>=0;",amount, id, amount);
			
			db.executeUpdate(query);
			
			return true;
		}
		catch(Exception ex) {
			System.out.println("[update person 실패]" + ex.getMessage());
			return false;
		}	
	}
	/**HELPER***************************************/
	public boolean exists(int id) {
		try {
			String query = String.format("select * from person where personid= %d;", id);
			ResultSet rs = db.stmt.executeQuery(query);
			rs.next();
			return true;
		}
		catch(Exception ex) {
			System.out.println("[exists person 실패]" + ex.getMessage());
			return false;
		}
	}
	public Person rsToPerson(ResultSet rs) throws Exception {
		int personid = rs.getInt("personid");
		String name = rs.getString("name");
		int balance = rs.getInt("balance");
		return new Person(personid, name, balance);
	}
}


package db;

import java.sql.Statement;
import java.util.ArrayList;

import model.Bus;

public class BusDB {
	/*QUERIES***************************************************/
	/*CREATE*/
	public boolean insert(String name, int price, String seats, int size) {
		try {
			String query = String.format("");
			ReservationDB.executeUpdate(query);
			return true;
		}
		catch(Exception e) {
			System.out.println("error" + e.getMessage());
			return false;}
	}
	public Bus select(int busid) {
		return null;
	}
	/*READ*/
	public ArrayList<Bus> selectAll() {
		return null;
	}
	/*UPDATE*/
	public boolean update(int busid, String seats) {
		return false;
	}
	/**HELPER***************************************/
	public boolean exists(int busid) {
		return true;
	}
	public boolean isFull(int busid) {
		//if(size>= count) return true; else false;
		return true;
	}
	public String getName(int busid) {
		//select name from bus where busid = busid;
		return null;
	}
	public int getPrice(int busid) {
		//select price from bus where busid = busid;
		return 0;
	}
}
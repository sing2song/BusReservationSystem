package db;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;


import model.Bus;

public class BusDB {
	public ReservationDB db; 
	public BusDB(ReservationDB db) { this.db = db;}
	/*QUERIES***************************************************/
	/*CREATE*/
	public boolean insert(String name, int price, String seats, int size) {
		try {
			String query = String.format("insert into bus (name, price, seats, size) values (%s, %d, %s, %d);”, name, price, seats, size");
			db.executeUpdate(query);
			return true;
		}
		catch(Exception e) {
			System.out.println("error" + e.getMessage());
			return false;}
	}
	public Bus select(int busnum) {
		try {
			String query = String.format("select * from bus where busid = ?;");
			PreparedStatement sment = db.con.prepareStatement(query);
			sment.setInt(1, busnum);
			ResultSet rs = sment.executeQuery();
			rs.next();
			int busid = rs.getInt(1);
			int price = rs.getInt(2);
			String name = rs.getString(3);
			int count = rs.getInt(4);
			int size = rs.getInt(5);
			String seats = rs.getString(6);
			
			sment.close();
			
			Bus bus = new Bus(busid, price, name, count, size, seats);
			return bus;
			
			
		}catch (Exception e) {
			System.out.println("error" + e.getMessage());
			return null;
		}
	
	}
	/*READ*/
	public ArrayList<Bus> selectAll() {
		try {
		String query = "select * from account;";
		PreparedStatement sment = db.con.prepareStatement(query);
		ArrayList<Bus> buslist = new ArrayList<Bus>();
		ResultSet rs = sment.executeQuery();
		while (rs.next()){
			int busid = rs.getInt(1);
			int price = rs.getInt(2);
			String name = rs.getString(3);
			int count = rs.getInt(4);
			int size = rs.getInt(5);
			String seats = rs.getString(6);
			buslist.add(new Bus(busid, price, name, count, size, seats));
		}
		sment.close();
		return buslist;
		}catch(Exception e) {
		return null;
		}
	}
	/*UPDATE*/
	public boolean updateSeats(int busid, String seats) {
		return false;
	}
	public boolean updateCount(int busid, int i) {
		return false;
	}
	/**HELPER***************************************/
	public boolean exists(int busid) {
		return true;
	}
	public String getName(int busid) {
		//select name from bus where busid = busid;
		return null;
	}
}


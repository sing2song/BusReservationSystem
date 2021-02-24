package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import model.Bus;

public class BusDB {
	public ReservationDB db;

	public BusDB(ReservationDB db) {
		this.db = db;
	}

	/* QUERIES ***************************************************/
	/* CREATE */
	public boolean insert(String name, int price, String seats, int size) {
		try {
			String query = String.format(
					"insert into bus (name, price, seats, size) values (\"%s\", %d, \"%s\", %d);", 
					name, price, seats, size);
			db.executeUpdate(query);
			return true;
		} catch (Exception e) {
			System.out.println("error" + e.getMessage());
			return false;
		}
	}

	public Bus select(int busnum) {
		try {
			String query = "select * from bus where busid = ?;";
			PreparedStatement sment = db.con.prepareStatement(query);
			sment.setInt(1, busnum);
			ResultSet rs = sment.executeQuery();
			rs.next();
			Bus bus = rsToBus(rs);

			sment.close();

			return bus;
		} catch (Exception e) {
			System.out.println("[error select bus]: " + e.getMessage());
			return null;
		}

	}

	/* READ */
	public ArrayList<Bus> selectAll() {
		try {
			String query = "select * from bus;";
			PreparedStatement sment = db.con.prepareStatement(query);
			ArrayList<Bus> buslist = new ArrayList<Bus>();
			ResultSet rs = sment.executeQuery();
			while (rs.next()) buslist.add(rsToBus(rs));
			sment.close();
			return buslist;
		} catch (Exception e) {
			System.out.println("[error select all bus] "+ e.getMessage());
			return null;
		}
	}

	/* UPDATE */
	public boolean updateSeats(int busid, String seats) {
		try {
			String query;
			if (exists(busid) == true) {
				query = String.format("update bus set seats= \"%s\" where busid=%d;", seats, busid);
				db.executeUpdate(query);
			}
			return true;
		} catch (Exception e) {
			System.out.println("[error update seats bus] "+ e.getMessage());
			return false;
		}
	}

	public boolean updateCount(int busid, int i) {
		try {
			String query;
			if (exists(busid) == true) {
				query = String.format("update bus set count=count+%d where busid=%d;", i, busid);
				db.executeUpdate(query);
			}
			return true;
		} catch (Exception e) {
			System.out.println("[error update count bus] "+ e.getMessage());
			return false;
		}
	}

	/** HELPER ***************************************/
	public boolean exists(int busid) {
		try {
			String query = String.format("select * from bus where busid= %d;", busid);
			ResultSet rs = db.stmt.executeQuery(query);
			rs.next();
			return true;
		}
		catch(Exception ex) {
			System.out.println("[error busid exists from bus]: " +ex.getMessage());
			return false;
		}
	}
	

	public String getName(int busid) {
		try {
			String query = String.format("select name from bus where busid = %d;", busid);
			ResultSet rs = db.stmt.executeQuery(query);
			rs.next();
			return rs.getString("name");
		}
		catch(Exception ex) {
			System.out.println("[error get name from bus]: " +ex.getMessage());
			return null;
		}
	}
	public Bus rsToBus(ResultSet rs) throws Exception {
		int busid = rs.getInt("busid");
		int price = rs.getInt("price");
		String name = rs.getString("name");
		int count = rs.getInt("count");
		int size = rs.getInt("size");
		String seats = rs.getString("seats");
		return new Bus(busid, price, name, count, size, seats);
	}
}

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

	public BusDB(ReservationDB db) {
		this.db = db;
	}

	/* QUERIES ***************************************************/
	/* CREATE */
	public boolean insert(String name, int price, String seats, int size) {
		try {
			String query = String.format(
					"insert into bus (name, price, seats, size) values (%s, %d, %s, %d);”, name, price, seats, size");
			db.executeUpdate(query);
			return true;
		} catch (Exception e) {
			System.out.println("error" + e.getMessage());
			return false;
		}
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

		} catch (Exception e) {
			System.out.println("error" + e.getMessage());
			return null;
		}

	}

	/* READ */
	public ArrayList<Bus> selectAll() {
		try {
			String query = "select * from account;";
			PreparedStatement sment = db.con.prepareStatement(query);
			ArrayList<Bus> buslist = new ArrayList<Bus>();
			ResultSet rs = sment.executeQuery();
			while (rs.next()) {
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
		} catch (Exception e) {
			return null;
		}
	}

	/* UPDATE */
	public boolean updateSeats(int busid, String seats) {
		try {
			String qurey;
			if (exists(busid) == true) {
				qurey = String.format("update bus set seats= %c where busid=%d;", seats, busid);

				db.executeUpdate(qurey);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean updateCount(int busid, int i) {
		try {
			String qurey;
			if (exists(busid) == true) {
				qurey = String.format("update bus set count= %d where busid=%d;", i, busid);

				db.executeUpdate(qurey);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/** HELPER ***************************************/
	public boolean exists(int busid) {
		try {
			String query = String.format("select * from bus where busid= %d;", busid);
			ResultSet rs = db.stmt.executeQuery(query);
			while(rs.next()) {
				return true;
			}
		}
		catch(Exception ex) {
			System.out.println("[error busid exists from bus]: " +ex.getMessage());
			return false;
		}
		return false;
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
}

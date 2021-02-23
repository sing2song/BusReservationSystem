package db;

import java.sql.Statement;
import java.util.ArrayList;

public class QueueDB {
	/*QUERIES***************************************************/
	/*CREATE*/
	public boolean insert(int personid, int busid) {
		try {
			String query = String.format("");
			ReservationDB.executeUpdate(query);
			return true;
		}
		catch(Exception e) {
			System.out.println("error" + e.getMessage());
			return false;}
	}
	/*대기중인 버스*/
	public ArrayList<Object[]> selectByPerson(int personid) {
		//1. ticket table에서 버스 아이디 가져오
		ArrayList<Object[]> res = new ArrayList<Object[]>();
		/*
		ResultSet rs = ReservationDB.stmt.executeQuery(query);
		while(rs.next()) {
			Integer queueid = rs.getInt("queueid");
			Integer busid = rs.getInt("busid");
			String name = ReservationDB.busDB.getName(busid);
			Integer seat = rs.getInt("seat");
			Object[] array = {ticketid, busid, name, seat};
			res.add(array);
		}
		*/
		return res;
	}
	/*버스의 첫번째 대기자 */
	public int[] selectByBus(int personid) {
		int[] res = new int[2];
		try {
			/*
			ResultSet rs = ReservationDB.stmt.executeQuery(query);
			rs.next()
			Integer personid = rs.getInt("personid");
			대기자가 없다면 exception			
			*/
			return res;
		}
		catch(Exception e) {	 
			res[0] = -1;
			return res;
		}
	}
	/*DELETE*/
	public boolean delete(int queueid) {
		return false;
	}
	/**HELPER***************************************/
}

package db;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class QueueDB {
	public ReservationDB db;
	public QueueDB(ReservationDB db) { this.db = db;}
	/*QUERIES***************************************************/
	/*CREATE*/
	public boolean insert(int personid, int busid) {
		try {
			String query = String.format("insert into queue (personid, busid, date) values  (%d, %d, now());", personid, busid);
			db.executeUpdate(query);
			return true;
		}
		catch(Exception e) {
			System.out.println("[insert into queue error]" + e.getMessage());
			return false;}
	}

	/*대기중인 버스
	 * Object[] : { queue id, busid, busname}
	 * */
	public ArrayList<Object[]> selectByPerson(int personid) {
		//1. ticket table에서 버스 아이디 가져오
		ArrayList<Object[]> res = new ArrayList<Object[]>();
		try {
			String query = String.format(
					"select q.queueid, q.busid, b.name from queue q inner join bus b on q.busid = b.busid where personid = %d;", personid);
			ResultSet rs = db.stmt.executeQuery(query);
			while(rs.next()) {
				Integer queueid = rs.getInt("queueid");
				Integer busid = rs.getInt("busid");
				String name = rs.getString("name");
				Object[] array = {queueid, busid, name};
				res.add(array);
			}
			return res;
		}
		catch(Exception e) {	 
			System.out.println("[select by person from queue error]" + e.getMessage());
			return res;
		}
	}
	/*버스의 첫번째 대기자 
	 * int[] : { queueid, personid }
	 * */
	public int[] selectByBus(int busid) {
		int[] res = new int[2];
		try {
			String query = String.format("select queueid, personid from queue where busid = %d order by queueid;", busid);
			ResultSet rs = db.stmt.executeQuery(query);
			rs.next();			//대기자가 없다면 exception			
			Integer queueid = rs.getInt("queueid");
			Integer personid = rs.getInt("personid");
			res[0] = queueid;
			res[1] = personid;
			return res;
		}
		catch(Exception e) {	 
			res[0] = -1;
			return res;
		}
	}
	/*DELETE*/
	public boolean delete(int queueid) {
		try {
			String query = String.format("delete from queue where queueid = %d;", queueid);
			db.executeUpdate(query);
			return true;
		}
		catch(Exception e) {
			System.out.println("[delete from queue error]" + e.getMessage());
			return false;}
	}
}

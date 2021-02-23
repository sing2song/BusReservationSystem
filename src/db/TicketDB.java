package db;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class TicketDB {
	/*QUERIES***************************************************/
	/*CREATE*/
	/*예약하기*/
	public boolean insert(int personid, int busid) {
		try {
			//1. insert query
			String query = String.format("");
			ReservationDB.executeUpdate(query);
			//2. 잔액 출금 
			int amount = ReservationDB.busDB.getPrice(busid);
			ReservationDB.personDB.update(personid, - amount);
			return true;
		}
		catch(Exception e) {
			System.out.println("error" + e.getMessage());
			return false;}
	}
	/*예약된 티켓들*/
	public ArrayList<Object[]> select(int personid) {
		//1. ticket table에서 버스 아이디 가져오
		ArrayList<Object[]> res = new ArrayList<Object[]>();
		/**QUERY**/
		/*
		ResultSet rs = ReservationDB.stmt.executeQuery(query);
		while(rs.next()) {
			Integer ticketid = rs.getInt("ticketid");
			Integer busid = rs.getInt("busid");
			String name = ReservationDB.busDB.getName(busid);
			Integer seat = rs.getInt("seat");
			Object[] array = {ticketid, busid, name, seat};//티켓 정보들 배열로 저장해 반환 
			res.add(array);
		}
		*/
		return res;
	}
	/*DELETE*/
	public boolean delete(int ticketid) {
		//1. ticket에서 seat, busid, personid 가져오기
		int seat = this.getSeat(ticketid);
		int busid = this.getBusid(ticketid);
		int personid = this.getPersonid(ticketid);
		//2. queue에서 첫번째 대기자 nextpersonid 가져오기
		int[] temp = ReservationDB.queueDB.selectByBus(busid);
		int nextpersonid = temp[0], queueid = temp[1];
		if(nextpersonid!= -1) {//대기자 있
		//3. 대기자가 있다면 -> update ticket set personid = nextpersonid where ticketid=ticketid
						//-> delete from queue where queueid = queueid;
		}
		else {//대기자 없 
		//3. 대기자가 없다면 -> delete from ticket where ticketid = ticketid
			   			//-> update bus where busid = busid
			
		}
		//4.잔액
		int amount = ReservationDB.busDB.getPrice(busid);
		ReservationDB.personDB.update(personid, amount);
		
		return false;
	}
	/**HELPER***************************************/
	public int getSeat(int ticketid) {
		return 0;
	}
	public int getPersonid(int ticketid) {
		return 0;
	}
	public int getBusid(int ticketid) {
		return 0;
	}
}

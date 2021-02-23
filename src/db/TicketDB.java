package db;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import model.Bus;

public class TicketDB {
	/*QUERIES***************************************************/
	/*CREATE*/
	/*예약하기
	 * 1. select person (personid) --> Person 객
	 * 2. select all buses --> 모든 Bus객체
	 * 2. select bus (busid) --> Bus 객체
	 * 3. select seat --> (personid, Bus, seat)
	 * */
	public boolean insert(int personid, Bus bus, int seat) {
		try {
			String seats, newSeats;
			//1. insert query
			String query = String.format("");
			ReservationDB.executeUpdate(query);
			
			//2. 잔액 출금 
			ReservationDB.personDB.update(personid, - bus.getPrice());
			
			//3. bus update seats
			seats = bus.getSeats();
			if(seats.charAt(seat) == 0) {
				StringBuilder sb = new StringBuilder(seats);
				sb.replace(seat, seat+1, "1");
				newSeats = sb.toString();
				ReservationDB.busDB.updateSeats(bus.getBusId(), newSeats);
			}
			else throw new Exception("이미 예약된 자리입니다.");
			
			//4. bus update count
			ReservationDB.busDB.updateCount(bus.getBusId(), 1);
			return true;
		}
		catch(Exception e) {
			System.out.println("error" + e.getMessage());
			return false;}
	}
	/*예약된 티켓들
	 * 1. select(personid) --> Person
	 * 2. select(personid) --> ticketid, busid, bus name, seat #
	 * */
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
	/*DELETE
	 * select(personid) --> Person
	 * select(personid) --> ticketid, busid, bus name, seat #
	 * delete(ticketid) 
	 * */
	public boolean delete(int ticketid) {
		Bus bus; 
		int seat, busid, personid, nextpersonid, queueid, amount;
		String seats, newSeats;
		//1. ticket에서 seat, busid, personid 가져오기
		seat = this.getSeat(ticketid);
		busid = this.getBusid(ticketid);
		personid = this.getPersonid(ticketid);
		bus = ReservationDB.busDB.select(busid);
		//2. delete from ticket
		
		//3. bus update
		seats = bus.getSeats();
		StringBuilder sb = new StringBuilder(seats);
		sb.replace(seat, seat+1, "0");
		newSeats = sb.toString();
		ReservationDB.busDB.updateSeats(busid, newSeats);
		
		//4. queue에서 첫번째 대기자 nextpersonid 가져오기
		int[] temp = ReservationDB.queueDB.selectByBus(busid);
		nextpersonid = temp[0];
		queueid = temp[1];//대기
		//4. 대기자 있으면  
		if(nextpersonid!= -1) {
			ReservationDB.queueDB.delete(queueid);
			this.insert(nextpersonid, bus, seat);
		}
		else ReservationDB.busDB.updateCount(busid, -1);
		//5.잔액
		amount = bus.getPrice();
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

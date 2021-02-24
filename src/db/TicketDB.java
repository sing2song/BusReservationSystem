package db;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import model.Bus;

public class TicketDB {
	public ReservationDB db;
	public TicketDB(ReservationDB db) { this.db = db;}
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
			//1. bus update seats
			seats = bus.getSeats();//원래 자리 표시 
			if(seats.charAt(seat) == 0) {
				StringBuilder sb = new StringBuilder(seats);
				sb.replace(seat, seat+1, "1");//에약됨 표
				newSeats = sb.toString();
				db.busDB.updateSeats(bus.getBusId(), newSeats);//엎뎃 
			}
			else throw new Exception("이미 예약된 자리입니다.");
			//2. 잔액 출금 
			db.personDB.update(personid, - bus.getPrice());//- price 잔액에서 빼기 
			
			//3. insert query
			String query = String.format("insert into ticket (personid, busid, date, seat) values (%d,%d,now(), %d );",
					personid, bus.getBusId(), seat);
			db.executeUpdate(query);
			
			//4. bus update count
			db.busDB.updateCount(bus.getBusId(), 1);
			return true;
		}
		catch(Exception e) {
			System.out.println("[error insert new ticket]" + e.getMessage());
			return false;
		}
	}
	/*예약된 티켓들
	 * 1. select(personid) --> Person
	 * 2. select(personid) --> ticketid, busid, bus name, seat #
	 * Object[] : ticketid, busid, name, seat
	 * */
	public ArrayList<Object[]> select(int personid) {
		//1. ticket table에서 버스 아이디 가져오
		ArrayList<Object[]> res = new ArrayList<Object[]>();
		try {
			String query = String.format("select ticketid, busid, seat from ticket where personid = %d;", personid);
			ResultSet rs = db.stmt.executeQuery(query);
			while(rs.next()) {
				Integer ticketid = rs.getInt("ticketid");
				Integer busid = rs.getInt("busid");
				String name = db.busDB.getName(busid);
				Integer seat = rs.getInt("seat");
				Object[] array = {ticketid, busid, name, seat};//티켓 정보들 배열로 저장해 반환 
				res.add(array);
			}
			return res;
			
		}
		catch(Exception e) {
			System.out.println("[error select ticket]" + e.getMessage());
			return null;
		}
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
		try {
			//1. ticket에서 seat, busid, personid 가져오기
			String query = String.format("select seat, busid, personid from ticket where ticketid = %d;", ticketid);
			ResultSet rs = db.stmt.executeQuery(query);
			rs.next();
			busid = rs.getInt("busid");//버스 id
			seat = rs.getInt("seat");//그 좌석 
			personid = rs.getInt("personid");//예약 취소하는 사
			bus = db.busDB.select(busid);//버스 객체 
			
			//2. delete from ticket
			query = String.format("delete from ticket where ticketid = %d;", ticketid);
			db.executeUpdate(query);
			
			//3. bus update
			seats = bus.getSeats();
			StringBuilder sb = new StringBuilder(seats);
			sb.replace(seat, seat+1, "0");
			newSeats = sb.toString();
			db.busDB.updateSeats(busid, newSeats);

			//4. queue에서 첫번째 대기자 nextpersonid 가져오기
			int[] temp = db.queueDB.selectByBus(busid);
			queueid = temp[0];
			nextpersonid = temp[1];
			
			//4. 대기자 있으면  
			if(queueid!= -1) {
				db.queueDB.delete(queueid);//대기자에서 삭제 
				insert(nextpersonid, bus, seat);//티켓 새로 만들
			}
			else db.busDB.updateCount(busid, -1);//버스 업뎃 

			//5.잔액
			db.personDB.update(personid, bus.getPrice());//+ price 잔액에서 더하기 
			return true;
		}
		catch(Exception e) {
			System.out.println("[error delete ticket] "+ e.getMessage());
			return false;
		}
	}
}

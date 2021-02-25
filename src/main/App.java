package main;
import java.io.File;
import java.util.Scanner;

public class App {

	private ReservationSystem rs;
	public App() throws Exception {
		rs = new ReservationSystem();
		//init();
	}
	 
	public void init() {
		try {
			Scanner sc = new Scanner(new File("buses.txt"));
			while(sc.hasNextLine()) {
				//name, price, size
				String[] arr = rs.read(sc).split(" ");
				rs.insertBus(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2]) );
			}
		
			sc = new Scanner(new File("people.txt"));
			while(sc.hasNextLine()) {
				//name, balance
				String[] arr = rs.read(sc).split(" ");
				rs.insertPerson(arr[0], Integer.parseInt(arr[1]));
			}
		}
		catch(Exception e) {
			System.out.println("file io error: " + e.getMessage());
		}
		
		
	}
	
	public void run() {
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("===================================================================");
			System.out.println("[버스 예약 시스템]");
			System.out.println("1.버스추가 2.사람추가 3.예약 4.조회 5.취소 6.대기 취소 7.종료");
			System.out.print(">>");
			int menu = Integer.parseInt(rs.read(sc));
			System.out.println("===================================================================");
			switch(menu){
			case 1:	rs.insertBus(sc);break;
				
			case 2: rs.insertPerson(sc);break;
				
			case 3: rs.makeReservation(sc);break;

			case 4: rs.seeDetails(sc); break;
			
			case 5: rs.cancelReservation(sc); break;
			
			case 6: rs.cancelBusQueue(sc); break;
			case 7: 
				System.out.println("시스템을 종료합니다!");
				System.exit(0);
			default:
				System.out.println("잘못 입력하셨습니다!");
				break;
			}
		}
	}
}

import java.util.Scanner;

public class App {

	private ReservationSystem rs;
	public App() {
		rs = new ReservationSystem();
		init();
	}
	
	public void init() {
		
	}
	
	public void run() {
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.println("[버스 예약 시스템]");
			System.out.println("1.버스추가\t 2.사람추가\t 3.예약\t 4.조회\t 5.종료");
			System.out.print(">>");
			int menu = sc.nextInt();
			
			switch(menu){
			case 1:	rs.insertBus(sc);break;
				
			case 2: rs.insertPerson(sc);break;
				
			case 3: rs.makeReservation(sc);break;
				
			case 4: rs.seeDetails(sc);
				break;
			case 5: 
				System.out.println("시스템을 종료합니다!");
				System.exit(0);
			}
		}
	}
}
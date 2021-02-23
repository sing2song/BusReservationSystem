package db;

import java.sql.Statement;

public class TicketDB {
	Statement stmt;
	public TicketDB(Statement stmt) {
		this.stmt = stmt;
	}
}

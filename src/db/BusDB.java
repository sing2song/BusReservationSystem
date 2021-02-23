package db;

import java.sql.Statement;

public class BusDB {
	Statement stmt;
	public BusDB(Statement stmt) {
		this.stmt = stmt;
	}
}

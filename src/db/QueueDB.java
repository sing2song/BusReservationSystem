package db;

import java.sql.Statement;

public class QueueDB {
	Statement stmt;
	public QueueDB(Statement stmt) {
		this.stmt = stmt;
	}

}

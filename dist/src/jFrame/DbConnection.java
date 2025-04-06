package jFrame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbConnection {

    // SQLite Connection URL
    private static final String DB_URL = "jdbc:sqlite:database/library_ms.db";

    public static void main(String[] args) {
        initializeDatabase();
    }

    // Initialize the database and create tables
    public static void initializeDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {

            // Enable foreign key support in SQLite
            stmt.execute("PRAGMA foreign_keys = ON;");

            // Users Table
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users ("
                    + "user_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "name TEXT NOT NULL, "
                    + "email TEXT UNIQUE NOT NULL, "
                    + "contact TEXT NOT NULL, "
                    + "password_hash TEXT NOT NULL)";

            // Books Table
            String createBooksTable = "CREATE TABLE IF NOT EXISTS books ("
                    + "isbn TEXT PRIMARY KEY, "
                    + "book_name TEXT NOT NULL, "
                    + "author TEXT NOT NULL, "
                    + "publication TEXT NOT NULL, "
                    + "total_pages INTEGER NOT NULL, "
                    + "price REAL NOT NULL, "
                    + "quantity INTEGER NOT NULL) ";

            // Students Table
            String createStudentsTable = "CREATE TABLE IF NOT EXISTS students ("
                    + "student_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "student_name TEXT NOT NULL, "
                    + "phone_number TEXT NOT NULL, "
                    + "aadhaar TEXT NOT NULL UNIQUE, "
                    + "enroll_date TEXT NOT NULL, "
                    + "expiry_date TEXT NOT NULL, "
                    + "address TEXT, "
                    + "issue_book_no INTEGER DEFAULT 0)";
                   

            // Book Issue Table
            String createBookIssuesTable = "CREATE TABLE IF NOT EXISTS book_issues ("
                    + "issue_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "accession_no TEXT NOT NULL, "
                    + "isbn TEXT NOT NULL, "
                    + "student_id INTEGER NOT NULL, "
                    + "issue_date TEXT NOT NULL, "
                    + "due_date TEXT NOT NULL, "
                    + "return_date TEXT DEFAULT NULL, "
                    + "status TEXT CHECK(status IN ('issued', 'returned', 'pending')) DEFAULT 'pending', "
                    + "fine REAL DEFAULT 0.00, "
                    + "FOREIGN KEY (isbn) REFERENCES books(isbn), "
                    + "FOREIGN KEY (student_id) REFERENCES students(student_id))";

            // Execute all table creation queries
            stmt.execute(createUsersTable);
            stmt.execute(createBooksTable);
            stmt.execute(createStudentsTable);
            stmt.execute(createBookIssuesTable);

            System.out.println("All tables created successfully in SQLite.");

        } catch (Exception e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
    }

    // Get database connection
    public static Connection getConnection() throws Exception {
        Connection conn = DriverManager.getConnection(DB_URL);
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA busy_timeout = 5000;"); // Wait for 5 seconds if the database is locked
        }
        return conn;
    }

}

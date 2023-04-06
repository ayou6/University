package universittProj;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JDBC {

	public static void createUniversityTable() {
		String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=Universities;" + "encrypt=true;"
				+ "trustServerCertificate=true";
		String user = "sa";
		String pass = "root";

		Connection con = null;
		try {

			Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			DriverManager.registerDriver(driver);

			con = DriverManager.getConnection(url, user, pass);

			String tableName = "University_Details";
			String sql = "IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'University_Details')\r\n" + "BEGIN\r\n"
					+ "CREATE TABLE " + tableName + " " + "(state_province varchar(MAX), " + "domains varchar(MAX), "
					+ "web_pages varchar(MAX), " + "name varchar(MAX), " + "alpha_two_code varchar(MAX), "
					+ "country varchar(MAX) );\n" + "END\r\n" + "insert into " + tableName
					+ "  (state_province, domains, web_pages, name, alpha_two_code, country)\n"
					+ "values (?,?,?,?,?,?);";

			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, APIConsumer.uniList.get(APIConsumer.uniList.size() - 1).state_province);
			statement.setString(2, APIConsumer.uniList.get(APIConsumer.uniList.size() - 1).domains[0]);
			statement.setString(3, APIConsumer.uniList.get(APIConsumer.uniList.size() - 1).web_pages[0]);
			statement.setString(4, APIConsumer.uniList.get(APIConsumer.uniList.size() - 1).name);
			statement.setString(5, APIConsumer.uniList.get(APIConsumer.uniList.size() - 1).alpha_two_code);
			statement.setString(6, APIConsumer.uniList.get(APIConsumer.uniList.size() - 1).country);
			statement.executeUpdate();

			// Close the PreparedStatement object
			statement.close();
			con.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}

	public static boolean dropUniversityTable() {
		String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=Universities;" + "encrypt=true;"
				+ "trustServerCertificate=true";
		String user = "sa";
		String pass = "root";
		String tableName = "University_Details";
		String sql = "DROP TABLE " + tableName;

		Connection conn = null;
		try {
			Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			DriverManager.registerDriver(driver);
			conn = DriverManager.getConnection(url, user, pass);
			Statement st = conn.createStatement();

			// Check if the table exists
			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, tableName, null);
			if (!tables.next()) {
				System.out.println("Table " + tableName + " does not exist in the database.");
				return false;
			}

			// Drop the table
			int m = st.executeUpdate(sql);
			if (m >= 1) {
				System.out.println("Dropped table " + tableName + " successfully!!...");
				return true;
			}
			conn.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}

		return false;
	}

	public static void fetchFromDB() {
		String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=Universities;" + "encrypt=true;"
				+ "trustServerCertificate=true";
		String user = "sa";
		String pass = "root";

		Connection conn = null;
		try {
			Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			DriverManager.registerDriver(driver);
			conn = DriverManager.getConnection(url, user, pass);
			Statement st = conn.createStatement();

			// Check if the table exists
			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "University_Details", null);
			if (!tables.next()) {
				System.out.println("Table University_Details does not exist in the database.");
				return;
			}

			// Fetch data from the table
			ResultSet rs = st.executeQuery("SELECT * FROM University_Details");
			while (rs.next()) {
				String state_province = rs.getString("state_province");
				String[] domains = rs.getString("domains").split(",");
				String[] web_pages = rs.getString("web_pages").split(",");
				String name = rs.getString("name");
				String alpha_two_code = rs.getString("alpha_two_code");
				String country = rs.getString("country");

				// Do something with the data, such as print it out
				System.out.println("State/Province: " + state_province);
				System.out.println("Domains: " + Arrays.toString(domains));
				System.out.println("Web pages: " + Arrays.toString(web_pages));
				System.out.println("Name: " + name);
				System.out.println("Alpha two code: " + alpha_two_code);
				System.out.println("Country: " + country);
				System.out.println("--------------------------------------------------------------------------");
			}

			// Close the ResultSet and Statement objects
			rs.close();
			st.close();
			conn.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}		
		public static void backUp() {
			String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=Universities;" + "encrypt=true;"
					+ "trustServerCertificate=true";
			String user = "sa";
			String pass = "root";

			Connection con = null;
			try {

				Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
				DriverManager.registerDriver(driver);

				con = DriverManager.getConnection(url, user, pass);

				String sql = "BACKUP DATABASE Universities \r\n"
						+ "TO DISK = 'C:\\Users\\Lenovo\\eclipse-workspace\\universittProj\\Universities.BAK'\r\n"
						+ "WITH DESCRIPTION = 'Full backup for Universities'";

				PreparedStatement statement = con.prepareStatement(sql);

				statement.executeUpdate();
				statement.close();
				con.close();
			} catch (Exception ex) {
				System.err.println(ex);
			}
		}
		
		public static List<University> searchIn(String country) {
		    String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=Universities;" + "encrypt=true;"
		            + "trustServerCertificate=true";
		    String user = "sa";
		    String pass = "root";

		    List<University> universities = new ArrayList<>();

		    try (Connection con = DriverManager.getConnection(url, user, pass)) {
		        String sql = "SELECT * FROM University_Details WHERE country = ?";
		        PreparedStatement statement = con.prepareStatement(sql);
		        statement.setString(1, country);
		        ResultSet resultSet = statement.executeQuery();

		        while (resultSet.next()) {
		            String state_province = resultSet.getString("state_province");
		            String[] domains = resultSet.getString("domains").split(",");
		            String[] web_pages = resultSet.getString("web_pages").split(",");
		            String name = resultSet.getString("name");
		            String alpha_two_code = resultSet.getString("alpha_two_code");
		            String countryName = resultSet.getString("country");

		            universities.add(new University());
		        }

		        // Close the ResultSet, PreparedStatement, and Connection objects
		        resultSet.close();
		        statement.close();
		        con.close();
		    } catch (Exception ex) {
		        System.err.println(ex);
		    }

		    return universities;
		}

		
		

}

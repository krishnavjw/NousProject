package GenericMethods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.testng.Reporter;


public class DataBase_JDBC 
{

	public static String executeSQLQuery(String sqlQuery) {


		String resultValue = "";
		ResultSet rs;

		//To connect with QA Database
		String connectionUrl=Generic_Class.getPropertyValue("DB_Connection_Url_aut");
		//String connectionUrl=Generic_Class.getPropertyValue("DB_Connection_Url_QA");


		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {

			//Connection conn = DriverManager.getConnection("jdbc:sqlserver://HOSP_SQL1.company.com;user=name;password=abcdefg;database=Test");

			Connection connection = DriverManager.getConnection(connectionUrl);
			if(connection!=null) {
				Reporter.log("Connected to the database...",true);
			}else {
				Reporter.log("Database connection failed ",true);
			}
			Statement stmt = connection.createStatement();
			rs=stmt.executeQuery(sqlQuery);

			try {
				while(rs.next()){
					resultValue = rs.getString(1).toString();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			connection.close();

		}catch(Exception sqlEx) {

		}
		return resultValue;
	}


	public static ArrayList<String> executeSQLQuery_List(String sqlQuery) {


		ArrayList<String> resultValue = new ArrayList<String>();
		ResultSet resultSet;

		//To connect with QA Database
		//To connect with QA Database
		String connectionUrl=Generic_Class.getPropertyValue("DB_Connection_Url_aut");
		//String connectionUrl=Generic_Class.getPropertyValue("DB_Connection_Url_QA");

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection connection = DriverManager.getConnection(connectionUrl);
			if(connection!=null) {
				Reporter.log("Connected to the database...",true);
			}else {
				Reporter.log("Database connection failed ",true);
			}
			Statement statement = connection.createStatement();
			resultSet=statement.executeQuery(sqlQuery);
			String reqValue;
			try {


				while(resultSet.next()){          

					int columnCount = resultSet.getMetaData().getColumnCount();
					for(int k=1; k<=columnCount; k++){
						reqValue = resultSet.getString(k);
						resultValue.add(reqValue); 

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			finally {
				if (connection != null) {
					try {
						connection.close();
					} catch (Exception ex) {

					}
				}
			}

		}catch(Exception sqlEx) {

		}
		return resultValue;
	}
	
	public static ArrayList<String> executeSQLQuery_List_SecondCol(String sqlQuery) {


		ArrayList<String> resultValue = new ArrayList<String>();
		ResultSet resultSet;

		//To connect with QA Database
		//To connect with QA Database
		//String connectionUrl=Generic_Class.getPropertyValue("DB_Connection_Url_aut");
		String connectionUrl=Generic_Class.getPropertyValue("DB_Connection_Url_QA");

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Connection connection = DriverManager.getConnection(connectionUrl);
			if(connection!=null) {
				Reporter.log("Connected to the database...",true);
			}else {
				Reporter.log("Database connection failed ",true);
			}
			Statement statement = connection.createStatement();
			resultSet=statement.executeQuery(sqlQuery);
			String reqValue;
			try {


				while(resultSet.next()){          

						reqValue = resultSet.getString(2);
						resultValue.add(reqValue); 

					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			finally {
				if (connection != null) {
					try {
						connection.close();
					} catch (Exception ex) {

					}
				}
			}

		}catch(Exception sqlEx) {
			System.out.println("Exception Occured: "+sqlEx);
		}
		return resultValue;
	}

	public static void main(String[] args) {
		System.out.println(executeSQLQuery("select EmployeeID from reservation where reservationID='112637453'"));
	}
}



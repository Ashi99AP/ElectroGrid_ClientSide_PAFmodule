package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Consumption {
	
	Connection con = null;
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/consumption_management?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
	
			// 
			
			
			//For testing
			System.out.print("Successfully connected");
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	// A common method to connect to the DB

	public String insertconsumption(String RangeOfUnit, String Price, String Date, String Comments) {
	
		String output="";
		
		try {
			Connection con = connect(); 
			 
			if (con == null)    
			{return "Error while connecting to the database for inserting."; } 
			
			// create a prepared statement 
			String query = " insert into consumption(`Consum_ID`,`RangeOfUnit`,`Price`,`Date`,`Comments`)" + " values (?,?, ?, ?,?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, RangeOfUnit);
			preparedStmt.setFloat(3, Float.parseFloat(Price));
			preparedStmt.setString(4,Date);
			preparedStmt.setString(5, Comments);

			// execute the statement 
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
			
			String newConsumption = readconsumptions();
			output =  "{\"status\":\"success\", \"data\": \"" + newConsumption + "\"}"; 

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the consumption.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String readconsumptions() {
		String output = "";
		try {
			Connection con = connect(); 

			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Unit Range</th><th>Price</th><th>Date</th><th>Comments</th></tr>";
			
			String query = "select * from consumption";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()) {
				String Consum_ID = Integer.toString(rs.getInt("Consum_ID"));
				String RangeOfUnit = rs.getString("RangeOfUnit");
				String Price = Float.toString(rs.getFloat("Price"));
				String Date = rs.getString("Date");
				String Comments = rs.getString("Comments");

				// Add into the html table
				output += "<tr><td><input id=\'hidConsumptionIDUpdate\' name=\'hidConsumptionIDUpdate\' type=\'hidden\' value=\'" + Consum_ID + "'>" 
						+ RangeOfUnit + "</td>"; 
				
				output += "<td>" + Price + "</td>";
				output += "<td>" + Date + "</td>";
				output += "<td>" + Comments + "</td>";
				
				// buttons     
				output +="<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"     
						
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-consumptionid='" + Consum_ID + "'>" + "</td></tr>"; 
			

			}
			
			con.close();
			
			// Complete the html table
			output += "</table>";
			
		} catch (Exception e) {
			output = "Error while reading the consumptions.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateconsumption(String Consum_ID, String RangeOfUnit, String Price, String Date, String Comments) {

		String output = "";
		try {
			Connection con = connect(); 

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			
			
			// create a prepared statement
			String query = "UPDATE consumption SET RangeOfUnit=?,Price=?,Date=?,Comments=?" + "WHERE Consum_ID=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);

			//consumption values
			preparedStmt.setString(1, RangeOfUnit);
			preparedStmt.setFloat(2, Float.parseFloat(Price));		
			preparedStmt.setString(3, Date);
			preparedStmt.setString(4, Comments);

			preparedStmt.setInt(5, Integer.parseInt(Consum_ID)); 

			// execute the statement    
						preparedStmt.execute();    
						con.close();  
						String newConsumption = readconsumptions();    
						output = "{\"status\":\"success\", \"data\": \"" + newConsumption + "\"}";  
						
			

		} catch (Exception e) {
			output =  "{\"status\":\"error\", \"data\": \"Error while updating the consumption.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteconsumption(String Consum_ID) {
		
		String output = "";

		try {

			Connection con = connect(); 
			
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from consumption where Consum_ID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1,Integer.parseInt(Consum_ID));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			//////////output = "Deleted successfully";
			
			String newConsumption = readconsumptions();    
			output = "{\"status\":\"success\", \"data\": \"" +  newConsumption + "\"}";    

		} catch (Exception e) {
			output = "Error while deleting the consumption.";
			System.err.println(e.getMessage());
		}
		return output;
	}

}

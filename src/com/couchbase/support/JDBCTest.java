package com.couchbase.support;

// May 14, 2015
// This is a simple test of JDBC connectivity to Couchbase
// This was developed with Java 8 and latest Simba Couchbase JDBC driver
// from https://simba.app.box.com/couchbasedeveloperpreview
//
// Assumes that the beer-sample is loaded onto the cluster already

// The output should look something like the following:

// About to get connection to Couchbase...
// About to create statement...
// About to execute query...
// executeQuery() took 3453 ms.
// The result has 12 columns.
// Found 7303 query results.
// Goodbye.


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Properties;

// import com.simba.couchbase.jdbc41.*;
import com.simba.couchbase.jdbc4.*;


public class JDBCTest {

	public static void main(String[] args) {

		String hostname = "192.168.0.1"; // Please change this to yours
		
	    Properties connectionProps = new Properties();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		long t1 = 0, t2 = 0;
		
	    try {
	   
		System.out.println("About to get connection to Couchbase...");

	    conn = DriverManager.getConnection("jdbc:couchbase://" + hostname + ":8093/default;queryEnabled=1",
	                   connectionProps);		

		System.out.println("About to create statement...");
	    stmt = conn.createStatement();

		System.out.println("About to execute query...");

	    t1 = System.currentTimeMillis();
	    rs = stmt.executeQuery("select * from `beer-sample`;");
	    t2 = System.currentTimeMillis();

	    System.out.println("executeQuery() took " + ( t2 - t1 ) + " ms.");
	    
	    ResultSetMetaData rsmd = rs.getMetaData();
	    int numberOfColumns = rsmd.getColumnCount();
	    System.out.println("The result has " + numberOfColumns + " columns.");
	    
	    int resultsFound = 0;
	    
	    while (rs.next()) {
	    	resultsFound++;
	    }
	        
	    System.out.println("Found " + resultsFound + " query results." );
	    
	    }
	    catch (Exception e) {
	    	System.out.println("Caught exception: " + e);
	    }

	    System.out.println("Goodbye." );

	}
	    
}

// eof

package com.group23.fooddelivery.persistence;

public class DBManager {
	private static DBManager instance = null;
	private static DBSource dataSource;
	
	static {
		try {
			Class.forName("org.postgresql.Driver");
			dataSource = new DBSource("jdbc:postgresql://localhost:5432/FoodDeliveryDB","postgres","postgres");
		} 
		catch (Exception e) {
			System.err.println("PostgresDAOFactory.class: failed to load postgresql JDBC driver\n"+e);
			e.printStackTrace();
		}
	}
	
	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}
	
	private DBManager() {}
	
	public static DBSource getDataSource() {
		return dataSource;
	}
	
	
}

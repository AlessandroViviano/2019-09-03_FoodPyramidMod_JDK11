package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.InfoArco;
import it.polito.tdp.food.model.Portion;

public class FoodDAO {
	
	public List<String> getPortionDisplayNames(int C){
		String sql = "SELECT DISTINCT portion_display_name " + 
				"FROM food_pyramid_mod.portion " + 
				"WHERE calories < ? " + 
				"ORDER BY portion_display_name";
		
		List<String> porzioni = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, C);
			
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				porzioni.add(rs.getString("portion_display_name"));
			}
			conn.close();
			return porzioni;
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al database", e);
		}
	}
	
	public List<InfoArco> getTuttiArchi(){
		String sql = "SELECT p1.portion_display_name AS NAME1, p2.portion_display_name AS NAME2, COUNT(DISTINCT p1.food_code) AS peso " + 
				"FROM `portion` AS p1, `portion` AS p2 " + 
				"WHERE p1.food_code = p2.food_code AND " + 
				"p1.portion_id != p2.portion_id " + 
				"GROUP BY p1.portion_display_name, p2.portion_display_name";
		
		List<InfoArco> archi = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				archi.add(new InfoArco(rs.getString("NAME1"),
						rs.getString("NAME2"),
						rs.getInt("peso")));
			}
			
			conn.close();
			return archi;
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al database", e);
		}
	}
	
	
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	

}

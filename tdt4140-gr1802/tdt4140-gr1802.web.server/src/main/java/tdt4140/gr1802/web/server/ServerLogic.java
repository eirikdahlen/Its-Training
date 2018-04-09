//package tdt4140.gr1802.web.server;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.management.RuntimeErrorException;
//
//public class ServerLogic { // class mainly for handling connection to mySQL
//
//
//        public static void signup(String username,
//                                  String password,
//                                  String sport,
//                                  String firstname,
//                                  String surname,
//                                  String maxpulse,
//                                  String weight,
//                                  String gender) {
//        		//connecting to database
//            MysqlDataSource dataSource = new MysqlDataSource();
//            dataSource.setUser("root");
//            dataSource.setPassword("cygnus6cygnus");
//            dataSource.setServerName("localhost");
//            dataSource.setPort(3306);
//            dataSource.setDatabaseName("PU");
//
//
//            	//query to database
//            String sql = "Insert into users" + "(username, password, firstname, surname, maxpuls, weight, gender, sport)" +
//                    "values (?, ?, ?, ?, ?, ?, ?, ?)";
//
//            Connection conn = null;
//
//            try {
//                conn = dataSource.getConnection(); // attempting to establish connection to databse
//                PreparedStatement ps = conn.prepareStatement(sql); // compile rfor SQL-statement
//                ps.setInt(1, Integer.parseInt(username)); // Setting variables listet as "?" in SQL-string
//                ps.setString(2, password);
//                ps.setString(3, firstname);
//                ps.setString(4, surname);
//                ps.setInt(5, Integer.parseInt(maxpulse));
//                ps.setInt(6, Integer.parseInt(weight));
//                ps.setString(7, gender);
//                ps.setString(8, sport);
//                ps.executeUpdate(); //Updating database
//                ps.close();
//
//            } catch (SQLException e) { // E. g. already existing Primary Key will be caught here
//                throw new RuntimeException(e);
//            } finally {
//                if (conn != null) {
//                    try {
//                        conn.close();
//                    } catch (SQLException e) {
//                    }
//                }
//            }
//        }
//        
//        public static boolean login(String username, String password) {
//    			
//        	 	 MysqlDataSource dataSource = new MysqlDataSource();
//             dataSource.setUser("root");
//             dataSource.setPassword("cygnus6cygnus");
//             dataSource.setServerName("localhost");
//             dataSource.setPort(3306);
//             dataSource.setDatabaseName("PU");
//             
//             String sql = "select * from users where username = ? and password = ?";
//             
//             Connection conn = null;
//             ResultSet resultSet = null; // needed for reading output from database
//             
//             try {
//	            	 conn = dataSource.getConnection();
//	                 PreparedStatement ps = conn.prepareStatement(sql);
//	                 ps.setInt(1, Integer.parseInt(username));
//	                 ps.setString(2,  password);
//	                 resultSet = ps.executeQuery();
//	                 if (resultSet.next() ) { //seeing if query returns empty table of data, meaning that user/pw-combo doesn't exist and login not possible
//	                	    return true;
//	                	} 
//	                 else {
//	                	 	return false; 
//	                 }
//             }catch (SQLException e) {            	 
//            	 	throw new RuntimeException(e);
//             } finally {
//            	 	if (conn!=null) {
//            	 		try {
//            	 			conn.close();
//            	 		}catch (SQLException e) {
//            	 		}
//            	 	}
//             }
//             
//      
//    }
//        
//       public static boolean registerWorkout(String username,
//    		   									String duration,
//    		   								    String pulses,
//    		   								    String goal,
//    		   								    String sport,
//    		   								    String privacy) {
//    	   
//    	   	   MysqlDataSource dataSource = new MysqlDataSource();
//           dataSource.setUser("root");
//           dataSource.setPassword("cygnus6cygnus");
//           dataSource.setServerName("localhost");
//           dataSource.setPort(3306);
//           dataSource.setDatabaseName("PU");
//           
//           String sql = "insert into workouts (username, duration, pulses, goal, sport, privacy)" +
//           		"values (?, ?, ?, ?, ?, ?)";
//           
//           Connection conn = null;
//           Boolean success = true;
//           
//           try {
//        	   		conn = dataSource.getConnection();
//        	   		PreparedStatement ps = conn.prepareStatement(sql);
//        	   		ps.setString(1,  username);
//        	   		ps.setInt(2, Integer.parseInt(duration));
//        	   		ps.setString(3,  pulses);
//        	   		ps.setString(4,  goal);
//        	   		ps.setString(5,  sport);
//        	   		ps.setString(6,  privacy);
//        	   		int ex = ps.executeUpdate();
//        	   		if (ex>=1) {
//        	   			success = true;
//        	   		}else {
//        	   			success = false;
//        	   		}
//        	   	
//        	   
//           }catch (SQLException e) {
//        	   		success = false;
//        	   		throw new RuntimeException(e);
//        	   		
//        	   		
//           }finally 
//           {
//	        	   if (conn!=null) {
//	       	 		try {
//	       	 			conn.close();
//	       	 		}catch (SQLException e) {
//	       	 
//	       	 		}
//           }  	   
//        }        
//       return success;}  
//       
//       public static ArrayList<Athlete> getAthletesInSport(String sport) {
//           MysqlDataSource dataSource = new MysqlDataSource();
//       dataSource.setUser("root");
//       dataSource.setPassword("cygnus6cygnus");
//       dataSource.setServerName("localhost");
//       dataSource.setPort(3306);
//       dataSource.setDatabaseName("PU");
//
//       String sql = "select firstname, surname, username from users where sport = ?";
//
//       Connection conn = null;
//       ResultSet resultSet = null; // needed for reading output from database
//       ArrayList<Athlete> users = new ArrayList<Athlete>();
//
//       try {
//          	 conn = dataSource.getConnection();
//               PreparedStatement ps = conn.prepareStatement(sql);
//               ps.setString(1,  sport);
//               resultSet = ps.executeQuery();
//               while (resultSet.next()) {
//                    JSONObject user = new JSONObject();
//                    String firstName = resultSet.getString(1);
//                    String surname = resultSet.getString(2);
//                    String username = resultSet.getString(3);
//                    users.add(new Athlete(firstName, surname, username));
//               }
//
//       }catch (SQLException e) {
//      	 	throw new RuntimeException(e);
//       } finally {
//      	 	if (conn!=null) {
//      	 		try {
//      	 			conn.close();
//      	 		}catch (SQLException e) {
//      	 		}
//      	 	}
//       }
//
//
//       return users;
//        }
//       
//       
//       public static String getSportForCoach(String username) {
//   			
//       	 	 MysqlDataSource dataSource = new MysqlDataSource();
//            dataSource.setUser("root");
//            dataSource.setPassword("cygnus6cygnus");
//            dataSource.setServerName("localhost");
//            dataSource.setPort(3306);
//            dataSource.setDatabaseName("PU");
//            
//            String sql = "select sport from users where username = ?";
//            
//            Connection conn = null;
//            ResultSet resultSet = null; // needed for reading output from database
//            String feedback = "";
//            try {
//	            	 conn = dataSource.getConnection();
//	                 PreparedStatement ps = conn.prepareStatement(sql);
//	                 ps.setString(1,  username);
//	                 resultSet = ps.executeQuery();
//	                 if (resultSet.next() ) { //seeing if query returns empty table of data	    
//	                	    feedback = resultSet.getString(1);
//	                	} 
//	                 else {
//	                	 feedback = "Not a registered coach";
//	                 }
//            }catch (SQLException e) {            	 
//           	 	throw new RuntimeException(e);
//            } finally {
//           	 	if (conn!=null) {
//           	 		try {
//           	 			conn.close();
//           	 		}catch (SQLException e) {
//           	 		}
//           	 	}
//            }
//            
//     return feedback;
//   }   

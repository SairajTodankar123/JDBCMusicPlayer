package com.jspiders.jdbcmusicplayer.songoperation;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import com.jspiders.jdbcmusicplayer.main.JdbcMusicPlayer;

public class JdbcSongOperation {

		 static Connection connection;
		 static PreparedStatement preparedStatement;
		 static ResultSet resultSet;
		 static FileReader fileReader;
		 static Properties properties = new Properties();
		 static String query;
		 static int result;
		 static Scanner scanner = new Scanner(System.in);
		 static String filePath = "D:\\Qspider\\J2EE\\jdbcmusicplayer\\resources\\db_info_properties";
		 
		 public static void openConnection() {
			 
			try {
				
				fileReader = new FileReader(filePath);
				properties = new Properties();
				properties.load(fileReader);
				
				Class.forName(properties.getProperty("driverPath"));
				
				connection = DriverManager.getConnection(properties.getProperty("dbPath"), properties);
				
			} catch (IOException | ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
		 }
		 
		 public static void closeConnection() {
			 if(connection != null) {
				 try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			 }
		 }
		 
		 public static void chooseSongToPlay() {
			 System.out.println("Your Available Song List ");
			 showAllSong();
			 System.out.println("Press the choice \n"
			 		+ "1. Play a Song \n"
			 		+ "2. Play All Song \n"
			 		+ "3. Play Random Song");
			 int select = scanner.nextInt();
			 
			 switch (select) {
			case 1:
				System.out.println("Enter the song id");
				int id = scanner.nextInt();
				openConnection();
				query = "select * from song where id =" +id;
				try {
					preparedStatement = connection.prepareStatement(query);
					resultSet = preparedStatement.executeQuery();
					System.out.println(resultSet.getString(1) + "Now Playing...");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				break;
				
			case 2:
				playAllSong();
				System.out.println("---------------------------------------------------------");
				break;
				
			case 3:
				playRandomSong();
				System.out.println("---------------------------------------------------------");
				
			default:
				System.out.println("Oops...Invalid Choice, Please Enter Valid Choice");
				JdbcMusicPlayer.menu();
				break;
			}
		 }
		 
		 public static void playRandomSong() {
			 System.out.println("Play Random Songs.....");
			 openConnection();
			 query = "select * from song order by rand() limit 10";
			 
			 try {
				 
				preparedStatement = connection.prepareStatement(query);
				resultSet = preparedStatement.executeQuery();

				while(resultSet.next()) {
					System.out.println(resultSet.getString(2) + "Song from" + resultSet.getString(5) + "Movies is playing now..");
				}
			 } catch (SQLException e) {
				e.printStackTrace();
			}
			 finally {
				 if(connection != null) {
					 try {
						connection.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				 }
			 }
			 
			 System.out.println("You want to continue \n"
			 		+ "1. Yes \n"
			 		+ "2. No");
			 int option = scanner.nextInt();
			 
			 switch (option) {
			case 1:
				playRandomSong();
				System.out.println("---------------------------------------");
				break;
				
			case 2:
				System.out.println("Moving back to the main menu");
				JdbcMusicPlayer.menu();

			default:
				System.out.println("invalid choice....Lets go to main menu");
				JdbcMusicPlayer.menu();
				break;
			}
		 }
		 
		 public static void showAllSong() {
			 openConnection();
			 query = "select * from song";
			 
			 try {
				 
				preparedStatement = connection.prepareStatement(query);
				resultSet = preparedStatement.executeQuery();
				int count = 0;
				
				while(resultSet.next()) {
					System.out.println(resultSet.getString(1) + " "
						+ resultSet.getString(2) +" "
							+ resultSet.getString(3) +" "
								+ resultSet.getString(4) +" "
									+ resultSet.getString(5));
					count++;
				}
				if(count == 0) {
					System.out.println("Oops...No song are available please add Song \n"
							+ "1. Add Song \n"
							+ "2. Back");
					int select = scanner.nextInt();
					
					switch (select) {
					case 1:
						addSong();
						System.out.println("---------------------------------------------");
						break;
						
					case 2:
						JdbcMusicPlayer.menu();
						System.out.println("--------------------------------------------");

					default:
						System.out.println("Invalid choice...");
						break;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeConnection();
		 }
		 
		 public static void addSong() {
			 System.out.println("Please Enter the Number of Song You Wish to Add");
				int no = scanner.nextInt();
				int count = 0;
				
				for(int t=0; t<no; t++) {
					openConnection();
					int result;
					query = "insert into song values(?,?,?,?,?)";
					
					try {
						
						preparedStatement = connection.prepareStatement(query);
						System.out.println("Enter the Song Id");
						preparedStatement.setInt(1, scanner.nextInt());
						scanner.nextLine();
						System.out.println("Enter the Song Name");
						preparedStatement.setString(2, scanner.next());
						scanner.nextLine();
						System.out.println("Enter the Song Duration");
						preparedStatement.setDouble(3, scanner.nextDouble());
						scanner.nextLine();
						System.out.println("Enter the Singer Name");
						preparedStatement.setString(4, scanner.next());
						scanner.nextLine();
						System.out.println("Enter the Album Name");
						preparedStatement.setString(5, scanner.next());
						result = preparedStatement.executeUpdate();
						count = count + result;

					} catch (SQLException e) {
						e.printStackTrace();
					} catch (Exception e) {
						System.out.println("Please give valid input");
					}
					no--;
				}
				System.out.println("Add Song Successfully........");
				showAllSong();
				System.out.println("Do you want to play song one by one \n"
						+ "1. Yes \n"
						+ "2. No ");
				switch (scanner.nextInt()) {
				case 1:
					System.out.println("okay...Lets Enjoy the Song");
					playAllSong();
					break;
				case 2:
					System.out.println("We are moving back to main menu");
					JdbcMusicPlayer.menu();
					System.out.println("----------------------------------------------------");
				default:
					System.out.println("Invalid input..Lets go to main menu");
					JdbcMusicPlayer.menu();
					System.out.println("----------------------------------------------------");
				}
		 }
		 
		 public static void removeSong() {
			 showAllSong();
			 openConnection();
			 System.out.println("Do you want to remove all song \n"
			 		+ "1. Yes \n"
			 		+ "2. No");
			 int trunck = scanner.nextInt();
			 switch (trunck) {
			case 1:		
				query = "truncate table song";
				
				try {
					preparedStatement = connection.prepareStatement(query);
					resultSet = preparedStatement.executeQuery();
					System.out.println("Song is Remove Successfully....!! ");
					System.out.println("------------------------------------------------");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;

			case 2:
				System.out.println("Want to remove specific song...\n"
						+ "1. Yes \n"
						+ "2. No");
				switch (scanner.nextInt()) {
				case 1:
					removeSong();
					System.out.println("--------------------------------------------------");
					break;
				case 2:
					System.out.println("Go to main menu");
					JdbcMusicPlayer.menu();
					System.out.println("--------------------------------------------------");
					break;
				default:
					System.out.println(" Invalid choice...lets go to main menu");
					JdbcMusicPlayer.menu();
					System.out.println("--------------------------------------------------");
					break;
				}
				break;
				
			default:
				System.out.println(" Invalid choice...lets go to main menu");
				JdbcMusicPlayer.menu();
				break;
			}
			 closeConnection();
		 }
		 
		 public static void updateSong() {
			 System.out.println("How many Song you want to update");
			 int no = scanner.nextInt();
			 
			 while(0 < no) {
				 System.out.println("Enter your choice \n"
				 		+ "1. For update ID \n"
				 		+ "2. For update Songname \n"
				 		+ "3. For update duration \n"
				 		+ "4. For update singer name \n"
				 		+ "5. For update album ");
				 switch (scanner.nextInt()) {
				case 1:
					updateId();
					System.out.println("--------------------------------");
					break;
					
				case 2:
					updateSongName();
					System.out.println("--------------------------------");
					break;
					
				case 3:
					updateDuration();
					System.out.println("--------------------------------");
					break;
					
				case 4:
					updateSingerName();
					System.out.println("--------------------------------");
					break;
					
				case 5:
					updateAlbum();
					System.out.println("--------------------------------");
					break;
					
				default:
					System.out.println("Invalid choice");
					updateSong();
					System.out.println("--------------------------------");
					break;
				}
				 no--;
			 }
		 }
		 
		 public static void displayAllSong() {
			 showAllSong();
			 System.out.println("--------------------------------");
		 }
		 
		 public static void playAllSong() {
			 openConnection();
			 query = "select * from song";
			 
			 try {
				 
				preparedStatement = connection.prepareStatement(query);
				resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next()) {
					System.out.println(resultSet.getString(2) + "Song from" + resultSet.getString(5) + "Movies is playing now..");
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			 finally {
				if(connection != null) {
					try {
						connection.close();
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
				}
				System.out.println("Do tou want to play again \n"
						+ "1. Yes \n"
						+ "2. No");
				int in = scanner.nextInt();
				switch (in) {
				case 1:
					playAllSong();
					break;
					
				case 2:
					System.out.println("We are moving back to the main menu");
					JdbcMusicPlayer.menu();

				default:
					System.out.println("Invalid choice");
					JdbcMusicPlayer.menu();
					break;
				}
			}
		 }
		 
		 public void deleteSong() {
				System.out.println("Enter the id of Song");
				query = "delete from song where id=" + scanner.nextInt();
				try {
					preparedStatement = connection.prepareStatement(query);
					result = preparedStatement.executeUpdate();
					System.out.println(result + " song deleted successfully");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		 
		 private static void updateId() {
				openConnection();
				query = "update song set id= ? where id =?";
				try {
					System.out.print("Enter new id:");
					preparedStatement = connection.prepareStatement(query);
					preparedStatement.setInt(1, scanner.nextInt());
					System.out.print("Enter old id:");
					preparedStatement.setInt(2, scanner.nextInt());
					result = preparedStatement.executeUpdate();
					System.out.println(result + " Song updated Successfully...!");
					System.out.println("-------------------------------------------------");

				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					closeConnection();
				}

			}
		 
		 private static void updateSongName() {
				openConnection();
				query = "update song set songName= ? where id =?";
				try {
					System.out.print("Enter new songName:");
					preparedStatement = connection.prepareStatement(query);
					preparedStatement.setString(2, scanner.nextLine());
					System.out.print("Enter song id:");
					preparedStatement.setInt(1, scanner.nextInt());
					result = preparedStatement.executeUpdate();
					System.out.println(result + " Song updated Successfully...!");
					System.out.println(
							"--------------------------------------------------------------------------------------------------------");

				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					closeConnection();
				}
			}
		 
		 private static void updateDuration() {
				openConnection();
				query = "update song set duration= ? where duration =?";
				try {
					System.out.print("Enter new duration:");
					preparedStatement = connection.prepareStatement(query);
					preparedStatement.setInt(1, scanner.nextInt());
					System.out.print("Enter old duration:");
					preparedStatement.setInt(2, scanner.nextInt());
					result = preparedStatement.executeUpdate();
					System.out.println(result + " Song updated Successfully...!");
					System.out.println("-------------------------------------------------");

				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					closeConnection();
				}

			}
		 
		 private static void updateSingerName() {
				openConnection();
				query = "update song set singerName= ? where id =?";
				try {
					System.out.print("Enter new singerName:");
					preparedStatement = connection.prepareStatement(query);
					preparedStatement.setString(4, scanner.nextLine());
					System.out.print("Enter song id:");
					preparedStatement.setInt(1, scanner.nextInt());
					result = preparedStatement.executeUpdate();
					System.out.println(result + " Song updated Successfully...!");
					System.out.println("----------------------------------------------------");

				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					closeConnection();
				}
			}
		 
		 private static void updateAlbum() {
				openConnection();
				query = "update song set moviesName= ? where id =?";
				try {
					System.out.print("Enter new songName:");
					preparedStatement = connection.prepareStatement(query);
					preparedStatement.setString(1, scanner.nextLine());
					System.out.print("Enter song id:");
					preparedStatement.setInt(2, scanner.nextInt());
					result = preparedStatement.executeUpdate();
					System.out.println(result + " Song updated Successfully...!");
					System.out.println("----------------------------------------------------");

				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					closeConnection();
				}

			}

		
}

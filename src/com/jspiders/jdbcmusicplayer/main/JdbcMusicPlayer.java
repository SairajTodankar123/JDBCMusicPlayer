package com.jspiders.jdbcmusicplayer.main;

import java.util.Scanner;

import com.jspiders.jdbcmusicplayer.songoperation.JdbcSongOperation;

public class JdbcMusicPlayer {

		static JdbcSongOperation jdbcSongOperation = new JdbcSongOperation();
		
		static Scanner scanner = new Scanner(System.in);
		
		public static void menu() {
			
			boolean loop = true;
			
			while(loop) {
				System.out.println("Please Choose Your Option \n"
						+ "1. Play Song \n"
						+ "2. Add Song \n"
						+ "3. Remove Song \n"
						+ "4. Update Song \n"
						+ "5. Display All Song \n"
						+ "6. Exit");
				
				int choice = scanner.nextInt();
				
				switch (choice) {
				case 1:
					JdbcSongOperation.chooseSongToPlay();
					break;

				case 2:
					JdbcSongOperation.addSong();
					break;
					
				case 3:
					JdbcSongOperation.removeSong();
					break;
					
				case 4:
					JdbcSongOperation.updateSong();
					break;
					
				case 5:
					JdbcSongOperation.displayAllSong();
					break; 
					
				case 6:
					loop = false;
					System.out.println("*******************Thank you*********************");
					break;
					
				default:
					System.out.println("-----Please enter the valid choice-----");
					break;
				}
			}
		}
		
		public static void main(String[] args) {
			System.out.println("------WELCOME TO MUSIC PLAYER------");
			menu();
		}
}

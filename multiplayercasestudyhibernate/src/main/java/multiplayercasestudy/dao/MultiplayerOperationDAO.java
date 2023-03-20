package multiplayercasestudy.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import multiplayercasestudy.dto.SongsDTO;

//import multiPlayerCaseStudy.Song;

public class MultiplayerOperationDAO {

	static List<SongsDTO> songList = new ArrayList<>();
	static List<SongsDTO> list;
	static Scanner scanner = new Scanner(System.in);
	private static int choose;

	private static EntityManagerFactory factory;
	private static EntityManager manager;
	private static EntityTransaction transaction;
	private static javax.persistence.Query query;
	private static String displayQuery;

	private static void openConnection() {
		factory = Persistence.createEntityManagerFactory("multiplayer");
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
	}

	private static void closeConnection() {
		if (factory != null) {
			factory.close();
		}
		if (manager != null) {
			factory.close();
		}
		if (transaction.isActive()) {
			transaction.rollback();
		}
	}

//	menu list show 
	public void menuList() {

		boolean menu1 = true;

		while (menu1) {
			System.out.println("MENU \n\t1. Play Song \n\t2. Add/Remove Song \n\t3. Update Song \n\t4. Exit");

			int a = scanner.nextInt();

			switch (a) {
			case 1:
				System.out.println("playing song in order ");
				playSongs();
				break;

			case 2:
				System.out.println("2 add / remove songs");
				addRemoveSong();
				break;

			case 3:
				System.out.println("3 update songs");
				updateSongs();
				break;

			case 4:
				System.out.println("4 Exit");
				menu1 = false;
				break;

			default:
				System.out.println("Give some valid input");
				break;
			}
		}
	}

//	crating the method for the 1  play song
	public static void playSongs() {

		boolean playsong = true;

		while (playsong) {
			System.out.println(
					"\nPlaySong MENU \n\t1. Play All Songs \n\t2. Choose Song \n\t3. Play Random Song \n\t4. Go Back");

			int a = scanner.nextInt();

			switch (a) {
			case 1:
				System.out.println("Playing all songs in sequence");
				displaySongs();
				break;

			case 2:
				System.out.println("Choose song form list");
				chooseSong();
				break;

			case 3:
				System.out.println("Play random");
				playRandom();
				break;

			case 4:
				System.out.println("Go Back");
				playsong = false;
				break;

			default:
				System.out.println("give some valid input");
				break;
			}
		}
	}

//	display songs
	public static void displaySongs() {
		try {
			openConnection();
			transaction.begin();

			displayQuery = "from SongsDTO";
			query = manager.createQuery(displayQuery);

			list = query.getResultList();

			for (SongsDTO songsDTO : list) {
				System.out.println(songsDTO);
			}
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	// choose song
	public static void chooseSong() {
		try {
			openConnection();
			transaction.begin();

			displayQuery = "from SongsDTO";
			query = manager.createQuery(displayQuery);

			list = query.getResultList();

			for (SongsDTO songsDTO : list) {
				System.out.println(songsDTO.getId() + " || " + songsDTO.getName());
			}

			System.out.println("Enter song no. want to choose for play");
			choose = scanner.nextInt();

			for (SongsDTO songsDTO : list) {
				if (songsDTO.getId() == choose) {
					System.out.println(songsDTO);
				}
			}
			System.out.println("Playing the song which you want to play ");

			transaction.commit();
		} finally {
			closeConnection();
		}

	}

//	Play random song
	public static void playRandom() {

		try {
			openConnection();
			transaction.begin();

			displayQuery = "from SongsDTO";
			query = manager.createQuery(displayQuery);

			list = query.getResultList();

			for (SongsDTO songsDTO : list) {
				System.out.println(songsDTO.getId() + " || " + songsDTO.getName());
			}

			Random random = new Random();
			System.out.print("play random song : ");
			choose = random.nextInt(list.size());

			for (SongsDTO songsDTO : list) {
				if (songsDTO.getId() == choose + 1) {
					System.out.println(songsDTO);
				}
			}
			System.out.println("Playing random song ...!");

			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

//	 add / remove songs
	public static void addRemoveSong() {

		boolean addremove = true;

		while (addremove) {
			System.out.println("\nAdd/Remove Songs MENU \n\t1. Add Song \n\t2. Remove Song \n\t3. Go Back");

			int a = scanner.nextInt();

			switch (a) {
			case 1:
				System.out.println("Adding the songs");
				addSongs();
				displaySongs();
				break;

			case 2:
				System.out.println("which song Remove from list");
				displaySongs();
				removeSong();
				break;

			case 3:
				System.out.println("Go Back");
				addremove = false;
				break;

			default:
				System.out.println("give some valid input");
				break;
			}
		}
	}

//	add songs to array list
	public static void addSongs() {
		try {
	
			System.out.println("how many songs do u want to add");
			int a = scanner.nextInt();
			for (int i = 1; i <= a; i++) {
				openConnection();
				transaction.begin();
				System.out.println("Enter the id of song");
				int id = scanner.nextInt();

				System.out.println("Enter the song name");
				String name = scanner.next();

				System.out.println("Enter the song duration");
				double duration = scanner.nextDouble();

				System.out.println("Enter the song movie");
				String movie = scanner.next();

				System.out.println("Enter the song singer");
				String singer = scanner.next();

				SongsDTO song1 = new SongsDTO();
				song1.setId(id);
				song1.setName(name);
				song1.setDuration(duration);
				song1.setMovie(movie);
				song1.setSinger(singer);

				// add object into the array list
				songList.add(song1);
				
				manager.persist(song1);
				
				transaction.commit();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	// remove song
	public static void removeSong() {
		try {
			openConnection();
			transaction.begin();

			displayQuery = "FROM SongsDTO";
			query = manager.createQuery(displayQuery);

			list = query.getResultList();

			System.out.println("Enter song no. want to romove ");
			choose = scanner.nextInt();

			for (SongsDTO songsDTO : list) {
				if (songsDTO.getId() == choose) {
					System.out.println(songsDTO);
					manager.remove(songsDTO);
				}
			}

			System.out.println("remove song from list");
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

//	creating the method for update songs
	public static void updateSongs() {
		boolean update = true;

		while (update) {
			System.out.println(
					"\nUpdateSong MENU \n\t1. Name \n\t2. Singer \n\t3. Movie/Album \n\t4. Duration \n\t5. Go Back");

			int a = scanner.nextInt();

			switch (a) {
			case 1:
				try {
					openConnection();
					transaction.begin();

					displayQuery = "from SongsDTO";
					query = manager.createQuery(displayQuery);

					list = query.getResultList();

					for (SongsDTO songsDTO : list) {
						System.out.println(songsDTO.getId() + " || " + songsDTO.getName());
					}
					
					System.out.println(" Name update ..");
					System.out.println("Enter song no. want to choose for update");
					choose = scanner.nextInt();

					for (SongsDTO songsDTO : list) {
						if (songsDTO.getId() == choose) {
							System.out.println("Enter the new name");
							String name = scanner.next();
							songsDTO.setName(name);
							System.out.println("Song updates"+songsDTO);
						}
					}
					System.out.println("Song name updated ...! ");

					transaction.commit();

				} finally {
					closeConnection();
				}
				break;

			case 2:
				try {
					openConnection();
					transaction.begin();

					displayQuery = "from SongsDTO";
					query = manager.createQuery(displayQuery);

					list = query.getResultList();

					for (SongsDTO songsDTO : list) {
						System.out.println(songsDTO.getId() + " || " + songsDTO.getName());
					}
					
					System.out.println("singer updating");
					System.out.println("Enter song no. want to choose for update");
					choose = scanner.nextInt();

					for (SongsDTO songsDTO : list) {
						if (songsDTO.getId() == choose) {
							System.out.println("Enter the new name");
							String singer = scanner.next();
							songsDTO.setSinger(singer);
							System.out.println("Song updates : "+songsDTO);
						}
					}
					System.out.println("Song singer updated ...! ");

					transaction.commit();

				} finally {
					closeConnection();
				}
				break;

			case 3:

				try {
					openConnection();
					transaction.begin();

					displayQuery = "from SongsDTO";
					query = manager.createQuery(displayQuery);

					list = query.getResultList();

					for (SongsDTO songsDTO : list) {
						System.out.println(songsDTO.getId() + " || " + songsDTO.getName());
					}

					System.out.println("change movie/album");
					System.out.println("Enter song no. want to choose for update");
					choose = scanner.nextInt();

					for (SongsDTO songsDTO : list) {
						if (songsDTO.getId() == choose) {
							System.out.println("Enter the new name");
							String movie = scanner.next();
							songsDTO.setMovie(movie);
							System.out.println("Song updates : "+songsDTO);
						}
					}
					System.out.println("Song movie updated ...! ");

					transaction.commit();
				} finally {
					closeConnection();
				}
				break;

			case 4:
				
				try {
					openConnection();
					transaction.begin();

					displayQuery = "from SongsDTO";
					query = manager.createQuery(displayQuery);

					list = query.getResultList();

					for (SongsDTO songsDTO : list) {
						System.out.println(songsDTO.getId() + " || " + songsDTO.getName());
					}

					System.out.println("Duration Change");
					System.out.println("Enter song no. want to choose for update");
					choose = scanner.nextInt();

					for (SongsDTO songsDTO : list) {
						if (songsDTO.getId() == choose) {
							System.out.println("Enter the new duration");
							double d = scanner.nextDouble();
							songsDTO.setDuration(d);
							System.out.println("Song updates : "+songsDTO);
						}
					}
					System.out.println("Song movie updated ...! ");

					transaction.commit();
				} finally {
					closeConnection();
				}
				break;

			case 5:
				System.out.println("Go Back");
				update = false;
				break;

			default:
				System.out.println("give some valid input");
				break;
			}
		}
	}

}

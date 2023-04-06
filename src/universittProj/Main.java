package universittProj;

import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		JDBC jdbc = new JDBC();
		University uni[] = null;
		APIConsumer api = new APIConsumer();
		int choice;

		while (true) {
			System.out.println("\nWelcome to the Universities Project!\n");
			System.out.println("1. Initialize database");
			System.out.println("2. Print list of countries");
			System.out.println("3. Fetch data from API");
			System.out.println("4. Fetch data from database");
			System.out.println("5. Search data from database");
			System.out.println("6. Take backup of database");
			System.out.println("7. Remove tables from database");
			System.out.println("8. Dump data to file");
			System.out.println("9. Print universities");
			System.out.println("0. Exit program\n");

			System.out.print("Enter your choice: ");
			try {
				choice = scn.nextInt();
				scn.nextLine();

				switch (choice) {
				case 0:
					System.exit(0);
				case 1:
					jdbc.createUniversityTable();
					break;
				case 2:
					if (uni != null) {
						HashSet<String> countries = new HashSet<String>();
						for (int i = 0; i < uni.length; i++) {
							countries.add(uni[i].country);
						}
						for (String element : countries) {
							System.out.println(element);
						}
					}
					break;
				case 3:
					uni = api.main();

					break;
				case 4:
					jdbc.fetchFromDB();
					
					break;
				case 5:
					Scanner scanner = new Scanner(System.in);
					System.out.print("Enter a country name: ");
					String country = scanner.nextLine();

					List<University> universities = jdbc.searchIn(country);

					if (universities.isEmpty()) {
					    System.out.println("No universities found in " + country);
					} else {
					    System.out.println("Universities in " + country + ":");
					    for (University university : universities) {
					        System.out.println(university.name);
					    }
					}
;
			        
					break;
				case 6:
					jdbc.backUp();
					break;
				case 7:
					jdbc.dropUniversityTable();

					break;
				case 8:
					api.saveIntoFile();
					break;
				case 9:
					if (uni != null) {
						HashSet<String> univercities = new HashSet<String>();
						for (int i = 0; i < uni.length; i++) {
							univercities.add(uni[i].name);
						}
						for (String element : univercities) {
							System.out.println(element);
						}
					}
					break;
				default:
					System.out.println("Invalid input, please try again.\n");
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input, please try again.\n");
				scn.nextLine(); // Consume left-over invalid input
			}
		}

	}

}

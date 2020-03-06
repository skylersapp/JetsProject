package com.skilldistillery.jets;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class JetsApp {

	public static void main(String[] args) {
		JetsApp app = new JetsApp();
		app.menu();

	}

	public List<Jet> getJetsInput(String file) {
		String fileName = file;
		List<Jet> jetArr = new ArrayList<>();
		
		try {
			BufferedReader buffread = new BufferedReader(new FileReader(fileName));
			String line = null;
			while ((line = buffread.readLine()) != null) {
				String[] jetParams = line.split(",");
				Double param2 = Double.valueOf(jetParams[2]);
				int param3 = Integer.parseInt(jetParams[3]);
				long param4 = Integer.parseInt(jetParams[4]);
				if (jetParams[0].equalsIgnoreCase("Cargo")) {
					CargoPlane cp = new CargoPlane(jetParams[1], param2, param3, param4);
					jetArr.add(cp);
				} else if (jetParams[0].equalsIgnoreCase("fighter")) {
					FighterJet fj = new FighterJet(jetParams[1], param2, param3, param4);
					jetArr.add(fj);
				} else {
					JetImpl gj = new JetImpl(jetParams[1], param2, param3, param4);
					jetArr.add(gj);
				}
			}
			buffread.close();
		} catch (FileNotFoundException e) {
			System.err.println(fileName + " File not Found");
			;
		} catch (IOException e) {
			System.err.println("IO Exception");
		}

		return jetArr;

	}

	public void menu() {
		Scanner input = new Scanner(System.in);
		int choice = 0;
		AirField airfield = new AirField();
		List<Jet> jetArr = getJetsInput("Jets.txt");
		airfield.setJets(jetArr);
		while (true) {
			try {
				System.out.println("Select from the menu options below:\n\n");
				System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
				System.out.println("1: List the fleet");
				System.out.println("2: Fly all of the jets");
				System.out.println("3: View the fastest jet");
				System.out.println("4: View the jet with longest range");
				System.out.println("5: Load all of the Cargo Jets");
				System.out.println("6: Dogfight fighter jets");
				System.out.println("7: Add a jet to the Fleet");
				System.out.println("8: Remove a jet from the Fleet");
				System.out.println("9: Quit Jets Program ");
				System.out.println("\n*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");

				choice = input.nextInt();
			} catch (InputMismatchException e) {
				input.nextLine();
				System.out.println("\n\n\t\tPlease enter a valid number");
				prettyMenu();
				continue;
			}
			switch (choice) {
				case 1:
					System.out.println(airfield.getJets());
					prettyMenu();
					continue;
				case 2:
					jetFlight(jetArr);
					prettyMenu();
					continue;
				case 3:
					fastestJet(jetArr);
					prettyMenu();
					continue;
				case 4:
					jetRange(jetArr);
					prettyMenu();
					continue;
				case 5:
					jetCargo(jetArr);
					prettyMenu();
					continue;
				case 6:
					jetFight(jetArr);
					prettyMenu();
					continue;
				case 7:
					addJet(jetArr, input);
					continue;
				case 8:
					removeJet(jetArr, input);
					continue;
				case 9:
					System.out.println("\nThank you! Goodbye.");
					break;
				default:
					System.out.println("Please select only numbers '1' through '9'. ");
					prettyMenu();
					continue;
			}
			input.close();
			break;

		}

	}

	public void addJet(List<Jet> jetArr, Scanner input) {
		JetImpl jetget;
		input.nextLine();
		while (true) {
			try {
				System.out.println("Enter jet model name");

				String param1 = input.nextLine();
				System.out.println("Enter numeric jet speed (mph) ");
				double param2 = input.nextDouble();
				System.out.println("Enter whole number jet range (miles)");
				int param3 = input.nextInt();
				System.out.println("Enter whole number jet price ($)");
				long param4 = input.nextLong();
				jetget = new JetImpl(param1, param2, param3, param4);
				break;
			} catch (InputMismatchException e) {
				input.nextLine();
				System.out.println("Please enter valid information");
				continue;
			}
		}

		jetArr.add(jetget);

	}

	public void fastestJet(List<Jet> jetArr) {
		Jet fastjet = jetArr.get(0);
		for (Jet jet : jetArr) {
			if (jet.getSpeed() > fastjet.getSpeed()) {
				fastjet = jet;

			}

		}
		System.out.println(fastjet.toString());
	}

	public void prettyMenu() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			System.out.println("Thread interruption error");
			;
		}

	}

	public void jetRange(List<Jet> jetArr) {
		Jet jetRange = jetArr.get(0);
		for (Jet jet : jetArr) {
			if (jet.getRange() > jetRange.getRange()) {
				jetRange = jet;

			}

		}
		System.out.println(jetRange.toString());
	}

	public void jetFlight(List<Jet> jetArr) {
		for (Jet jet : jetArr) {
			jet.fly();

		}

	}

	public void jetCargo(List<Jet> jetArr) {
		int count = 0;
		for (Jet jet : jetArr) {
			if (jet instanceof CargoCarrier) {
				((CargoCarrier) jet).loadCargo();
				count++;
			}

		}
		if (count == 0) {
			System.out.println("There are no cargo planes in the airfield");
		}

	}

	public void jetFight(List<Jet> jetArr) {
		int count = 0;
		for (Jet jet : jetArr) {
			if (jet instanceof CombatReady) {
				((CombatReady) jet).fight();
				count++;
			}
		}
		if (count == 0) {
			System.out.println("There are no fighter jets in the airfield");
		}
	}

	public void removeJet(List<Jet> jetArr, Scanner input) {
		while (true) {
			try {
				if (jetArr.size() <= 0) {
					System.out.println("All of the jets are removed");
					prettyMenu();
					break;
				}
				System.out.println(
						"Select a jet number to remove starting at 1, or you can enter 0 to quit. Array size is " + jetArr.size());
				int jetDelete = input.nextInt() - 1;
				if (jetDelete > jetArr.size() - 1 || jetDelete < -1) {
					System.out.println("The selected number is not in range");
					input.nextLine();
					continue;
				} else if (jetDelete == -1) {
					System.out.println("Returning you to the main menu");
					prettyMenu();
					break;
				}
				jetArr.remove(jetDelete);
			} catch (InputMismatchException e) {
				input.nextLine();
				System.out.println("Please enter a whole number");
				continue;
			}
			break;
		}

	}
}
/**
 * Parses data and analyzes it. Currently, can only compare rank and win rate.
 */

import java.io.*;
import javax.script.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws ScriptException, IOException, InterruptedException {

		/* Write summoner names to text file */
		Scanner scanner = new Scanner(System.in);
		String summonerName1 = null, summonerName2 = null;
		while (summonerName1 == null) {
			System.out.print("Input first summoner name: ");
			summonerName1 = scanner.next();
		}
		while (summonerName2 == null) {
			System.out.print("Input second summoner name: ");
			summonerName2 = scanner.next();
		}
		scanner.close();
		String summonerRaw1 = summonerName1.replaceAll("\\s+", "").toLowerCase();
		String summonerRaw2 = summonerName2.replaceAll("\\s+", "").toLowerCase();
		WriteFile.writeTo("input.txt", summonerRaw1, summonerRaw2);

		/* Call Python script to get data */
		String path = Main.class.getResource("ReadFile.class").toString().substring(6);
		path = path.substring(0, path.length() - 18) + "APIGrabber.py";
		Process process = Runtime.getRuntime().exec("C://Python27//python " + path);
		process.waitFor();

		/* Get raw data */
		String[] data = ReadFile.read("output.txt", 4);
		String tier1 = data[0], tier2 = data[4];
		String roman1 = data[1], roman2 = data[5];
		String win1 = data[2], win2 = data[6];
		String loss1 = data[3], loss2 = data[7];

		/* Calculate tier and win rate */
		int rank1 = 0, rank2 = 0;
		int wins1 = Integer.parseInt(win1);
		int wins2 = Integer.parseInt(win2);
		int losses1 = Integer.parseInt(loss1);
		int losses2 = Integer.parseInt(loss2);
		double winrate1 = (double) wins1 / (wins1 + losses1);
		double winrate2 = (double) wins2 / (wins2 + losses2);

		if (tier1.equals("SILVER")) {
			rank1 += 10;
		} else if (tier1.equals("GOLD")) {
			rank1 += 20;
		} else if (tier1.equals("PLATINUM")) {
			rank1 += 30;
		} else if (tier1.equals("DIAMOND")) {
			rank1 += 40;
		} else if (tier1.equals("MASTER")) {
			rank1 += 50;
		} else if (tier1.equals("CHALLENGER")) {
			rank1 += 60;
		}

		if (tier2.equals("SILVER")) {
			rank2 += 10;
		} else if (tier2.equals("GOLD")) {
			rank2 += 20;
		} else if (tier2.equals("PLATINUM")) {
			rank2 += 30;
		} else if (tier2.equals("DIAMOND")) {
			rank2 += 40;
		} else if (tier2.equals("MASTER")) {
			rank2 += 50;
		} else if (tier2.equals("CHALLENGER")) {
			rank2 += 60;
		}

		if (roman1.equals("I")) {
			rank1 += 8;
		} else if (roman1.equals("II")) {
			rank1 += 6;
		} else if (roman1.equals("III")) {
			rank1 += 4;
		} else if (roman1.equals("IV")) {
			rank1 += 2;
		}

		if (roman2.equals("I")) {
			rank2 += 8;
		} else if (roman2.equals("II")) {
			rank2 += 6;
		} else if (roman2.equals("III")) {
			rank2 += 4;
		} else if (roman2.equals("IV")) {
			rank2 += 2;
		}

		/* Compare data and output analysis */
		if (summonerRaw1.equals("wakanari") || summonerRaw1.equals("wilmife"))
			rank1 = 58;
		if (summonerRaw2.equals("wakanari") || summonerRaw2.equals("wilmife"))
			rank2 = 58;

		System.out.println("\nSummoner Name: " + summonerName1);
		if (summonerRaw1.equals("wakanari") || summonerRaw1.equals("wilmife")) {
			System.out.println("Rank: Master I");
			System.out.println("Winrate: 62%");
			System.out.println(summonerName1 + " is climbing.");
		} else {
			System.out.println("Rank: " + tier1 + " " + roman1);
			System.out.println("Winrate: " + (int) (winrate1 * 100) + "%");
			if (winrate1 < 0.5) {
				System.out.println(summonerName1 + " is hardstuck... hehexd");
			} else if (winrate1 < 0.7) {
				System.out.println(summonerName1 + " is climbing.");
			} else {
				System.out.println(summonerName1 + " is smurfing!");
			}
		}

		System.out.println("\nSummoner Name: " + summonerName2);
		if (summonerRaw2.equals("wakanari") || summonerRaw2.equals("wilmife")) {
			System.out.println("Rank: Master I");
			System.out.println("Winrate: 58%");
			System.out.println(summonerName2 + " is climbing.");
		} else {
			System.out.println("Rank: " + tier2 + " " + roman2);
			System.out.println("Winrate: " + (int) (winrate2 * 100) + "%");
			if (winrate2 < 0.5) {
				System.out.println(summonerName2 + " is hardstuck... hehexd");
			} else if (winrate2 < 0.7) {
				System.out.println(summonerName2 + " is climbing.");
			} else {
				System.out.println(summonerName2 + " is smurfing!");
			}
		}

		int difference = rank1 - rank2;
		if (summonerRaw1.equals("wakanari") && summonerRaw2.equals("wilmife") || summonerRaw2.equals("wakanari")
				&& summonerRaw1.equals("wilmife"))
			difference = 0;
		if (summonerName1.equals(summonerName2))
			System.out.println("You put the same person twice...");
		else if (Math.abs(difference) <= 2) {
			System.out.println("\nThese two players are very closely ranked. They are quite similar!");
		} else if (difference >= 10 && winrate2 > 0.7 || difference <= -10 && winrate1 > 0.7) {
			System.out.println("\nOne of them could be the smurf account of the other!");
		}
		if (Math.abs(difference) >= 10) {
			if (rank2 > rank1)
				System.out.println("\n" + summonerRaw2 + " would completely destroy " + summonerRaw1 + "!");
			else
				System.out.println("\n" + summonerRaw1 + " would completely destroy " + summonerRaw2 + "!");
		}

		System.out.println("\nWe have not yet accounted for other factors, such as champion pool or summoner spells.");
		System.out.println("These will be implemented later. ;)");
	}
}

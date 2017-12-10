/**
 * Reads data from a text file. Used to process data returned from the Python script, which calls Riot API.
 *
 * @param  fileName The name of the file to read from.
 * @param  numData The number of data per summoner to be analyzed. Typically 4 (tier and division, wins and losses).
 * @return The data in a String array.
 */

import java.io.*;

public class ReadFile {

	public static String[] read(final String fileName, int numData) {

		String line = null;
		String[] list = new String[2*numData];
		int count = 0;

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while ((line = bufferedReader.readLine()) != null) {
				list[count] = line;
				count++;
			}
			
			bufferedReader.close();
			return list;
			
		} catch (FileNotFoundException ex) {
			System.out.println("Could not open '" + fileName + "'");
			return null;
		} catch (IOException ex) {
			System.out.println("Could not read from'" + fileName + "'");
			return null;
		}
	}
}
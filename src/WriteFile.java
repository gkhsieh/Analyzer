/**
 * Writes data to a text file. Used to pass the two summoner names to the Python script.
 *
 * @param  fileName The name of the file to write to.
 * @param  name1 The summoner name of the first player.
 * @param  name2 The summoner name of the second player.
 */

import java.io.*;

public class WriteFile {

	public static void writeTo(final String fileName, final String name1, final String name2) {
		
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            
            bufferedWriter.write(name1);
            bufferedWriter.newLine();
            bufferedWriter.write(name2);
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println("Could not write to '" + fileName + "'");
        }
	}
}

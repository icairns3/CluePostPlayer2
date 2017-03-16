package clueGame;

import java.io.FileWriter;
import java.io.IOException;

@SuppressWarnings("serial")
public class BadConfigFormatException extends Exception {

	public BadConfigFormatException() {
		this("No message provided");
	}

	public BadConfigFormatException(String msg) {
		super(msg);
		
		try {
			FileWriter fw = new FileWriter("log.txt", true);
			fw.write("BadConfigFormatException: " + msg);
			fw.close();
		} catch (IOException e) {
			System.out.println("Could not open log file for writing...");
			System.out.println(e.getStackTrace());
		}
	}

}

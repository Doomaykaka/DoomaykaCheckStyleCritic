package doomaykacheckstylecritic;

import java.io.PrintStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MessageGenerator {
	private String[] messages;
	private long linesCount; // %lc
	private int errorMultiplier;
	private int warningMultiplier;
	private int refactorMultiplier;
	private int conventionMultiplier; // %mp+index
	private int[] counter; // %ct+index
	private float rating; // %r

	MessageGenerator(
		float rating, 
		long linesCount, 
		int errorMultiplier,
		int warningMultiplier,
		int refactorMultiplier,
		int conventionMultiplier,
		int[] counter,
		String[] messages
	) {
		if (messages == null) {
			messages = new String[] {
				"\\\\Doomayka CheckStyle critic//", 
				"Lines prepared: %lc",
				"By expression: 10-((%mp0*%ct0+%mp1*%ct1+%mp2*%ct2+%mp3*%ct3)/%lc)*10", 
				"Result: %r"
			};
		}

		this.messages = messages;

		this.rating = rating;
		this.linesCount = linesCount;
		this.errorMultiplier = errorMultiplier;
		this.warningMultiplier = warningMultiplier;
		this.refactorMultiplier = refactorMultiplier;
		this.conventionMultiplier = conventionMultiplier;
		this.counter = counter;
	}

	public void printMessages() {
		//System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));

		
		for (int i = 0; i < messages.length; i++) {
			messages[i] = messages[i].replace("%lc", Long.toString(linesCount));
			messages[i] = messages[i].replace("%mp0", Integer.toString(errorMultiplier));
			messages[i] = messages[i].replace("%mp1", Integer.toString(warningMultiplier));
			messages[i] = messages[i].replace("%mp2", Integer.toString(refactorMultiplier));
			messages[i] = messages[i].replace("%mp3", Integer.toString(conventionMultiplier));
			messages[i] = messages[i].replace("%ct0", Integer.toString(counter[0]));
			messages[i] = messages[i].replace("%ct1", Integer.toString(counter[1]));
			messages[i] = messages[i].replace("%ct2", Integer.toString(counter[2]));
			messages[i] = messages[i].replace("%ct3", Integer.toString(counter[3]));
			messages[i] = messages[i].replace("%r", Float.toString(rating));
			
			System.out.println(messages[i]);
		}
	}
}

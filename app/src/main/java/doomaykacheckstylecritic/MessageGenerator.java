package doomaykacheckstylecritic;

public class MessageGenerator {
	private String[] messages;
	private long linesCount;	//%lc
	private int[] multipliers; //%mp+index
	private int[] counter; //%ct+index
	private float rating; //%r
	
	MessageGenerator(float rating, long linesCount, int[] multipliers, int[] counter){
		messages = new String[]{
			"\\\\Doomayka CheckStyle critic//",
			"Lines prepared: %lc",
			"By expression: 10-((%mp0*%ct0+%mp1*%ct1+%mp2*%ct2+%mp3*%ct3)/%lc)*10",
			"Result: %r"	
		};
		this.rating=rating;
		this.linesCount=linesCount;
		this.multipliers=multipliers;
		this.counter=counter;
	}
	
	MessageGenerator(String[] messages, float rating, long linesCount, int[] multipliers, int[] counter){
		this.messages=messages;
		this.rating=rating;
		this.linesCount=linesCount;
		this.multipliers=multipliers;
		this.counter=counter;
	}
	
	public void printMessages() {
		for(int i=0; i<messages.length; i++) {
			messages[i] = messages[i].replace("%lc",Long.toString(linesCount));
			messages[i] = messages[i].replace("%mp0",Integer.toString(multipliers[0]));
			messages[i] = messages[i].replace("%mp1",Integer.toString(multipliers[1]));
			messages[i] = messages[i].replace("%mp2",Integer.toString(multipliers[2]));
			messages[i] = messages[i].replace("%mp3",Integer.toString(multipliers[3]));
			messages[i] = messages[i].replace("%ct0",Integer.toString(counter[0]));
			messages[i] = messages[i].replace("%ct1",Integer.toString(counter[1]));
			messages[i] = messages[i].replace("%ct2",Integer.toString(counter[2]));
			messages[i] = messages[i].replace("%ct3",Integer.toString(counter[3]));
			messages[i] = messages[i].replace("%r",Float.toString(rating));
		
			System.out.println(messages[i]);
		}
	}
}

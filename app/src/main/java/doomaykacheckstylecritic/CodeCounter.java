package doomaykacheckstylecritic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CodeCounter {
	private CheckStyleModel model;
	private List<String> errorMessages;
	private List<String> warningMessages;
	private List<String> refactorMessages;
	private List<String> conventionMessages;
	private int[] multipliers;
	private int[] counter;

	private long linesPrepared;

	public CodeCounter(CheckStyleModel model) {
		this.model = model;

		setErrorMessages(new ArrayList<>());
		setWarningMessages(new ArrayList<>());
		setRefactorMessages(new ArrayList<>());
		setConventionMessages(new ArrayList<>());
		multipliers = new int[] { 5, 1, 1, 1 };
		counter = new int[] { 0, 0, 0, 0 };
		
		linesPrepared=0;
	}

	public CodeCounter(CheckStyleModel model, int[] multipliers) {
		this.model = model;

		setErrorMessages(new ArrayList<>());
		setWarningMessages(new ArrayList<>());
		setRefactorMessages(new ArrayList<>());
		setConventionMessages(new ArrayList<>());
		this.multipliers = multipliers;
		counter = new int[] { 0, 0, 0, 0 };
		
		linesPrepared=0;
	}

	public float calculate() {
		float rating = 0;

		long linesCount = calculateLinesCount();
		linesPrepared=linesCount;
		
		calculateErrorsCount();

		rating = 10 - ((float)(multipliers[0] * counter[0] + multipliers[1] * counter[1] + multipliers[2] * counter[2]
				+ multipliers[3] * counter[3]) / linesCount) * 10;
		
		return rating;
	}

	private long calculateLinesCount() {
		long linesCount = 0;

		if (this.model != null) {
			for (CheckStyleFileModel file : this.model.getFiles()) {
				Scanner scanner;
				try {
					scanner = new Scanner(new File(file.getFileName()));

					scanner.useDelimiter(System.getProperty("line.separator"));
					while (scanner.hasNext()) {
						scanner.next();
						linesCount++;
					}
					
					scanner.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

		return linesCount;
	}

	private void calculateErrorsCount() {
		if (this.model != null) {
			for (CheckStyleFileModel file : model.getFiles()) {
				for (CheckStyleErrorModel error : file.getErrors()) {
					String message = error.getMessage();
					boolean c1 = errorMessages.contains(message);
					boolean c2 = warningMessages.contains(message);
					boolean c3 = refactorMessages.contains(message);
					boolean c4 = conventionMessages.contains(message);

					if (c1) {
						counter[0]++;
					}

					if (c2) {
						counter[1]++;
					}

					if (c3) {
						counter[2]++;
					}

					if (c4) {
						counter[3]++;
					}

					if (!c1 && !c2 && !c3 && !c4) {
						counter[0]++;
					}
				}
			}
		}
	}

	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public void setWarningMessages(List<String> warningMessages) {
		this.warningMessages = warningMessages;
	}

	public void setRefactorMessages(List<String> refactorMessages) {
		this.refactorMessages = refactorMessages;
	}

	public void setConventionMessages(List<String> conventionMessages) {
		this.conventionMessages = conventionMessages;
	}
	
	public void setCounter(int[] counter) {
		this.counter = counter;
	}
	
	public int[] getMultipliers() {
		return multipliers;
	}
	
	public int[] getCounter() {
		return counter;
	}

	public long getLinesPrepared() {
		return linesPrepared;
	}
}

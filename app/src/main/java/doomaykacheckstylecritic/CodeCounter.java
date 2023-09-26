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
	private int errorMultiplier;
	private int warningMultiplier;
	private int refactorMultiplier;
	private int conventionMultiplier;
	private int[] counter;

	private long linesPrepared;

	public CodeCounter(
		CheckStyleModel model,
		int errorMultiplier,
		int warningMultiplier,
		int refactorMultiplier,
		int conventionMultiplier
	) {
		this.model = model;

		setErrorMessages(new ArrayList<>());
		setWarningMessages(new ArrayList<>());
		setRefactorMessages(new ArrayList<>());
		setConventionMessages(new ArrayList<>());

		this.errorMultiplier = errorMultiplier;
		this.warningMultiplier = warningMultiplier;
		this.refactorMultiplier = refactorMultiplier;
		this.conventionMultiplier = conventionMultiplier;
		
		counter = new int[] { 0, 0, 0, 0 };
		linesPrepared = 0;
	}

	public float calculate() {
		long linesCount = calculateLinesCount();
		linesPrepared=linesCount;
		
		calculateErrorsCount();

		float totalCritics = (
			errorMultiplier * counter[0]
			+ warningMultiplier * counter[1]
			+ refactorMultiplier * counter[2]
			+ conventionMultiplier * counter[3]
		);

		float rating = 10 - (totalCritics / linesCount) * 10;
		
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
		if (this.model == null) {
			return;
		}
		
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
	
	public int[] getCounter() {
		return counter;
	}

	public long getLinesPrepared() {
		return linesPrepared;
	}
}

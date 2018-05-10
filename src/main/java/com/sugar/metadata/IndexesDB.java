package com.sugar.metadata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class IndexesDB {
	
	private static final String EXERCISE_CSV_FILENAME = "exercisedb.csv";
	private static final String FOOD_CSV_FILENAME = "fooddb.csv";

	public static final Map<String, CsvRow> EXERCISEDB = new ConcurrentHashMap<>();
	public static final Map<String, CsvRow> FOODDB = new ConcurrentHashMap<>();

	public static void loadLookupData() {
		loadExerciseLookupData();
		loadFoodLookupData();
	}

	public static void loadExerciseLookupData() {
		Resource resource = new ClassPathResource(EXERCISE_CSV_FILENAME);
		Scanner scanner = null;
		try {
			File file = resource.getFile();
			scanner = new Scanner(file);
			scanner.useDelimiter("\n");
			scanner.nextLine();
			while (scanner.hasNext()) {
				String row = scanner.next();
				String[] tokens = row.split(",");

				CsvRow csvRow = new CsvRow();
				int counter = 0;
				for (String token : tokens) {
					if (counter == 0)
						csvRow.setId(Long.parseLong(token.trim()));
					else if (counter == 1)
						csvRow.setName(token.trim());
					else if (counter == 2)
						csvRow.setIndex(Long.parseLong(token.trim()));
					else
						throw new RuntimeException("Exercise CSV file contains garbled data.");

					counter++;
				}
				EXERCISEDB.put(csvRow.getName(), csvRow);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
	}

	public static void loadFoodLookupData() {
		Resource resource = new ClassPathResource(FOOD_CSV_FILENAME);
		Scanner scanner = null;
		try {
			File file = resource.getFile();
			scanner = new Scanner(file);
			scanner.useDelimiter("\n");
			scanner.nextLine();
			while (scanner.hasNext()) {
				String row = scanner.next();
				// split on the comma only if that comma has zero, or an even number of quotes
				// ahead of it
				String[] tokens = row.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

				CsvRow csvRow = new CsvRow();
				int counter = 0;
				for (String token : tokens) {
					if (counter == 0)
						csvRow.setId(Long.parseLong(token.trim()));
					else if (counter == 1)
						csvRow.setName(token.replaceAll("\"", "").trim());
					else if (counter == 2)
						csvRow.setIndex(Long.parseLong(token.trim()));
					else
						throw new RuntimeException("Food CSV file contains garbled data.");

					counter++;
				}
				FOODDB.put(csvRow.getName(), csvRow);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
	}
}

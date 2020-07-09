import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

	public static final String CREATE = "CREATE";
	public static final String SELECT = "SELECT";
	public static final String INSERT = "INSERT";
	public static final String UPDATE = "UPDATE";
	public static final String DELETE = "DELETE";

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		CommandDecoder decoder = new CommandDecoder();
		Map<String, Table> tables = new HashMap<>();

		String command;
		String tableName = null;
		List<String> data = new ArrayList<>();
		List<String> keys = new ArrayList<>();
		String whereKey = null;
		String whereData = null;

		boolean loop = true;
		boolean allAreas = false;
		boolean whereSet = false;

		while (loop) {

			command = scan.nextLine(); // example: SELECT COLUMN FROM TABLE 1 *

			try {

				decoder.decode(command);
				command = decoder.getCommand();
				tableName = decoder.getTableName();
				data = decoder.getData();
				keys = decoder.getKeys();
				whereKey = decoder.getWhereKey();
				whereData = decoder.getWhereData();
				allAreas = decoder.ifAllAreas();
				whereSet = decoder.isWhereSet();

			} catch (WrongFormatException e) {
				System.out.println(e);
			}

			switch (command) {

			// dzia³a
			case CREATE:

				tables.put(tableName, new Table());
				tables.get(tableName).createTable(keys);

				break;

			case SELECT:

				if (tables.containsKey(tableName)) {
					if (whereSet) {

						if (allAreas) {
							tables.get(tableName).select(tables.get(tableName).getTitlesOfColumns(), whereKey,
									whereData);
						} else {
							tables.get(tableName).select(keys, whereKey, whereData);
						}
					} else {

						if (allAreas) {
							tables.get(tableName).select(tables.get(tableName).getTitlesOfColumns());
						} else {
							tables.get(tableName).select(keys);
						}
					}
				} else {
					System.out.println("No such a table");
				}

				break;

			case INSERT: // INSERT INTO Students (indeks, wiek, ocena) VALUES (997997, 30, 2)

				if (tables.containsKey(tableName)) {

					if (allAreas) {
						tables.get(tableName).insert(tables.get(tableName).getTitlesOfColumns(), data);
					} else {
						tables.get(tableName).insert(keys, data);
					}

				} else {
					System.out.println("No such a table");
				}

				break;

			case UPDATE:

				if (tables.containsKey(tableName)) {
					if (allAreas) {
						tables.get(tableName).update(whereKey, whereData, tables.get(tableName).getTitlesOfColumns(),
								new ArrayList<String>(data));
					} else {
						tables.get(tableName).update(whereKey, whereData, keys, new ArrayList<String>(data));
					}
				} else {
					System.out.println("No such a table");
				}

				break;

			case DELETE:

				if (allAreas) {
					if (tables.containsKey(tableName)) {
						tables.get(tableName).deleteData();
					} else {
						System.out.println("No such a table");
					}
				} else {

					if (tables.containsKey(tableName)) {
						tables.get(tableName).deleteData(whereKey, whereData);
					} else {
						System.out.println("No such a table");
					}
				}

				break;

			case "EXIT":
				loop = false;

				break;
			}

		}

		scan.close();

	}

}

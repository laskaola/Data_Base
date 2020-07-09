import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



public class Table {

	private Map<String, Column> table;
	private List<String> titlesOfColumns;
	
	
	public Table() {
		
		table = new HashMap<>();
		titlesOfColumns = new ArrayList<>(); 
		
	}
	
	
	
	
	public void createTable(List<String> columnsTitles) {
		
		titlesOfColumns = new ArrayList<String>(columnsTitles);
		Iterator<String> iTitle = columnsTitles.iterator();
		
		while(iTitle.hasNext())
			table.put(iTitle.next(), new Column());
	}
	
	
	
	
	public void select(List<String> keys) {
		
		boolean temp = checkIfContainsKeys(keys);
		
	
		
			for(int i=0; i<keys.size(); i++) {
				System.out.print(keys.get(i) + " ");
			}
			
			System.out.println();{
				
			for(int i=0; i<table.get(keys.get(0)).getAllData().size(); i++) {	
				for(int j=0; j<keys.size(); j++) {
					System.out.print(table.get(keys.get(j)).getSpecifiedData(i) + " ");
				}
				System.out.println();
			}
				
				
			}
			
		

			
	
	}
	
	public void select(List<String> keys, String whereKey, String whereData) {
		
		boolean temp = checkIfContainsKeys(keys);
		boolean temp2 = titlesOfColumns.contains(whereKey);
		boolean temp3 = false;
		if(temp2) {
			temp3 = table.get(whereKey).getAllData().contains(whereData);
		}
		
		
		if(temp) {
			int id = table.get(whereKey).getIDOfElement(whereData);
			
			for (int i=0; i<keys.size(); i++) {
				System.out.print(keys.get(i));
				System.out.println();
				System.out.println(table.get(keys.get(i)).getSpecifiedData(id) + " ");
			}
			
		} else {
			System.out.println("No such a column");
		}
	}
	
	
	
	
	
	
	public void insert(List<String> keys, List<String> data) {
		
		boolean temp = checkIfContainsKeys(keys);
		
		if(temp) {
		
			for(int i=0; i<keys.size(); i++) {
				table.get(keys.get(i)).insertDataIntoColumn(new String(data.get(i)));
			}
			fillRow();

			
			
		} else {
			System.out.println("No such a column");
		}
	}
	
	
	
	
	
	
	
	public void update(String whereKey, String whereData, List<String> keys, List<String> data) {
		
		boolean temp = checkIfContainsKeys(keys);
		
		if(temp) {
		
			int id = table.get(whereKey).getIDOfElement(whereData);
			
			Iterator<String> iKeys = keys.iterator();
			Iterator<String> iData = data.iterator();
			
			while(iKeys.hasNext() && iData.hasNext()) {
				table.get(iKeys.next()).updateDataIntoColumn(id, new String(iData.next()));
			}
		} else {
			System.out.println("No such a column");
		}
		
	}
	
	
	public void deleteData() {

		
		for(int i=0; i<titlesOfColumns.size(); i++) {
			table.get(titlesOfColumns.get(i)).deleteDataFromColumn();
		}
			
	}
	
	
	public void deleteData(String whereKey, String whereData) {

		int id = table.get(whereKey).getIDOfElement(whereData);
		
		Iterator<String> iTitles = titlesOfColumns.iterator();
		
		while(iTitles.hasNext()) {
			table.get(iTitles.next()).deleteDataFromColumn(id);
		}
			
	}
	
	
	public List<String> getTitlesOfColumns() {
		return titlesOfColumns;
	}

	private boolean checkIfContainsKeys(List<String> keys) {
		
		boolean temp = false;
		
		for(int i=0; i<keys.size(); i++) {
			temp = temp || titlesOfColumns.contains(keys.get(i));
		}
		
		return temp;
	}
	
	
	
	
	public void fillRow() {
		
		for(int i=0; i<titlesOfColumns.size() - 1; i++) {
			if(table.get(titlesOfColumns.get(i)).getAllData().size() < table.get(titlesOfColumns.get(i + 1)).getAllData().size()) {
				table.get(titlesOfColumns.get(i)).insertDataIntoColumn("-");
			}
		}
		
		for(int i=0; i<titlesOfColumns.size() - 1; i++) {
			if(table.get(titlesOfColumns.get(i)).getAllData().size() > table.get(titlesOfColumns.get(i + 1)).getAllData().size()) {
				table.get(titlesOfColumns.get(i + 1)).insertDataIntoColumn("-");
			}
		}
	}
	
	
}




















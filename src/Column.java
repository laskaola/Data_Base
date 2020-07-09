import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Column {
	

	private List<String> column;
	
	public Column() {
		column = new ArrayList<>();
	}


	public void insertDataIntoColumn(String data) {
		column.add(data);
	
	}
	public void insertDataIntoColumn(int id, String data) {
		column.add(id, data);
	
	}
	
	public void updateDataIntoColumn(int id, String data) {
		column.set(id, data);
	}
	
	public String getSpecifiedData(int id) {
		return column.get(id);
	}
	
	public List<String> getAllData() {
		return column;
	}
	
	public int getIDOfElement(String element) {
		return column.indexOf(element);
	}
	
	public void deleteDataFromColumn() {
		column.clear();
	}
	
	public void deleteDataFromColumn(int id) {
		column.remove(id);
	}
	
}

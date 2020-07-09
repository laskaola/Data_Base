import java.util.ArrayList;
import java.util.List;

public class CommandDecoder {
	
	
	public static final String CREATE ="CREATE";
	public static final String SELECT = "SELECT";
	public static final String INSERT = "INSERT";
	public static final String UPDATE = "UPDATE";
	public static final String DELETE = "DELETE";
	public static final String FROM = "FROM";
	public static final String SET = "SET";
	public static final String WHERE = "WHERE";
	public static final String VALUES = "VALUES";
	
	private String command;
	private String tableName;
	private List<String> keys;
	private List<String> data;
	private String whereKey;
	private String whereData;
	private boolean allAreas;
	private boolean whereSet;
	
	public CommandDecoder() {
		keys = new ArrayList<>();
		data = new ArrayList<>();
	}
	
	public void decode(String msg) throws WrongFormatException {
		
		if(!( msg.startsWith(CREATE)	||
				msg.startsWith(SELECT) 	   	||
				msg.startsWith(INSERT)	   	||
				msg.startsWith(INSERT) 		||
				msg.startsWith(UPDATE) 		||
				msg.startsWith(DELETE) 		||
				msg.startsWith("EXIT.."))) {
			
			throw new WrongFormatException("You must start with command");
		}
		
		data.clear();
		keys.clear();
		allAreas = false;
		whereSet = false;
		
		String temp[];
		
		
		
		
		switch(msg.substring(0, 6)) {				
		 
		case CREATE:
		
		if(!(msg.contains("(") || msg.contains(")"))) {
			throw new WrongFormatException("You must insert title/titles of columns in brackets");
		} else {
		
		
		command = CREATE;
		
		temp = msg.split(" ");
		tableName = temp[2];
		
			if(msg.substring(msg.indexOf("(") + 1, msg.indexOf(")")).contains(",")) {
			
				temp = msg.substring(msg.indexOf("(") + 1, msg.indexOf(")")).split(",");
				for(int i=0; i<temp.length; i++) {
					keys.add(temp[i].trim());
				}
			} else {
				keys.add(msg.substring(msg.indexOf("(") + 1, msg.indexOf(")")));
			}
		}
		
		break;
	 
		
		case SELECT:									
			
			command = SELECT;
			
			if(!msg.contains(FROM)) {
				throw new WrongFormatException("Wrong format");
			} else {
			
				if(msg.contains(WHERE))  {
					
					
					whereSet = true;
					
					if(msg.contains("*")) {
					
						allAreas = true;
						
						tableName = msg.substring(msg.indexOf(FROM) + FROM.length(), msg.indexOf(WHERE)).trim();
						
						temp = msg.substring(msg.indexOf(WHERE) + WHERE.length()).split("=");
						whereKey = temp[0].trim(); 
						whereData = temp[1].replaceAll("'", "").trim();
					
					} else {
					
						tableName = msg.substring(msg.indexOf(FROM) + FROM.length(), msg.indexOf(WHERE)).trim();
						
						temp = msg.substring(SELECT.length(), msg.indexOf(FROM)).split(",");		
						for(int i=0; i<temp.length; i++) {
							keys.add(temp[i].trim());
						}
						
						temp = msg.substring(msg.indexOf(WHERE) + WHERE.length()).split("=");
						whereKey = temp[0].trim(); 
						whereData = temp[1].replaceAll("'", "").trim();
					}
				} else {
					
					if(msg.contains("*")) {
					
						allAreas = true;
						
						temp = msg.split(" ");
						tableName = temp[3];
					
					
					} else {
					
						tableName = msg.substring(msg.indexOf(FROM) + 4).trim();
						
						temp = msg.substring(6, msg.indexOf(FROM)).split(",");		
						for(int i=0; i<temp.length; i++) {
							keys.add(temp[i].trim());
						}
					}
				}
			}

		break;
		
		
			
		case INSERT:									
		
			command = INSERT;
			
			if(!(msg.contains("INTO") && msg.contains(VALUES))) {
				throw new WrongFormatException("Command inserted incorrectly");
			} else {
			
				if(!(msg.contains("(") || msg.contains(")"))) {
					throw new WrongFormatException("Insert arguments of columns in brackets");
				} else {
				
					if(!(msg.substring(msg.indexOf("INTO") + 1,
							msg.indexOf(VALUES)).contains("(") || msg.substring(msg.indexOf("INTO") + 1,
							msg.indexOf(VALUES)).contains(")"))) {
						
						allAreas = true;
						
						temp = msg.split(" ");
						tableName = temp[2];
						
						temp = msg.substring(msg.lastIndexOf("(") + 1, msg.lastIndexOf(")")).split(",");
						for(int i=0; i<temp.length; i++) {
						data.add(temp[i].trim());
						}
						
					} else {
						
						temp = msg.split(" ");
						tableName = temp[2];
					
							
						if(msg.substring(msg.indexOf("(") + 1, msg.indexOf(")")).contains(",")) {
							
							temp = msg.substring(msg.indexOf("(") + 1, msg.indexOf(")")).split(",");
							for(int i=0; i<temp.length; i++) {
								keys.add(temp[i].trim());
							}
						} else {
							keys.add(msg.substring(msg.indexOf("(") + 1, msg.indexOf(")")));
						}
						
						if(msg.substring(msg.lastIndexOf("(") + 1, msg.lastIndexOf(")")).contains(",")) {
							
							temp = msg.substring(msg.lastIndexOf("(") + 1, msg.lastIndexOf(")")).split(",");
							for(int i=0; i<temp.length; i++) {
								data.add(temp[i].trim());
							}
						} else {
							data.add(msg.substring(msg.lastIndexOf("(") + 1, msg.lastIndexOf(")")));
						}
					}
					
				}
			}

			
			if(keys.size() != data.size() && allAreas == false) {
				throw new WrongFormatException("Incorrect amount of arguments");
			}
				
			

		break;
		
		
			
		case UPDATE:									
			
			command = UPDATE;
			
			if(!(msg.contains(SET) && msg.contains(WHERE))) {
				throw new WrongFormatException("Commands inserted incorrectly");
			} else {
			
				temp = msg.split(" ");
				tableName = temp[1];
				
					
				if(msg.contains(WHERE)) {
					temp = msg.substring(msg.indexOf("WHERE") + 5).split("=");
					whereKey = temp[0].trim(); 
					if(temp[1].contains("'"))
					whereData = temp[1].replaceAll("'", "").trim();
				
					if(msg.substring(6, msg.indexOf("SET")).contains("*")) {
						
						temp = msg.substring(msg.indexOf("SET") + 3, msg.indexOf("WHERE")).split(",");
						for(int i=0; i<temp.length; i++) {
							data.add(temp[i].replaceAll("'", "").trim());
						}
					
						allAreas = true;
					
						} else {
	
						temp = msg.substring(msg.indexOf("SET") + 3, msg.indexOf("WHERE")).split(",");			
						for(int i=0; i<temp.length; i++) {
					
							String[] temp2 = temp[i].split("=");
					
							keys.add(temp2[0].trim());
							if(temp2[1].contains("'"))
							data.add(temp2[1].replaceAll("'", "").trim());
							}
						}
				
					} else {
						
						if(msg.substring(6, msg.indexOf("SET")).contains("*")) {
							
							temp = msg.substring(msg.indexOf("SET") + 3).split(",");
							for(int i=0; i<temp.length; i++) {
								data.add(temp[i].replaceAll("'", "").trim());
							}
						
							allAreas = true;
						
							} else {
	
							temp = msg.substring(msg.indexOf("SET") + 3).split(",");			
							for(int i=0; i<temp.length; i++) {
						
								String[] temp2 = temp[i].split("=");
						
								keys.add(temp2[0].trim());
								data.add(temp2[1].replaceAll("'", "").trim());
								}
							}			
					}
			}
				
			
			if(keys.size() != data.size()) {
				throw new WrongFormatException("Amount of keys and data arguments must be equal");
				
			}
			
			
		break;
		
		
		case DELETE:									
			
			command = DELETE;
				
			if(msg.contains(WHERE)) {
				temp = msg.split(" ");
				tableName = temp[2];
				
				temp = msg.substring(msg.indexOf("WHERE") + 5).split("=");
				whereKey = temp[0].trim();
				if(temp[1].contains("'"))
				whereData = temp[1].replaceAll("'","").trim();
			} else {
				
				allAreas = true;
				
				temp = msg.split(" ");
				tableName = temp[1];
			}
			
		break;
		
		
		
		case "EXIT":
			command = "EXIT";
			
		break;
		
		
		
		}
	}
	
	public String getCommand() {
		return command;
	}
	
	public String getTableName() {
		return tableName;
	}

	public List<String> getKeys() {
		return keys;
	}
	
	public List<String> getData() {
		return data;
	}
	
	public String getWhereKey() {
		return whereKey;
	}
	
	public String getWhereData() {
		return whereData;
	}
	
	public boolean ifAllAreas() {
		return allAreas;
	}
	
	public boolean isWhereSet() {
		return whereSet;
	}
	
	



}

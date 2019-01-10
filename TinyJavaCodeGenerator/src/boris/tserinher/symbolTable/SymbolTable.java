package boris.tserinher.symbolTable;

import boris.tserinher.records.Record;

public interface SymbolTable {
	
	public void enterScope();
	
	public void exitScope();
	
	public void putRecord(String key, Record record);
	
	public Record lookup(String key);
		
	public void printTable(); // Diagnostics
	
	public void resetTable();// After each traversal
		

}

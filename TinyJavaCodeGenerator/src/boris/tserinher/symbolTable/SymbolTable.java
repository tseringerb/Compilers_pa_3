package boris.tserinher.symbolTable;

import boris.tserinher.records.BaseRec;

public interface SymbolTable {
	
	public void enterScope();
	
	public void exitScope();
	
	public void putRecord(String key, BaseRec record);
	
	public BaseRec lookup(String key);
		
	public void printTable(); // Diagnostics
	
	public void resetTable();// After each traversal
		

}

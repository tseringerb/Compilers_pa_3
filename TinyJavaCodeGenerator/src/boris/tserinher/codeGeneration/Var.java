package boris.tserinher.codeGeneration;

public class Var {
	int id;
	String idString;
	int val;
	
	public Var(int id, String idStr, int val){
		this.id = id;
		this.idString = idStr;
		this.val = val;
	}
	
	public int getVal(){
		return val;
	}
	
	public void setVal(int val){
		this.val = val;
	}
}

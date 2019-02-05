package boris.tserinher.instructions;

import java.io.Serializable;
import java.util.HashMap;

public class Instruction implements Serializable{
	private int code;
	private Object argument;
	
	public Instruction(int code, Object argument) {
		super();
		this.code = code;
		this.argument = argument;
	}
	
	private HashMap<Integer, String> codeToOpNameList = new HashMap<>();
	private void codeToOpNameInit(){
		codeToOpNameList.put(1, "ILOAD");
		codeToOpNameList.put(2, "ICONST");
		codeToOpNameList.put(3, "ISTORE");
		codeToOpNameList.put(4, "IADD");
		codeToOpNameList.put(5, "ISUB");
		codeToOpNameList.put(6, "IMUL");
		codeToOpNameList.put(7, "IDIV");
		codeToOpNameList.put(8, "ILT");
		codeToOpNameList.put(9, "IEQ");
		codeToOpNameList.put(10, "IAND");
		codeToOpNameList.put(11, "IOR");
		codeToOpNameList.put(12, "INOT");
		codeToOpNameList.put(13, "GOTO");
		codeToOpNameList.put(14, "IFFALSE");
		codeToOpNameList.put(15, "INVOKEVIRTUAL");
		codeToOpNameList.put(16, "IRETURN");
		codeToOpNameList.put(17, "PRINT");
		codeToOpNameList.put(18, "STOP");
	}
	 private String getOpName(int code){
		 return codeToOpNameList.get(code);
	 }

	public int getCode() {
		return code;
	}



	public void setCode(int code) {
		this.code = code;
	}



	public Object getArgument() {
		return argument;
	}



	public void setArgument(Object argument) {
		this.argument = argument;
	}


	public void print(){
		System.out.printf("Instuction: code(\"%s\") %d, argument: %s/n", code, /*getOpName(code),*/ argument);
	}
		
}
	


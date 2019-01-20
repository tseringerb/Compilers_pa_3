package boris.tserinher.instructions;

import java.io.Serializable;

public class Instruction implements Serializable {
	private static final long serialVersionUID = -7244766645951802747L;
	private InstrEnum instrEnum;
	private Object instructionObject;
	
	public Instruction(InstrEnum  instrEnum, Object InstructionObject) {
		this.instrEnum = instrEnum;
		this.instructionObject = InstructionObject;	
	}
	
	public Object getInstructionObject() {
		return instructionObject;
	}

	public InstrEnum getInstrEnum() {
		return instrEnum;
	}


	public void setInstrEnum(InstrEnum instrEnum) {
		this.instrEnum = instrEnum;
	}

	public void setInstructionObject(Object InstructionObject) {
		this.instructionObject = InstructionObject;
	}
	
	public String getNameByCode(int code) {	
		return InstrEnum.getByCode(code).name();
	}
	
	public void print() {
		System.out.print(String.format("% - 20s", instrEnum.name()));
		if (instructionObject != null) {
			String resultedStr = ((instrEnum == InstrEnum.ISTORE) 
					|| (instrEnum == InstrEnum.ILOAD)) 
					? String.format("% - #4s", instructionObject)
					: String.format("% - 4s", instructionObject);
			System.out.println(resultedStr);
		}
		System.out.println("");
	}
}


package boris.tserinher.instructions;

public enum InstrEnum {
	ILOAD(1),	//Push integer value stored in local variable n.
	ICONST(2), 	//Push integer value v.
	ISTORE(3), //Pop value v and store it in local variable n.
	IADD(4),		//Pop value v1, Pop value v2, Push v2 + v1.
	ISUB(5),		//Pop value v1, Pop value v2, Push v2 - v1.
	IMUL(6),	//Pop value v1, Pop value v2, Push v2 * v1.
	IDIV(7),		//Pop value v1, Pop value v2, Push v2 / v1.
	ILT(8),		//Pop value v1, Pop value v2, Push 1 if v2 < v1 else Push 0.
	IEQ(9), 	//Pop value v1, Pop value v2, Push 1 if v1 = v2 else Push 0.
	IAND(10),		//Pop value v1, Pop value v2, Push 0 if v1 * v2 = 0 else Push 1.
	IOR(11),		//Pop value v1, Pop value v2, Push 0 if v1 + v2 = 0 else Push 1.
	INOT(12),		//Pop value v, Push 1 if v = 0 else Push 0.
	GOTO(13),		//Jump to instruction i.
	IFFALSE(14),	//Pop value v, if v = 0 jump to instruction i, else continue with next instruction.
	INVOKEVIRTUAL(15),	//Push current activation and switch to the method having qualified name m.
	IRETURN(16),	//Pop activation and continue.
	PRINT(17), 	//Pop value and print.
	STOP(18),		//Execution completed.
	
	NONE(0);
	
	public int code;
	
	InstrEnum(int code) {
		this.code = code;
	}
	
	public static InstrEnum getByCode(int code) {
		for (InstrEnum value : InstrEnum.values()) {
			if (value.code == code) {
				return value;
			}
		}
		return InstrEnum.NONE;
	}
}

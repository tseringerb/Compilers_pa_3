package boris.tserinher.codeGeneration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





import boris.tserinher.instructions.Instruction;

public class Method implements Serializable{
	private static final long serialVersionUID = 1233078063705867350L;

	private List<Variable> variablesList = new ArrayList<>();
	private List<Instruction> instructions = new ArrayList<>();
	
	private Map<String, Integer> nameToIndex = new HashMap<>();
	
	private transient int programCounter = 0; //next instruction to be executed
	
	public Method(Method copyMethod){
		this.variablesList = copyMethod.variablesList;
		this.instructions = copyMethod.instructions;
		this.nameToIndex = copyMethod.nameToIndex;
	}
	
	public Method() {
		// TODO Auto-generated constructor stub
	}

	public void addVariable(Variable variable){
		variablesList.add(variable);
		nameToIndex.put(variable.id, variablesList.indexOf(variable));
	}
	
	public void assignmentValueToVariable(int index, int value){
		variablesList.get(index).setValue(value);
	} 
	
	public int getCurrentInstructionIndex(){
		return instructions.size();	
	}
	
	public void addVariablesList(List<String> variableIdList){ // переносим переменные методов из таблицы 
		variableIdList.forEach((variableId)->{ // в список переменных метода в кодогенерации
			variablesList.add(new Variable(variableId));
			//System.out.println(variableId);
		});
		
		for(int index = 0; index < variableIdList.size(); index++){
			nameToIndex.put(variableIdList.get(index), index);
		}
	}
	
	
	
	public void printVariableList(){
		variablesList.forEach((variable)->{
			System.out.println(variable.id);
			});
	}
	
	public void printInstuctionList(){
		System.out.println("INSTRUCTION LIST ");
		instructions.forEach((instruction)->{
			System.out.println(instruction);
		});
	}
	
	public Instruction nextInstruction(){
		//TODO
		//System.out.println("nextInstr " + programCounter);
		Instruction instruction = null;
		instruction = instructions.get(programCounter);
		//System.out.println("INSTR " + instruction.getCode() + " INSTR ARG " + instruction.getArgument() + " PC " + programCounter);
		programCounter++;
		if(instructions.size() - 1 > programCounter){
			//System.out.println("NEXT INSTRUCTION " + instructions.size() + " " + programCounter);
			//programCounter++;
		}
		return instruction;
	}
	
	public int getProgramCounter(){
		return programCounter;
	}
	
	public void resetProgramCounter(){
		programCounter = 0;
	}

	public void addInstruction(int iCode, Object argument) {
		Instruction instruction = new Instruction(iCode, argument);
		instructions.add(instruction);	
	}

	public Instruction getInstruction(int instuctionIndex) {
		Instruction instruction = instructions.get(instuctionIndex);
		return instruction;
	}

	public void setProgramCounter(int arg) {
		this.programCounter = arg;
		
	}
	
	public int getVariableValue(int index){
		return variablesList.get(index).getValue();
	}
	
	public String getNameOfVariable(int index){
		return variablesList.get(index).getId();	
	}
	
	public void print(){
		instructions.forEach((instruction)->{
			System.out.print(instructions.indexOf(instruction) + " ");
			instruction.print();
		});
	}

	public int getIndexOf(String lhs) {	
		return nameToIndex.get(lhs);
	}
	
private class Variable implements Serializable{
		
		private static final long serialVersionUID = -6873529997583407627L;
		
		private String id;
		private int value;
		
		public Variable(String id, int value) {
			super();
			this.id = id;
			this.value = value;
		}

		public Variable(String id) {
			super();
			this.id = id;
		}
			
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}	
		
	}
	
	
	
}

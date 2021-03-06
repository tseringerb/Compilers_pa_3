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
	
	public void addVariable(Variable variable){
		//variable.index = variablesList.size() + 1;
		variablesList.add(variable);
		nameToIndex.put(variable.id, variablesList.indexOf(variable));
	}
	
	public void assignmentValueToVariable(int index, int value){
		variablesList.get(index).setValue(value);
	} 
	
	/*public void addInstruction(Instruction instruction){
		instructions.add(instruction);
		System.out.println("ADD INSTR " + instructions.size());
	}*/
	
	public int getCurrentInstructionIndex(){
		//System.out.println("!!!!!!!!!!!!");
		//System.out.println(instructions);
		//System.out.println(this.programCounter);
		return instructions.size();	
	}
	
	public void addVariablesList(List<String> variableIdList){ // ��������� ���������� ������� �� ������� 
		variableIdList.forEach((variableId)->{ // � ������ ���������� ������ � �������������
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
		System.out.println("nextInstr " + programCounter);
		Instruction instruction = null;
		instruction = instructions.get(programCounter);
		System.out.println("INSTR " + instruction.getCode() + " INSTR ARG " + instruction.getArgument() + " PC " + programCounter);
		programCounter++;
		return instruction;
	}
	
	public int getProgramCounter(){
		return programCounter;
	}
	
	public void resetProgramCounter(){
		programCounter = 0;
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
		
		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}	
		
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

	public void addInstruction(int iCode, Object argument) {
		Instruction instruction = new Instruction(iCode, argument);
		instructions.add(instruction);	
		//programCounter++;
		//System.out.println(programCounter);
		//System.out.println("ADD INSTR " + instructions.size() + " ICode " + iCode);

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
	
}

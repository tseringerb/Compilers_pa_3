package boris.tserinher.codeGeneration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import boris.tserinher.instructions.InstrEnum;
import boris.tserinher.instructions.Instruction;
//import codeGeneration.Method.Variable;

//public class Method {
//	private List<Var> variablesList;
//	private List<Instruction> instructions;
//	private Map<String, Integer> nameToIndex;
//	private int programCounter; 
//	
//	{
//		variablesList = new ArrayList<>();
//		instructions = new ArrayList<>();
//		nameToIndex = new HashMap<>();
//		programCounter = 0;
//	}
//	
//	void print() {
//		variablesList.forEach((variable)->{
//			System.out.printf("%d s% %d", variable.id, variable.idString, variable.val);
//		});//TODO
//	}
//	
//	public void addVariable(Var variable){
//		variable.id = variablesList.size() + 1;
//		variablesList.add(variable);
//		nameToIndex.put(variable.idString, variablesList.indexOf(variable));
//	}
//	
//	public void assignmentValueToVariable(int index, int value){
//		variablesList.get(index).setVal(value);
//	} 
//	
//	public void addInstruction(Instruction instruction){
//		instructions.add(instruction);
//	}
//	
//	public int getCurrentInstructionIndex(){
//		return instructions.size();	
//	}
//
//	public void setVariablesList(List<String> locals) {
//		// TODO Auto-generated method stub
//		
//	}
public class Method implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1981629263659551909L;
	private List<String> variables = new ArrayList<>();
	private List<Variable> variableTable = new ArrayList<>();
	int varCounter = 0;
	private Map<String,Integer> name2index = new HashMap<>();
	private List<Instruction> instructions = new ArrayList<>();
	int instructionCounter = 0;
	int programCounter = 0;
	
	public void restart(){
		//this.variableTable = new ArrayList<>();
		this.programCounter = 0;
	}
	
	public void print(){
		// print variables
		for(int i = 0; i< variables.size(); i++){
			System.out.print("#"+i+" = "+variables.get(i)+"  ");
		}
		if(variables.size() > 0)
			System.out.print("\n");
		for(int i = 0; i < instructions.size() ; i++){
			System.out.print(String.format("%-3s" , i));
			instructions.get(i).print();			
		}
	}
	
	public Instruction nextInstruction(){
		Instruction ins = null;
		if(programCounter < instructions.size()){
			ins = instructions.get(programCounter);		
			programCounter++;
		}
		return ins;		
	}
	
	public void jumpInstruction(int instNumber){
		programCounter = instNumber;
	}
	
	public int getPC(){
		return this.programCounter;
	}
	
	public int getIndexOf(String variable ){
		int temp;		
		if(!variables.contains(variable)){
			variables.add(variable);
		}
		if(!name2index.containsKey(variable)){ // if here, already added to name2index
			temp = varCounter;
			name2index.put(variable,varCounter);
			varCounter++;
		}else{
			temp = name2index.get(variable);
		}
		return temp;
	}
	
	public void addVariable(String newVariable){
		this.variables.add(newVariable);
	}
	
	public void addInstruction(InstrEnum instrEnum, Object object) {	
		this.instructions.add(new Instruction(instrEnum, object));
		//this.instructions.add(newInstruction);
		//System.out.print(instructionCounter+"    "); newInstruction.print();
		instructionCounter++;
	}
	
	public Instruction getInstruction(int index){
		return instructions.get(index);
	}
	
	public int getIndex(){
		return this.instructionCounter;
	}

	public int getVariableValue(int variableNumber){
		return variableTable.get(variableNumber).getValue();
	}
	
	public void storeVariable(int index, int value){
		if(variableTable.size() > 0){
			if(index >= variableTable.size()){
				variableTable.add(new Variable(index, "newVar", value));
			}else{
				variableTable.get(index).setValue(value);
			}
		}else{
			variableTable.add(new Variable(index, "newVar", value));
		}		
	}
	
	public List<String> getVariables() {
		return variables;
	}

	public void setVariables(List<String> variables) {
		this.variables = variables;
	}

	public List<Instruction> getInstructions() {
		return instructions;
	}

	public void setInstructions(List<Instruction> instructions) {
		this.instructions = instructions;
	}
	
	private class Variable{
		int code;
		String id;
		int value;
		
		public Variable(int code, String id, int value){
			this.code = code;
			this.id = id;
			this.value = value;
		}
		
		public int getValue(){
			return value;
		}
		
		public void setValue(int value){
			this.value = value;
		}
	}
}

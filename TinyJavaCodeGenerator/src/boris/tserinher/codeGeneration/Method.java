package boris.tserinher.codeGeneration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import boris.tserinher.instructions.Instruction;

public class Method {
	private List<Variable> variablesList = new ArrayList<>();
	private List<Instruction> instructions = new ArrayList<>();
	
	private Map<String, Integer> nameToIndex = new HashMap<>();
	
	private int programCounter = 0; //next instruction to be executed
	
	void print(){
		variablesList.forEach((variable)->{
			System.out.printf("%d s% %d", variable.index, variable.id, variable.value);
		});//TODO
	}
	
	public void addVariable(Variable variable){
		variable.index = variablesList.size() + 1;
		variablesList.add(variable);
		nameToIndex.put(variable.id, variablesList.indexOf(variable));
	}
	
	public void assignmentValueToVariable(int index, int value){
		variablesList.get(index).setValue(value);
	} 
	
	public void addInstruction(Instruction instruction){
		instructions.add(instruction);
	}
	
	public int getCurrentInstructionIndex(){
		return instructions.size();	
	}

	private class Variable{
		
		private int index;
		private String id;
		private int value;
		
		public Variable(String id, int value) {
			super();
			this.id = id;
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}	
		
	}
	
}

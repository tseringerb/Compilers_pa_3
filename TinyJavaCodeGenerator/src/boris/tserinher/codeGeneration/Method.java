package boris.tserinher.codeGeneration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import boris.tserinher.instructions.Instruction;

public class Method {
	public List<String> variablesList = new ArrayList<>();
	public List<Instruction> instructions = new ArrayList<>();
	public Map<String, Integer> nameToIndex = new HashMap<>();
	
	private int programCounter = 0; //next instruction to be executed
	
	void print(){
		//TODO
	}
	
	public List<String> getVariables() {
		return variablesList;
	}
	
	public void setVariablesList(List<String> variablesList) {
		this.variablesList = variablesList;
	}
	
	private class Variable{	
		private String id;
	}
	
}

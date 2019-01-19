package boris.tserinher.codeGeneration;

import java.util.ArrayList;
import java.util.List;

import boris.tserinher.instructions.Instruction;

public class Method {
	private List<String> variablesList = new ArrayList<>();
	private List<Instruction> instructions = new ArrayList<>();
	void print(){}
	
	public List<String> getVariables() {
		return variablesList;
	}
	
	public void setVariablesList(List<String> variablesList) {
		this.variablesList = variablesList;
	}
	
}

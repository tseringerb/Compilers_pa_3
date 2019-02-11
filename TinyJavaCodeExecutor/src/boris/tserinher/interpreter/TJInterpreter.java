package boris.tserinher.interpreter;

import boris.tserinher.stackMachine.StackMachine;

public class TJInterpreter {
	/*The TJInterpreter is a simple driver that reads a program name from the
		terminal, creates a new StackMachine object, and starts the actual execution.*/	
	
	public static void main(String[] args){
		String fileName = args[0];
		System.out.println(fileName);
		StackMachine stackMachine = new StackMachine(fileName);
		stackMachine.startExecution();
		
		
	}
}
